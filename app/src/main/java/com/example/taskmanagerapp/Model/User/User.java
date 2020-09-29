package com.example.taskmanagerapp.Model.User;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.taskmanagerapp.Repository.TasksRepository;
import com.example.taskmanagerapp.Repository.UserRepository;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class User implements Serializable {
    private UUID mUUID=UUID.randomUUID();
    private String mUserName;
    private String mPassword;
    private boolean isAdmin;
    private Date mMembership= new Date();
    private TasksRepository mTasksRepository=new TasksRepository();

    public TasksRepository getTasksRepository() {
        return mTasksRepository;
    }

    public void setTasksRepository(TasksRepository tasksRepository) {
        mTasksRepository = tasksRepository;
    }

    public User(String userName, String password) {
        mUserName = userName;
        mPassword = password;
    }

    public User(UUID uuid) {
        mUUID=uuid;
    }

    public User() {
    }

    public User(UUID uuid, String userName, String password, boolean isAdmin, Date membership) {
        this(uuid);
        mUserName = userName;
        mPassword = password;
        this.isAdmin = isAdmin;
        mMembership = membership;
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


    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getMembership() {
        return DateFormat.getDateInstance
                (DateFormat.SHORT).format(mMembership);
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
