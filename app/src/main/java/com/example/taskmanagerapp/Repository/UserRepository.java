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
    private List<User> mUserList=new ArrayList<>();

    private UserRepository() {

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void delete(User user) {
        for (int i = 0; i < mUserList.size(); i++) {
            if(mUserList.get(i).equals(user))
                mUserList.remove(mUserList.get(i));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void deleteAll(){
        for (int i = 0; i < mUserList.size(); i++) {
           delete(mUserList.get(i));
        }
    }

    @Override
    public void update(User oldUser, User newUser) {
        oldUser.setUserName(newUser.getUserName());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setAdmin(newUser.isAdmin());
        oldUser.setTasksRepository(newUser.getTasksRepository());
    }


    @Override
    public User get(UUID uuid) {
        for (int i = 0; i <mUserList.size() ; i++) {
            if(mUserList.get(i).getUUID().equals(uuid)) {
                return mUserList.get(i);
            }
        }
        return null;
    }


    public User get(String userName) {
        for (int i = 0; i <mUserList.size() ; i++) {
            if(mUserList.get(i).getUserName().equals(userName)) {
                return mUserList.get(i);
            }
        }
        return null;
    }

    public boolean userExist(String username){
        for (int i = 0; i < mUserList.size(); i++) {
            if(mUserList.get(i).getUserName().equals(username))
                return true;
        }
        return false;
    }

    public List<User> getUserList() {
        return mUserList;
    }

}
