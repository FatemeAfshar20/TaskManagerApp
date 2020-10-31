package com.example.taskmanagerapp.Model.Task;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.taskmanagerapp.Databese.TaskManagerSchema.Task.TaskColumns;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
@Entity
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long mId;
    @ColumnInfo(name =TaskColumns.UUID)
    private UUID mUUID;
    @ColumnInfo(name = TaskColumns.TITLE)
    private String mTaskTitle;
    @ColumnInfo(name = TaskColumns.CONTENT)
    private String mTaskContent;
    @ColumnInfo(name = TaskColumns.STATE)
    private TaskState mTaskState;
    @ColumnInfo(name = TaskColumns.DATE)
    private Date mTaskDate;
    @ColumnInfo(name = TaskColumns.TIME)
    private Date mTaskTime;
    @ColumnInfo(name = TaskColumns.USERID)
    private UUID mUserId;
    @ColumnInfo(name = TaskColumns.IMAGEADDRESS)
    private String mImgAddress;

    public Task() {
        this(UUID.randomUUID());
    }

    public Task(UUID UUID) {
        mUUID = UUID;
        mTaskDate = new Date();
        mTaskTime = new Date();
    }

    public Task(UUID UUID,
                String taskTitle,
                String taskContent,
                TaskState taskState,
                Date taskDate,
                Date taskTime,
                UUID userId,
                String imgAddress) {
        mUUID = UUID;
        mTaskTitle = taskTitle;
        mTaskContent = taskContent;
        mTaskState = taskState;
        mTaskDate = taskDate;
        mTaskTime = taskTime;
        mUserId = userId;
        mImgAddress = imgAddress;
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

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }


    public String getImgAddress() {
        return mImgAddress;
    }

    public void setImgAddress(String imgPath) {
        mImgAddress = imgPath;


    }
}
