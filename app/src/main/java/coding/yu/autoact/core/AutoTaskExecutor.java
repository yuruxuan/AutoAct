package coding.yu.autoact.core;

import android.os.AsyncTask;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ShellUtils;

import coding.yu.autoact.bean.AutoTask;

/**
 * Author: yu on 18-10-15
 */
public class AutoTaskExecutor extends AsyncTask<AutoTask, Void, Void> {

    private static final String TAG = "AutoTaskExecutor";

    @Override
    protected Void doInBackground(AutoTask... autoTasks) {
        LogUtils.file(TAG, autoTasks[0]);
        LogUtils.iTag(TAG, autoTasks[0]);

        ShellUtils.execCmd(autoTasks[0].getCommands() ,true);
        return null;
    }
}
