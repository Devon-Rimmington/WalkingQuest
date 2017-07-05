package walkingquest.kinematicworld.library.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Devon on 6/9/2017.
 */

public class StepCounterService extends Service implements SensorEventListener {

    private IBinder mBinder = new ServiceBinder();
    private ServiceHandler mServiceHandler;
    private boolean serviceHandlerRegistered;

    // sensor related variables
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    public  void onCreate(){

        // Log.d("Unity", "Step Service Created");

        // bind this service to the handler service
        // this allows this service to push up changes to the handler
        RegisterSensor(Sensor.TYPE_STEP_DETECTOR);
        Intent intent = new Intent(this, ServiceHandler.class);
        bindService(intent, serviceHandlerConneciton, Context.BIND_AUTO_CREATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // bindService(intent, serviceHandlerConneciton, Context.BIND_AUTO_CREATE);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){

        UnregisterSensor();
        unbindService(serviceHandlerConneciton);
    }

    private void RegisterSensor(int sensorType)
    {
        // register a sensor listener for the sensorType
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(sensorType);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);

        // Log.d("Unity", "Sensor Registered");

    }

    private void UnregisterSensor(){
        sensorManager.unregisterListener(this, sensor);
    }


    public class ServiceBinder extends Binder {
        StepCounterService getService() {
            // Return this instance of LocalService so clients can call public methods
            return StepCounterService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        // if the sensor is a step counter sensor
        if(sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            // push information up to the service handler
            if (serviceHandlerRegistered) {
                mServiceHandler.Update("STEP");
                // Log.d("Unity", "Step Added to Log");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // N/A
    }

    // create a connection between then stepcounter service and this
    private ServiceConnection serviceHandlerConneciton = new ServiceConnection() {
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
