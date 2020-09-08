package com.example.taskmanagerapp.Model.User;

import java.util.UUID;

public class Admin extends User {
    private UUID mUUID=UUID.randomUUID();
    private int mAdminId=randomNum(100,1000);

    @Override
    public UUID getUUID() {
        return mUUID;
    }

    public String getAdminPass() {
        return "@utab";
    }

    public int getAdminId() {
        return mAdminId;
    }

    private int randomNum(int min,int max){
        return min+(int) Math.round( Math.random() * (max-min));
    }

}
