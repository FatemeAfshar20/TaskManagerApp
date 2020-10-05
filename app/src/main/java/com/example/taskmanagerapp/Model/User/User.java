package com.example.taskmanagerapp.Model.User;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class User implements Serializable {
    private UUID mUUID=UUID.randomUUID();
    private String mUsername;
    private String mPassword;
    private boolean isAdmin;
    private Date mMemberShip;

    public User() {
        this(UUID.randomUUID());
    }

    public User(UUID uuid){
        mUUID=uuid;
        mMemberShip=new Date();
    }

    public User(UUID UUID, String username, String password, boolean isAdmin, Date memberShip) {
        mUUID = UUID;
        mUsername = username;
        mPassword = password;
        this.isAdmin = isAdmin;
        mMemberShip = memberShip;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
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

    public Date getMemberShip() {
        return mMemberShip;
    }

    public void setMemberShip(Date memberShip) {
        mMemberShip = memberShip;
    }
}
