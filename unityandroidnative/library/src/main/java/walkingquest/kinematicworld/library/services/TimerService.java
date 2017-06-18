package walkingquest.kinematicworld.library.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Devon on 6/15/2017.
 */

public class TimerService extends Service {

    private IBinder mBinder = new ServiceBinder();
    private ServiceHandler mServiceHandler;
    private boolean serviceHandlerRegistered;

    @Override
    public void onCreate(){

        Log.d("Unity", "Timer Service Created");

        // bind this service to the service handler to push and pull information from it
        Intent intent = new Intent(this, ServiceHandler.class);
        bindService(intent, serviceHandlerConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    public int onStartCommand(Intent intent, int startId, int flags){
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        unbindService(serviceHandlerConnection);
    }

    // method invokes a push notification to the user when an event or miniquest is available
    // or when a miniquest has been completed and a reward can be collected
    public void invokeNotification(String msg){

        if(serviceHandlerRegistered){
            /*
            // todo remove this hardcoding
            long stepsRequired = 100;
            // push a notification about the quest being done
            if(mServiceHandler.getSteps() >= stepsRequired){

                //todo create a proper notification that opens the game (to the correct state?)

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.sun)
                                .setContentTitle("My notification")
                                .setContentText("Hello World!");

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(0, mBuilder.build());
            }
            */
        }

    }

    // a timer used to check if someone is currently active or has just started to become active
    private void startTimer(){

        // if the player has not taken steps for a while push a notification about a new miniquest

        // if the player is walking for a while push a notification about an event

    }

    public void Update(){
        invokeNotification("MiniQuest Done!");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class ServiceBinder extends Binder {
        TimerService getService(){
            return TimerService.this;
        }
    }

    private ServiceConnection serviceHandlerConnection = new ServiceConnection() {
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
