package coding.yu.autoact.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import coding.yu.autoact.BuildConfig;
import coding.yu.autoact.core.AutoTaskManager;
import coding.yu.autoact.R;
import coding.yu.autoact.bean.AutoTask;
import coding.yu.autoact.helper.DialogHelper;
import coding.yu.autoact.widget.SimpleGroupView;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_NEW_TASK = 1;
    public static final int REQUEST_EDIT_TASK = 2;
    public static final String EXTRA_TASK_ID = "coding.yu.autoact.EXTRA_TASK_ID";

    private LinearLayout layout_task_container;
    private SimpleGroupView simple_clear_this_app;
    private SimpleGroupView simple_root;
    private SimpleGroupView simple_change_system_app;

    private Handler mUiHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout_task_container = findViewById(R.id.layout_task_container);

        simple_root = findViewById(R.id.simple_root);
        simple_root.setName(getString(R.string.root_permission));
        simple_root.setDescription(getString(R.string.root_permission_is_requested));
        simple_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRootStatus(AppUtils.isAppRoot());
            }
        });

        simple_change_system_app = findViewById(R.id.simple_change_system_app);
        simple_change_system_app.setName(getString(R.string.change_to_system_app));
        simple_change_system_app.setDescription(getString(R.string.effective_after_restart));
        simple_change_system_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppUtils.isAppSystem()) {
                    changeToSysApp();
                    checkNecessary();
                }
            }
        });

        simple_clear_this_app = findViewById(R.id.simple_clear_this_app);
        simple_clear_this_app.setName(getString(R.string.clear_this_app));
        simple_clear_this_app.setDescription(getString(R.string.effective_after_restart));
        simple_clear_this_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = DialogHelper.createNormalDialog(MainActivity.this,
                        getString(R.string.warning), getString(R.string.clear_app_tip),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clearThisApp();
                            }
                        });
                dialog.show();
            }
        });

        refreshAllTask();
    }

    private void setRootStatus(boolean active) {
        Drawable drawable = getDrawable(active ? R.drawable.icon_advance_has_active : R.drawable.icon_advance_no_active);
        simple_root.setEndDescriptionImage(drawable);
    }

    private void setSystemAppStatus(boolean active) {
        Drawable drawable = getDrawable(active ? R.drawable.icon_advance_has_active : R.drawable.icon_advance_no_active);
        simple_change_system_app.setEndDescriptionImage(drawable);
    }

    private void clearThisApp() {
        List<String> cmds = new ArrayList<>();
        cmds.add("mount -o rw,remount /system");
        cmds.add("rm -rf /system/app/AutoAct");
        cmds.add("rm -rf " + new File(AppUtils.getAppPath(Utils.getApp().getPackageName())).getParent());
        ShellUtils.execCmd(cmds, true);
        Toast.makeText(this, R.string.clear_app_successfully, Toast.LENGTH_SHORT).show();
    }

    private void refreshAllTask() {
        layout_task_container.removeAllViews();
        List<AutoTask> tasks = AutoTaskManager.getInstance().getAutoTaskList();
        for (int i = 0; i < tasks.size(); i++) {
            final AutoTask task = tasks.get(i);
            int hour = (int) (task.getIntervalTime() / AutoTaskManager.TIME_HOUR_1);
            SimpleGroupView taskView = new SimpleGroupView(this);
            taskView.setName(task.getName());
            taskView.setDescription(task.getDescription());
            taskView.setEndDescriptionText(getString(R.string.run_every_hour, "" + hour));
            taskView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, NewAutoTaskActivity.class);
                    intent.putExtra(EXTRA_TASK_ID, task.getId());
                    startActivityForResult(intent, REQUEST_EDIT_TASK);
                }
            });
            layout_task_container.addView(taskView, SimpleGroupView.getSimpleLayoutParams());
        }
    }

    private void changeToSysApp() {
        List<String> cmds = new ArrayList<>();
        cmds.add("mount -o rw,remount /system");
        cmds.add("mkdir " + "/system/app/AutoAct");
        cmds.add("cp -f " + AppUtils.getAppPath(Utils.getApp().getPackageName()) + " /system/app/AutoAct/AutoAct.apk");
        cmds.add("chmod 644 /system/app/AutoAct/AutoAct.apk");
        cmds.add("rm -rf " + new File(AppUtils.getAppPath(Utils.getApp().getPackageName())).getParent());
        Toast.makeText(this, R.string.change_system_app_successfully, Toast.LENGTH_SHORT).show();
        ShellUtils.execCmd(cmds, true);
    }

    private void checkNecessary() {
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setRootStatus(AppUtils.isAppRoot());
                setSystemAppStatus(AppUtils.isAppSystem());
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNecessary();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_add) {
            Intent intent = new Intent(MainActivity.this, NewAutoTaskActivity.class);
            startActivityForResult(intent, REQUEST_ADD_NEW_TASK);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_NEW_TASK) {
            if (resultCode == RESULT_OK) {
                refreshAllTask();
            }
        }

        if (requestCode == REQUEST_EDIT_TASK) {
            if (resultCode == RESULT_OK) {
                refreshAllTask();
            }
        }
    }
}
