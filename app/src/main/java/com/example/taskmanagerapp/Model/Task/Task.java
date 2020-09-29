package com.example.taskmanagerapp.Model.Task;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Task implements Serializable {
    private UUID mUUID=UUID.randomUUID();
    private String mTaskTitle;
    private String mTaskContent;
    private Date mTaskDate;
    private Date mTaskTime;
    private TaskState mTaskState;

    public Task(UUID uuid,String taskTitle, String taskContent, Date taskDate, Date taskTime, TaskState taskState) {
        this(uuid);
        mTaskTitle = taskTitle;
        mTaskContent = taskContent;
        mTaskDate = taskDate;
        mTaskTime = taskTime;
        mTaskState = taskState;
    }

    public Task(UUID uuid) {
        mUUID=uuid;
    }
    public Task() {
    }
    public UUID getUUID() {
        return mUUID;
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        mTaskTitle = taskTitle;
    }

    public String getTaskContent() {
        return mTaskContent;
    }

    public void setTaskContent(String taskContent) {
        mTaskContent = taskContent;
    }

    public Date getTaskDate() {
        return mTaskDate;
    }

    public void setTaskDate(Date taskDate) {
        mTaskDate = taskDate;
    }

    public Date getTaskTime() {
        return mTaskTime;
    }

    public void setTaskTime(Date taskTime) {
        mTaskTime = taskTime;
    }

    public TaskState getTaskState() {
        return mTaskState;
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
    }
}
