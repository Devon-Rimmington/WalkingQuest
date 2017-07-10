package walkingquest.kinematicworld.library.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import walkingquest.kinematicworld.library.database.DatabaseAccessor;
import walkingquest.kinematicworld.library.database.databaseHandlers.NativeDataHandler;
import walkingquest.kinematicworld.library.database.objects.NativeData;

/**
 * Created by Devon on 7/5/2017.
 */

public class DeclineActiveEvent extends Service {

    private ServiceHandler mServiceHandler;
    private boolean serviceHandlerRegistered;

    @Override
    public void onCreate(){
        Intent intent = new Intent(this, ServiceHandler.class);
        bindService(intent, serviceHandlerConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d("Unity", "Declined Started");
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private ServiceConnection serviceHandlerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ServiceHandler.ServiceBinder serviceBinder = (ServiceHandler.ServiceBinder) iBinder;
            mServiceHandler = serviceBinder.getService();
            serviceHandlerRegistered = true;

            // decline the event
            mServiceHandler.declineActiveEvent();
            stopSelf();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceHandlerRegistered = false;
        }
    };
}
