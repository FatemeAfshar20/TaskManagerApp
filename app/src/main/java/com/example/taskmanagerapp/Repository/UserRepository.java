package com.example.taskmanagerapp.Repository;

import com.example.taskmanagerapp.Model.User.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        for (int i = 0; i <mUserList.size() ; i++) {
            if(mUserList.get(i).equals(user))
                mUserList.add(user);
        }
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
            if(mUserList.get(i).getUUID()==uuid)
                return mUserList.get(i);
        }
        return null;
    }

}
