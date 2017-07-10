package walkingquest.kinematicworld.library.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.util.Log;

import walkingquest.kinematicworld.library.services.ServiceHandler;

/**
 * Created by Devon on 6/27/2017.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()){
            case Intent.ACTION_BOOT_COMPLETED: // when the device gets started restart the services
                Log.d("Unity", "Broadcast Received");
                context.startService(new Intent(context, ServiceHandler.class));
                break;
            default:
                break;
        }
    }
}
