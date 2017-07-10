package walkingquest.kinematicworld.library.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import walkingquest.kinematicworld.library.database.DatabaseAccessor;
import walkingquest.kinematicworld.library.database.databaseHandlers.NativeDataHandler;
import walkingquest.kinematicworld.library.database.objects.NativeData;

/**
 * Created by Devon on 6/15/2017.
 */

public class TimerService extends Service {

    private IBinder mBinder = new ServiceBinder();
    private ServiceHandler mServiceHandler;
    private boolean serviceHandlerRegistered;

    private DatabaseAccessor databaseAccessor;
    private NativeData nativeData;

    // the number of steps required for creating a new active event
    private final long STEPS_REQUIRED = 3500;

    @Override
    public void onCreate(){

        databaseAccessor = new DatabaseAccessor(this);
        if((nativeData = NativeDataHandler.getNativeData(databaseAccessor.getReadableDatabase())) == null){
            Log.d("Unity", "Could not find the native data");
        }

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
    public void checkTimer(){

        if(serviceHandlerRegistered){

            // todo remove this hardcoding
            long stepsRequired = STEPS_REQUIRED;

            // push a notification about a new event being available
            if((mServiceHandler.getTotalSteps() % stepsRequired) == 0) {
                mServiceHandler.Update("NEWEVENT");
            }
        }
    }

    // takes in specific events from the service handler
    public void Update(String event){

        switch (event){
            case "ACTIVEEVENT":
                checkTimer();
                break;
            case "MINIQUESTTIMER":
                break;
            default:
                break;
        }
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
