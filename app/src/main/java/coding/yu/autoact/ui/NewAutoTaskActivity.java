package coding.yu.autoact.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ConvertUtils;

import coding.yu.autoact.R;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_auto_task);

        setting_name = findViewById(R.id.setting_name);
        setting_name.setTitleText(R.string.name);
        setting_name.setSubTitleText("New Task 1");
        setting_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createEditTextDialog(getString(R.string.name), setting_name);
                dialog.show();
            }
        });

        setting_description = findViewById(R.id.setting_description);
        setting_description.setTitleText(R.string.description);
        setting_description.setSubTitleText("The First Description");
        setting_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createEditTextDialog(getString(R.string.description), setting_description);
                dialog.show();
            }
        });

        setting_script = findViewById(R.id.setting_script);
        setting_script.setTitleText(R.string.script);
        setting_script.setSubTitleText("Null");
        setting_script.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createEditTextDialog(getString(R.string.script), setting_script);
                dialog.show();
            }
        });

        setting_start_time = findViewById(R.id.setting_start_time);
        setting_start_time.setTitleText(R.string.start_time);
        setting_start_time.setSubTitleText("2018-10-18 21:00:00");
        setting_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createEditTextDialog(getString(R.string.start_time), setting_start_time);
                dialog.show();
            }
        });

        setting_interval_time = findViewById(R.id.setting_interval_time);
        setting_interval_time.setTitleText(R.string.interval_time);
        setting_interval_time.setSubTitleText("24 Hours");
        setting_interval_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createEditTextDialog(getString(R.string.interval_time), setting_interval_time);
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_auto_task, menu);
        return true;
    }

    private AlertDialog createEditTextDialog(String str, final SimpleSettingView simple) {
        FrameLayout layout = new FrameLayout(this);
        layout.setPadding(ConvertUtils.dp2px(20),
                ConvertUtils.dp2px(4),
                ConvertUtils.dp2px(20),
                ConvertUtils.dp2px(4));
        final EditText editText = new EditText(this);
        layout.addView(editText);
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(this);
        inputDialog.setTitle(str);
        inputDialog.setView(layout);
        inputDialog.setCancelable(true);
        inputDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                simple.setSubTitleText(editText.getText().toString());
            }
        });
        inputDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return inputDialog.create();
    }
}
