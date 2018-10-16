package coding.yu.autoact.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;

import coding.yu.autoact.bean.AutoTask;


/**
 * Author: yu on 18-10-15
 */
public class AutoActReceiver extends BroadcastReceiver {


    public static final String EXTRA_EXE_TASK_ID = "coding.yu.autoactserver.EXTRA_EXE_TASK_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(EXTRA_EXE_TASK_ID, 0);

        SparseArray<AutoTask> array = AutoTaskManager.getInstance().getAutoTasks();
        AutoTask task = array.get(id);

        if (task != null) {
            new AutoTaskExecutor().execute(task);
        }
    }
}
