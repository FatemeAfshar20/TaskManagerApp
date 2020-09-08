package com.example.taskmanagerapp.Model.User;

import com.example.taskmanagerapp.Repository.TasksRepository;

import java.util.UUID;

public class User {
    private UUID mUUID=UUID.randomUUID();
    private String mUserName;
    private String mPassword;
    private TasksRepository mTasksRepository;

    public User(String userName, String password) {
        mUserName = userName;
        mPassword = password;
    }

    public User() {
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public TasksRepository getTasksRepository() {
        return mTasksRepository;
    }

    public void setTasksRepository(TasksRepository tasksRepository) {
        mTasksRepository = tasksRepository;
    }
}
