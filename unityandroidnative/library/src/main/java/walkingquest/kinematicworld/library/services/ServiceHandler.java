package walkingquest.kinematicworld.library.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import walkingquest.kinematicworld.library.R;
import walkingquest.kinematicworld.library.database.DatabaseAccessor;
import walkingquest.kinematicworld.library.database.databaseHandlers.NativeDataHandler;
import walkingquest.kinematicworld.library.database.objects.NativeData;


/**
 * Created by Devon on 6/14/2017.
 */

public class ServiceHandler extends Service {

    private IBinder mBinder = new ServiceBinder();

    private StepCounterService mStepCounterService;
    private boolean stepCounterServiceRegistered;

    private TimerService mTimerService;
    private boolean timerServiceRegistered;

    private DatabaseAccessor databaseAccessor;
    private SQLiteDatabase sqLiteDatabase;
    private NativeData nativeData = null;
    private long steps;

    // variables for the timer
    private static final long MiniQuestNotificationTimeDelay = 20*60*1000; // 1 minute todo change to 20 minutes
    private Timer timer;
    private Handler handler = new Handler();


    @Override
    public void onCreate(){

        // setting up the native data inputs
        databaseAccessor = new DatabaseAccessor(this);
        sqLiteDatabase = databaseAccessor.getWritableDatabase();

        if((nativeData = NativeDataHandler.getNativeData(sqLiteDatabase)) == null){
            setupNativeDataEntry();
        }

        steps = 0;
        // start the timer for creating a new miniquest
        startMiniQuestTimer();

        // bind this service to the timer service to push and pull information from it
        Intent timerIntent = new Intent(this, TimerService.class);
        startService(timerIntent);
        bindService(timerIntent, timerServiceConnection, Context.BIND_AUTO_CREATE);

        // bind this service to the step counter service to pull information from it
        Intent stepIntent = new Intent(this, StepCounterService.class);
        startService(stepIntent);
        bindService(stepIntent, stepServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        unbindService(stepServiceConnection);
    }

    public void Update(){
        mTimerService.Update("MINIQUESTTIMER");
    }

    public void Update(String msg){

        switch (msg){
            case "STEP":
                steps++;

                // increase the trip counter
                long tripCount = nativeData.getTripCounterSteps();
                tripCount++;
                nativeData.setTripCounterSteps(tripCount);

                // increase the total steps
                long stepCount = nativeData.getUserTotalStepCount();
                stepCount++;
                nativeData.setUserTotalStepCount(stepCount);

                // if a miniquest exists and the miniquest_id has been set (aka a quest has been accepted)
                if(nativeData.isAvailableMiniQuest() && nativeData.getMiniquestId() != 0){
                    long questStepCount = nativeData.getMiniquestStepCompleted();
                    questStepCount++;
                    nativeData.setMiniquestStepCompleted(questStepCount);

                    // check if the miniquest has been completed
                    if(nativeData.getMiniquestStepCompleted() >= nativeData.getMiniquestStepRequired()){

                        // if the miniquest is completed change the nativeData
                        nativeData.setAvailableMiniQuest(false);
                        nativeData.setMiniquestId(0);

                        // notify the user
                        miniQuestCompletedNotification();
                    }
                }

                mTimerService.Update("ACTIVEEVENT");

                Log.d("Unity", "The steps from the NativeData is " + nativeData.getUserTotalStepCount());
                break;

            case "NEWEVENT":

                // increase the number of events the player has access to
                long numberOfEvents = nativeData.getAvailableEventCount();
                numberOfEvents++;
                nativeData.setAvailableEventCount(numberOfEvents);

                //todo create a proper notification that opens the game (to the correct state?)
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.title_1)
                                .setContentTitle("A New Event")
                                .setContentText("A new event is available for you to play");

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(1, mBuilder.build());
                break;
        default:
                break;
        }

        if(NativeDataHandler.updateNativeData(nativeData, sqLiteDatabase)) {
            nativeData = NativeDataHandler.getNativeData(sqLiteDatabase);
        }
    }

    /**************************************************************
     * GET NATIVE DATA INFORMATION
     * ************************************************************/

    // check if nativeData has been set
    public boolean isNativeDataAvailable(){ return nativeData != null; }

    // set the userId in nativeData
    public boolean setUserId(String userId){
        if(isNativeDataAvailable()){
            nativeData.setUserId(userId);
            if(NativeDataHandler.updateNativeData(nativeData, sqLiteDatabase)){
                nativeData = NativeDataHandler.getNativeData(sqLiteDatabase);
                return true;
            }
        }
        return false;
    }

    // set the character in the nativeData
    public boolean setCharacterId(long characterId){
        if(isNativeDataAvailable()){
            nativeData.setCharacterId(characterId);
            if(NativeDataHandler.updateNativeData(nativeData, sqLiteDatabase)){
                nativeData = NativeDataHandler.getNativeData(sqLiteDatabase);
                return true;
            }
        }
        return false;
    }

    // get the number of steps that have been put towards completing a miniquest
    public long getStepsCompleted(){
        if(isNativeDataAvailable()){
            return nativeData.getMiniquestStepCompleted();
        }
        return -1;
    }

    // get the number of steps that the player needs to complete the miniquest
    public long getStepsRequired(){
        if(isNativeDataAvailable()){
            return nativeData.getMiniquestStepRequired();
        }
        return -1;
    }

    // get the step count from nativeData
    public long getTotalSteps(){
        if(isNativeDataAvailable()) {
            return nativeData.getUserTotalStepCount();
        }
        return -1;
    }

    // set a new miniquest for the service to record
    public boolean setMiniQuest(long miniquestId, long completedSteps, long requiredSteps){
        if(isNativeDataAvailable()){
            nativeData.setMiniquestId(miniquestId);
            nativeData.setMiniquestStepCompleted(completedSteps);
            nativeData.setMiniquestStepRequired(requiredSteps);
            if(NativeDataHandler.updateNativeData(nativeData, sqLiteDatabase)){
                nativeData = NativeDataHandler.getNativeData(sqLiteDatabase);
                return true;
            }
        }

        return false;
    }

    // set a new miniquest for the service to record with a start time
    public boolean setMiniQuest(long miniquestId, long completedSteps, long requiredSteps, long startTime){
        if(isNativeDataAvailable()){
            nativeData.setMiniquestId(miniquestId);
            nativeData.setMiniquestStepCompleted(completedSteps);
            nativeData.setMiniquestStepRequired(requiredSteps);
            nativeData.setMiniquestStartTime(startTime);
            if(NativeDataHandler.updateNativeData(nativeData, sqLiteDatabase)){
                nativeData = NativeDataHandler.getNativeData(sqLiteDatabase);
                return true;
            }
        }

        return false;
    }

    // get the number of active events that have been earned and banked by the user
    public long getNumberOfEvents(){
        if(isNativeDataAvailable()){
            return nativeData.getAvailableEventCount();
        }
        return -1;
    }

    // get whether or not a miniquest is available for the player to choose
    public boolean getMiniQuestAvailable(){
        if(isNativeDataAvailable()){
            return nativeData.isAvailableMiniQuest();
        }
        return false;
    }

    // get the number of steps recorded by the trip counter
    public long getTripSteps(){
        if(isNativeDataAvailable()){
            return nativeData.getTripCounterSteps();
        }
        return -1;
    }

    public boolean resetTripSteps(){
        if(isNativeDataAvailable()){
            nativeData.setTripCounterSteps(0);
            if(NativeDataHandler.updateNativeData(nativeData, sqLiteDatabase)){
                nativeData = NativeDataHandler.getNativeData(sqLiteDatabase);
                return true;
            }
        }

        return false;
    }

    /*
    // set the step count to nativeData
    public void setTotalSteps(long steps){
        if(isNativeDataAvailable()) {
            Log.d("Unity", "Setting Steps " + steps);
            nativeData.setUserTotalStepCount(steps);
        }
    }
    */

    // if the database has no entry yet then set an initial entry
    private void setupNativeDataEntry(){

        NativeData _nativeData = new NativeData("test", 0, 0, 0, 0, 0, 0, 0, 0, false);
        if(NativeDataHandler.insertNativeData(sqLiteDatabase, _nativeData))
            Log.i("Unity", "Setup the native data");

        // if this fails then we have a problem
        if((nativeData = NativeDataHandler.getNativeData(sqLiteDatabase)) == null){
            Log.i("Unity", "We have a problem");
        }
    }

    /***************************************************************************************/

    private void miniQuestCompletedNotification(){

        //todo create a proper notification that opens the game (to the correct state?)

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.title_1)
                        .setContentTitle("MiniQuest Completed")
                        .setContentText("You have completed the MiniQuest!");

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, mBuilder.build());
    }

    // start the timer for the create a new miniQuest
    private void startMiniQuestTimer(){
        // create or cancel the timer
        if(timer != null) {
            timer.cancel();
        }else{
            timer = new Timer();
        }
        timer.schedule(new TimeDelyForMiniQuest(), 0, MiniQuestNotificationTimeDelay);
    }

    // Below is required for binding services/activities
    public class ServiceBinder extends Binder {
        ServiceHandler getService() {
            // Return this instance of LocalService so clients can call public methods
            return ServiceHandler.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // create a connection between the stepcounter service and this
    private ServiceConnection stepServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            StepCounterService.ServiceBinder serviceBinder = (StepCounterService.ServiceBinder) iBinder;
            mStepCounterService = serviceBinder.getService();
            stepCounterServiceRegistered = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            stepCounterServiceRegistered = false;
        }
    };

    // create a connection between the stepcounter service and this
    private ServiceConnection timerServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TimerService.ServiceBinder serviceBinder = (TimerService.ServiceBinder) iBinder;
            mTimerService = serviceBinder.getService();
            timerServiceRegistered = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            timerServiceRegistered = false;
        }
    };

    // timer class that will notify the player of a new miniquest
    class TimeDelyForMiniQuest extends TimerTask{

        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // if the local time is between 8am and 8pm then offer a new quest
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);

                    // todo implement this boolean in the timer class that is "better" (aka more like what we talked about)
                    if(hour >= 8 && hour <= 20 && (!nativeData.isAvailableMiniQuest() || nativeData.getMiniquestId() != 0)){

                        // todo uncomment this section when the
                        /*

                        // sets a miniquest to be available
                        // doing this will prevent this notification from happening again unless they turn down the miniquest
                        nativeData.setAvailableMiniQuest(true);
                        if(NativeDataHandler.updateNativeData(nativeData, sqLiteDatabase)){
                            nativeData = NativeDataHandler.getNativeData(sqLiteDatabase);
                        }
                        */


                        //todo create a proper notification that opens the game (to the correct state?)
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(getApplicationContext())
                                        .setSmallIcon(R.drawable.title_1)
                                        .setContentTitle("A New MiniQuest!")
                                        .setContentText("A new Quest is available for you to do!");

                        NotificationManager mNotificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        mNotificationManager.notify(2, mBuilder.build());

                    }
                }
            });
        }
    }


}
