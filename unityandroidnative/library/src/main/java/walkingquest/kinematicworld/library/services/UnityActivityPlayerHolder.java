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

        Log.i("Unity", "Trying to start service");
        Intent intent = new Intent(activity, ServiceHandler.class);
        activity.startService(intent);
        activity.bindService(intent, serviceHandlerConnection, Context.BIND_AUTO_CREATE);
        Log.d("Unity", "After trying to start service");

    }

    public static void Update(){

    }

    public static void Unbind(){
        unityActivity.unbindService(serviceHandlerConnection);
    }

    public static long getSteps(){

        if(serviceHandlerRegistered) {
            Log.d("Unity", "getting steps " +mServiceHandler.getSteps());
            return mServiceHandler.getSteps();
        }
        else
            return -1;
    }

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
