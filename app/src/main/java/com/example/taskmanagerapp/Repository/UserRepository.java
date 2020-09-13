package com.example.taskmanagerapp.Repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.taskmanagerapp.Model.User.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository implements IRepository<User>, Serializable {

    private static UserRepository sInstance;
    private User mUser=new User("kamran","111111");
    private List<User> mUserList=new ArrayList<>();

    private UserRepository() {
        getUserList();
    }

    public static UserRepository getInstance() {
        if(sInstance==null)
            sInstance= new UserRepository();
        return sInstance;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void insert(User user) {
        if(user!=null)
            mUserList.add(user);
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public User get(UUID uuid) {
        for (int i = 0; i <mUserList.size() ; i++) {
            if(mUserList.get(i).getUUID().equals(uuid)) {
                mUserList.get(i).getTasksRepository();
                return mUserList.get(i);
            }
        }
        return null;
    }

    public List<User> getUserList() {
        return mUserList;
    }

}
