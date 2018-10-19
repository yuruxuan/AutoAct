package coding.yu.autoact.core;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;

import com.blankj.utilcode.util.CacheDiskUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import coding.yu.autoact.util.SparseArrayUtils;
import coding.yu.autoact.bean.AutoTask;


/**
 * Author: yu on 18-10-15
 */
public class AutoTaskManager {

    public static final int TIME_HOUR_1 = 1000 * 60 * 60;
    public static final int TIME_HOUR_8 = TIME_HOUR_1 * 8;
    public static final int TIME_HOUR_12 = TIME_HOUR_1 * 12;
    public static final int TIME_HOUR_24 = TIME_HOUR_1 * 24;

    private static final String KEY_ALL_TASK_LIST = "KEY_ALL_TASK_LIST";

    private CacheDiskUtils cacheDiskUtils;

    private volatile static AutoTaskManager sInstance;

    private AutoTaskManager() {
        cacheDiskUtils = CacheDiskUtils.getInstance(Utils.getApp().getFilesDir());
    }

    public static AutoTaskManager getInstance() {
        if (sInstance == null) {
            synchronized (AutoTaskManager.class) {
                if (sInstance == null) {
                    sInstance = new AutoTaskManager();
                }
            }
        }
        return sInstance;
    }

    public void setupAlarm() {
        SparseArray<AutoTask> taskList = getAutoTasks();
        for (int i = 0; i < taskList.size(); i++) {
            AutoTask task = taskList.valueAt(i);
            cancelSingleTask(task);
            setupSingleTask(task);
        }
    }

    public SparseArray<AutoTask> getAutoTasks() {
        String str = cacheDiskUtils.getString(KEY_ALL_TASK_LIST, "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<List<AutoTask>>() {}.getType();
        List<AutoTask> list = gson.fromJson(str, type);

        SparseArray<AutoTask> array = new SparseArray<>();
        for (int i = 0; i < list.size(); i++) {
            AutoTask task = list.get(i);
            array.put(task.getId(), task);
        }

        return array;
    }

    public List<AutoTask> getAutoTaskList() {
        String str = cacheDiskUtils.getString(KEY_ALL_TASK_LIST, "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<List<AutoTask>>() {}.getType();
        List<AutoTask> list = gson.fromJson(str, type);

        return new ArrayList<>(list);
    }

    public void addTask(AutoTask task) {
        setupSingleTask(task);

        SparseArray<AutoTask> array = getAutoTasks();
        array.put(task.getId(), task);

        Gson gson = new Gson();
        String str = gson.toJson(SparseArrayUtils.array2List(array));
        cacheDiskUtils.put(KEY_ALL_TASK_LIST, str);
    }

    public void deleteTask(AutoTask task) {
        cancelSingleTask(task);

        SparseArray<AutoTask> array = getAutoTasks();
        array.remove(task.getId());

        Gson gson = new Gson();
        String str = gson.toJson(SparseArrayUtils.array2List(array));
        cacheDiskUtils.put(KEY_ALL_TASK_LIST, str);
    }

    private void setupSingleTask(AutoTask task) {
        AlarmManager alarmManager = (AlarmManager) Utils.getApp().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Utils.getApp(), AutoActReceiver.class);
        intent.setAction(AutoActReceiver.ACTION_EXE_TASK);
        intent.putExtra(AutoActReceiver.EXTRA_EXE_TASK_ID, task.getId());
        PendingIntent pi = PendingIntent.getBroadcast(Utils.getApp(), task.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, task.getStartTime(), task.getIntervalTime(), pi);
    }

    private void cancelSingleTask(AutoTask task) {
        AlarmManager alarmManager = (AlarmManager) Utils.getApp().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Utils.getApp(), AutoActReceiver.class);
        intent.putExtra(AutoActReceiver.EXTRA_EXE_TASK_ID, task.getId());
        PendingIntent pi = PendingIntent.getBroadcast(Utils.getApp(), task.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pi);
    }
}
