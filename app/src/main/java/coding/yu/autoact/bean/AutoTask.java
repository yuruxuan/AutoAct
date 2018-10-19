package coding.yu.autoact.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Author: yu on 18-10-15
 */
public class AutoTask {
    private int id;
    private String name;
    private String description;
    private List<String> commands;
    private long startTime;
    private long intervalTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public List<String> getCommands() {
        return commands;
    }

    @Override
    public String toString() {
        return "AutoTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", intervalTime=" + intervalTime +
                ", commands=" + commands +
                '}';
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public static AutoTask convert2AutoTask(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, AutoTask.class);
    }

    public static String convert2Json(AutoTask task) {
        Gson gson = new Gson();
        return gson.toJson(task);
    }
}
