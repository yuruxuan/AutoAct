package coding.yu.autoact;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;

/**
 * Author: yu on 18-10-15
 */
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.getConfig().setBorderSwitch(false);
        LogUtils.getConfig().setSaveDays(7);
    }
}
