package coding.yu.autoact.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;

import com.blankj.utilcode.util.LogUtils;

import coding.yu.autoact.bean.AutoTask;


/**
 * Author: yu on 18-10-15
 */
public class AutoActReceiver extends BroadcastReceiver {

    private static final String TAG = "AutoActReceiver";

    public static final String ACTION_EXE_TASK = "coding.yu.autoact.ACTION_EXE_TASK";
    public static final String EXTRA_EXE_TASK_ID = "coding.yu.autoact.EXTRA_EXE_TASK_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), ACTION_EXE_TASK)) {
            int id = intent.getIntExtra(EXTRA_EXE_TASK_ID, 0);

            SparseArray<AutoTask> array = AutoTaskManager.getInstance().getAutoTasks();
            AutoTask task = array.get(id);

            if (task != null) {
                new AutoTaskExecutor().execute(task);
            }
        }

        if (TextUtils.equals(intent.getAction(), Intent.ACTION_BOOT_COMPLETED)) {
            LogUtils.file(TAG, "ACTION_BOOT_COMPLETED Received");
            LogUtils.iTag(TAG, "ACTION_BOOT_COMPLETED Received");
            AutoTaskManager.getInstance().setupAlarm();
        }
    }
}
