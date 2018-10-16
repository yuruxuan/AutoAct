package coding.yu.autoact.core;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Author: yu on 18-10-15
 */
public class AutoActService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
