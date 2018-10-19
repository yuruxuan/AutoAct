package coding.yu.autoact.ui;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

    public static final int TIME_HOUR_1 = 1000 * 60 * 60;
    public static final int TIME_HOUR_8 = TIME_HOUR_1 * 8;
    public static final int TIME_HOUR_12 = TIME_HOUR_1 * 12;
    public static final int TIME_HOUR_24 = TIME_HOUR_1 * 24;

    private SimpleSettingView setting_name;
    private SimpleSettingView setting_description;
    private SimpleSettingView setting_script;
    private SimpleSettingView setting_start_time;
    private SimpleSettingView setting_interval_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_auto_task);

        setting_name = findViewById(R.id.setting_name);
        setting_name.setTitleText(R.string.name);
        setting_name.setSubTitleText("New Task");
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
        setting_description.setSubTitleText("No description");
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
        long intervalTime = TIME_HOUR_24;
        if (intervalTimeWhich == 0) {
            intervalTime = TIME_HOUR_24;
        } else if (intervalTimeWhich == 1) {
            intervalTime = TIME_HOUR_12;
        } else if (intervalTimeWhich == 2) {
            intervalTime = TIME_HOUR_8;
        } else if (intervalTimeWhich == 3) {
            intervalTime = TIME_HOUR_1;
        }

        AutoTask task = new AutoTask();
        task.setId(task.hashCode());
        task.setName(name);
        task.setDescription(description);
        task.setCommands(cmd);
        task.setStartTime(startTime);
        task.setIntervalTime(intervalTime);

        AutoTaskManager.getInstance().addTask(task);
    }
}
