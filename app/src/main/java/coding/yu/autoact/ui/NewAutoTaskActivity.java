package coding.yu.autoact.ui;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import coding.yu.autoact.R;
import coding.yu.autoact.bean.AutoTask;
import coding.yu.autoact.core.AutoTaskManager;
import coding.yu.autoact.helper.DialogHelper;
import coding.yu.autoact.widget.SimpleSettingView;

/**
 * Author: yu on 18-10-17
 */
public class NewAutoTaskActivity extends AppCompatActivity {

    private SimpleSettingView setting_name;
    private SimpleSettingView setting_description;
    private SimpleSettingView setting_script;
    private SimpleSettingView setting_start_time;
    private SimpleSettingView setting_interval_time;

    private AutoTask mExtraAutoTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_auto_task);

        setting_name = findViewById(R.id.setting_name);
        setting_name.setTitleText(R.string.name);
        setting_name.setSubTitleText(getString(R.string.new_task));
        setting_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = DialogHelper.createEditDialog(NewAutoTaskActivity.this,
                        getString(R.string.name), new DialogHelper.OnEditDialogPosClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, String content) {
                                setting_name.setSubTitleText(content);
                            }
                        });
                dialog.show();
            }
        });

        setting_description = findViewById(R.id.setting_description);
        setting_description.setTitleText(R.string.description);
        setting_description.setSubTitleText(getString(R.string.no_description));
        setting_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = DialogHelper.createEditDialog(NewAutoTaskActivity.this,
                        getString(R.string.description), new DialogHelper.OnEditDialogPosClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, String content) {
                                setting_description.setSubTitleText(content);
                            }
                        });
                dialog.show();
            }
        });

        setting_script = findViewById(R.id.setting_script);
        setting_script.setTitleText(R.string.script);
        setting_script.setSubTitleText("");
        setting_script.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = DialogHelper.createEditDialog(NewAutoTaskActivity.this,
                        getString(R.string.script), new DialogHelper.OnEditDialogPosClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, String content) {
                                setting_script.setSubTitleText(content);
                            }
                        });
                dialog.show();
            }
        });

        setting_start_time = findViewById(R.id.setting_start_time);
        setting_start_time.setTitleText(R.string.start_time);
        setting_start_time.setSubTitleText(TimeUtils.getNowString());
        setting_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = DialogHelper.createTimeDialog(NewAutoTaskActivity.this,
                        new DialogHelper.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(int hourOfDay, int minute) {
                                Calendar calendar = new GregorianCalendar();
                                calendar.setTime(new Date());
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
                                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                                }
                                String date2Str = TimeUtils.date2String(calendar.getTime());
                                setting_start_time.setSubTitleText(date2Str);
                            }
                        });
                dialog.show();
            }
        });

        setting_interval_time = findViewById(R.id.setting_interval_time);
        setting_interval_time.setTitleText(R.string.interval_time);
        setting_interval_time.setSubTitleText(getResources().getStringArray(R.array.choice_time)[0]);
        setting_interval_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = DialogHelper.createChoiceDialog(NewAutoTaskActivity.this,
                        getString(R.string.interval_time), R.array.choice_time, new DialogHelper.OnChoicePosClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setting_interval_time.setExtraWhat(which);
                                setting_interval_time.setSubTitleText(getResources().getStringArray(R.array.choice_time)[which]);
                            }
                        });
                dialog.show();
            }
        });

        int extraId = getIntent().getIntExtra(MainActivity.EXTRA_TASK_ID, -1);
        SparseArray<AutoTask> array = AutoTaskManager.getInstance().getAutoTasks();
        mExtraAutoTask = array.get(extraId);

        if (mExtraAutoTask != null) {
            int which = 0;
            if (mExtraAutoTask.getIntervalTime() == AutoTaskManager.TIME_HOUR_24) {
                which = 0;
            }
            if (mExtraAutoTask.getIntervalTime() == AutoTaskManager.TIME_HOUR_12) {
                which = 1;
            }
            if (mExtraAutoTask.getIntervalTime() == AutoTaskManager.TIME_HOUR_8) {
                which = 2;
            }
            if (mExtraAutoTask.getIntervalTime() == AutoTaskManager.TIME_HOUR_4) {
                which = 3;
            }
            if (mExtraAutoTask.getIntervalTime() == AutoTaskManager.TIME_HOUR_1) {
                which = 4;
            }
            String[] stringArr = getResources().getStringArray(R.array.choice_time);

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < mExtraAutoTask.getCommands().size(); i++) {
                builder.append(mExtraAutoTask.getCommands().get(i)).append("\n");
            }

            setting_name.setSubTitleText(mExtraAutoTask.getName());
            setting_description.setSubTitleText(mExtraAutoTask.getDescription());
            setting_script.setSubTitleText(builder.toString());
            setting_start_time.setSubTitleText(TimeUtils.date2String(new Date(mExtraAutoTask.getStartTime())));
            setting_interval_time.setSubTitleText(stringArr[which]);
            setting_interval_time.setExtraWhat(which);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_auto_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_save) {
            createNewTask();
            Toast.makeText(this, R.string.new_task_has_created, Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
            return true;
        }

        if (item.getItemId() == R.id.item_delete) {
            if (mExtraAutoTask != null) {
                AutoTaskManager.getInstance().deleteTask(mExtraAutoTask);
            }
            Toast.makeText(this, R.string.task_has_deleted, Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createNewTask() {
        String name = setting_name.getSubTitleText();
        String description = setting_description.getSubTitleText();
        String script = setting_script.getSubTitleText();
        String startTimeStr = setting_start_time.getSubTitleText();
        int intervalTimeWhich = setting_interval_time.getExtraWhat();

        List<String> cmd = new ArrayList<>();
        String[] array = script.split("\n");
        for (int i = 0; i < array.length; i++) {
            if (TextUtils.isEmpty(array[i].trim())) {
                continue;
            }
            cmd.add(array[i]);
        }

        long startTime = TimeUtils.string2Date(startTimeStr).getTime();
        long intervalTime = AutoTaskManager.TIME_HOUR_24;
        if (intervalTimeWhich == 0) {
            intervalTime = AutoTaskManager.TIME_HOUR_24;
        } else if (intervalTimeWhich == 1) {
            intervalTime = AutoTaskManager.TIME_HOUR_12;
        } else if (intervalTimeWhich == 2) {
            intervalTime = AutoTaskManager.TIME_HOUR_8;
        } else if (intervalTimeWhich == 3) {
            intervalTime = AutoTaskManager.TIME_HOUR_4;
        } else if (intervalTimeWhich == 4) {
            intervalTime = AutoTaskManager.TIME_HOUR_1;
        }

        AutoTask task = new AutoTask();
        task.setId(task.hashCode());
        task.setName(name);
        task.setDescription(description);
        task.setCommands(cmd);
        task.setStartTime(startTime);
        task.setIntervalTime(intervalTime);

        if (mExtraAutoTask != null) {
            task.setId(mExtraAutoTask.getId());
        }

        AutoTaskManager.getInstance().addTask(task);
    }
}
