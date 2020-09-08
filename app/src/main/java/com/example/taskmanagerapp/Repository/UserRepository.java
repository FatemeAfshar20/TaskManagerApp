package com.example.taskmanagerapp.Repository;

import com.example.taskmanagerapp.Model.User.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository  implements IRepository<User> {

    private static UserRepository sInstance;
    private User mUser;
    private List<User> mUserList=new ArrayList<>();

    private UserRepository() {

    }

    public static UserRepository getInstance() {
        if(sInstance==null)
            return new UserRepository();
        return sInstance;
    }

    @Override
    public void insert(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public User get(User user) {
        return null;
    }
}
