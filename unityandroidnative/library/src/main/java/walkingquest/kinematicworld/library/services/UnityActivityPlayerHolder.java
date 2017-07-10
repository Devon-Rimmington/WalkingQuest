package walkingquest.kinematicworld.library.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Devon on 6/9/2017.
 */

public class UnityActivityPlayerHolder {

    private static Activity unityActivity;
    private static ServiceHandler mServiceHandler;
    private static boolean serviceHandlerRegistered;

    public static void StartServices(Activity activity)
    {
        unityActivity = activity;

        Log.d("Unity", "Trying to start service");
        Intent intent = new Intent(activity, ServiceHandler.class);
        activity.startService(intent);
        activity.bindService(intent, serviceHandlerConnection, Context.BIND_AUTO_CREATE);
        Log.d("Unity", "After trying to start service");

    }

    public static void Update(){}

    public static void Unbind(){
        unityActivity.unbindService(serviceHandlerConnection);
    }


    /*************************************************************************
     * GET NATIVE DATA INFORMATION
     *************************************************************************/

    // when a user logs in change (or set on first login) the userId that is assigned to the service
    // should we hash this while it's being stored perhaps this could prevent hacking???
    // todo implement hashing on the userId
    public static boolean setUser(String userId){
        if(serviceHandlerRegistered)
            return mServiceHandler.setUserId(userId);
        return false;
    }

    // sets the currently active character
    public static boolean setCharacterId(long characterId){
        if(serviceHandlerRegistered)
            return mServiceHandler.setCharacterId(characterId);
        return false;
    }

    // get the current miniquest id
    public static long getMiniQuestId(){
        if(serviceHandlerRegistered)
            return mServiceHandler.getMiniQuestId();
        return -1;
    }

    // get the steps completed towards a miniquest
    public static long getStepsCompleted(){
        if(serviceHandlerRegistered)
            return mServiceHandler.getStepsCompleted();
        return -1;
    }

    public static long getStepsRequired(){
        if(serviceHandlerRegistered)
            return mServiceHandler.getStepsRequired();
        return -1;
    }

    // set the miniquest information used to store the miniquest in the service (this might not be used)
    public static boolean setMiniQuest(long miniquestId, long miniquestCompletedSteps, long miniquestRequiredSteps){
        if(serviceHandlerRegistered)
            return mServiceHandler.setMiniQuest(miniquestId, miniquestCompletedSteps, miniquestRequiredSteps);
        return false;
    }

    // use this if no miniquest was currently active
    public static boolean setMiniQuest(long miniquestId, long miniquestCompletedSteps, long miniquestRequiredSteps, long startTime){
        if(serviceHandlerRegistered)
            return mServiceHandler.setMiniQuest(miniquestId, miniquestCompletedSteps, miniquestRequiredSteps, startTime);
        return false;
    }

    // get the number of active events that the player has to play
    public static long getNumberOfEvents(){
        if(serviceHandlerRegistered)
            return mServiceHandler.getNumberOfEvents();
        return -1;
    }

    // gets whether or not a miniquest is available for the player to play
    public static boolean getMiniQuestAvailable(){
        if(serviceHandlerRegistered)
            return mServiceHandler.getMiniQuestAvailable();
        return false;
    }

    // get the total number of steps the player has taken since install
    public static long getTotalSteps(){
        if(serviceHandlerRegistered)
            return mServiceHandler.getTotalSteps();
        return -1;
    }

    // get the number of steps from the trip counter
    public static long getTripSteps(){
        if(serviceHandlerRegistered)
            return mServiceHandler.getTripSteps();
        return -1;
    }

    public static long getLastMiniQuestCompleted(){
        if(serviceHandlerRegistered){
            return mServiceHandler.getLastMiniQuestCompleted();
        }
        return -1;
    }

    // reset the trip counter
    public static boolean resetTripSteps(){
        if(serviceHandlerRegistered)
            return mServiceHandler.resetTripSteps();
        return false;
    }

    /******************************************************************/

    public static boolean getServiceHandlerRegistered(){
        return serviceHandlerRegistered;
    }


    // create a connection between then stepcounter service and this
    private static ServiceConnection serviceHandlerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ServiceHandler.ServiceBinder serviceBinder = (ServiceHandler.ServiceBinder) iBinder;
            mServiceHandler = serviceBinder.getService();
            serviceHandlerRegistered = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceHandlerRegistered = false;
        }
    };


}
