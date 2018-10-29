package coding.yu.autoact.core;

import android.os.AsyncTask;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.blankj.utilcode.util.Utils;

import coding.yu.autoact.R;
import coding.yu.autoact.bean.AutoTask;

/**
 * Author: yu on 18-10-15
 */
public class AutoTaskExecutor extends AsyncTask<AutoTask, Void, Void> {

    private static final String TAG = "AutoTaskExecutor";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(Utils.getApp(), R.string.autotask_will_be_executed, Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(AutoTask... autoTasks) {
        try {
            Thread.sleep(2000);
        }catch (Exception e) {
        }

        LogUtils.file(TAG, autoTasks[0]);
        LogUtils.iTag(TAG, autoTasks[0]);

        ShellUtils.execCmd(autoTasks[0].getCommands() ,true);

        AutoTaskManager.getInstance().setupAlarm();
        return null;
    }
}
