package coding.yu.autoact.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.util.ShellUtils;

import java.util.ArrayList;
import java.util.List;

import coding.yu.autoact.core.AutoTaskManager;
import coding.yu.autoact.R;
import coding.yu.autoact.bean.AutoTask;

public class MainActivity extends AppCompatActivity {

    public static final int TIME_HOUR_1 = 1000 * 60 * 60;
    public static final int TIME_HOUR_8 = TIME_HOUR_1 * 8;
    public static final int TIME_HOUR_12 = TIME_HOUR_1 * 12;
    public static final int TIME_HOUR_24 = TIME_HOUR_1 * 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> cmds = new ArrayList<>();
                cmds.add("input keyevent KEYCODE_MENU");
                cmds.add("sleep 2");
                cmds.add("input keyevent KEYCODE_MENU");
                cmds.add("sleep 5");
                cmds.add("am start -n com.hbmcc.heshenghuo/.ui.welcome.WelcomeActivity");
                cmds.add("sleep 10");
                cmds.add("input keyevent KEYCODE_BACK");
                cmds.add("sleep 2");
                cmds.add("input tap 1400 1560");
                cmds.add("sleep 5");
                cmds.add("input tap 1300 1300");
                cmds.add("sleep 10");
                cmds.add("input tap 700 1830");
                cmds.add("sleep 5");
                cmds.add("am force-stop com.hbmcc.heshenghuo");
                cmds.add("sleep 5");

                AutoTask autoTask = new AutoTask();
                autoTask.setId(1);
                autoTask.setName("Test");
                autoTask.setCommands(cmds);
                autoTask.setStartTime(System.currentTimeMillis() + 1000);
                autoTask.setIntervalTime(60000);

                AutoTaskManager.getInstance().addTask(autoTask);
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AutoTask autoTask = new AutoTask();
//                autoTask.setId(1);
//                autoTask.setName("Test");
//                autoTask.setStartTime(System.currentTimeMillis() + 5000);
//                autoTask.setIntervalTime(60000);
//
//                AutoTaskManager.getInstance().deleteTask(autoTask);
                ShellUtils.execCmd("ls", true);
            }
        });

    }
}
