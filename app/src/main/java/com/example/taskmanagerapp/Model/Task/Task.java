package com.example.taskmanagerapp.Model.Task;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Task implements Serializable {
    private UUID mUUID;
    private String mTaskTitle;
    private String mTaskContent;
    private TaskState mTaskState;
    private Date mTaskDate;
    private Date mTaskTime;
    private UUID mUserId;

    public Task() {
        this(UUID.randomUUID());
    }

    public Task(UUID UUID) {
        mUUID = UUID;
        mTaskDate = new Date();
        mTaskTime = new Date();
    }

    public Task(UUID UUID, String taskTitle,
                String taskContent, TaskState taskState,
                Date taskDate, Date taskTime, UUID userId) {
        mUUID = UUID;
        mTaskTitle = taskTitle;
        mTaskContent = taskContent;
        mTaskState = taskState;
        mTaskDate = taskDate;
        mTaskTime = taskTime;
        mUserId = userId;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
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

    public TaskState getTaskState() {
        return mTaskState;
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
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

    public UUID getUserId() {
        return mUserId;
    }

    public void setUserId(UUID userId) {
        mUserId = userId;
    }
}
