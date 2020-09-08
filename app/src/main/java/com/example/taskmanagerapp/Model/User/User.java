package com.example.taskmanagerapp.Model.User;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.taskmanagerapp.Repository.TasksRepository;
import com.example.taskmanagerapp.Repository.UserRepository;

import java.util.Objects;
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

    public static void addInRepository(User user){
        UserRepository.getInstance().insert(user);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(mUserName, user.mUserName) &&
                Objects.equals(mPassword, user.mPassword);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(mUserName, mPassword);
    }
}
