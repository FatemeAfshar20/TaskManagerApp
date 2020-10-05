package com.example.taskmanagerapp.Repository;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanagerapp.Databese.TaskManagerSchema;
import com.example.taskmanagerapp.Databese.TaskManagerSchema.User.UserColumns;
import com.example.taskmanagerapp.Model.User.User;

import java.util.Date;
import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser(){
        UUID uuid=UUID.fromString(
                getString(getColumnIndex(UserColumns.UUID)));
        String username=getString(
                getColumnIndex(UserColumns.USERNAME));
        String password=getString(
                getColumnIndex(UserColumns.PASSWORD));
        Date membership=new Date(getColumnIndex(
                UserColumns.MEMBERSHIP));
        boolean isAdmin=getInt( getColumnIndex(UserColumns.PASSWORD))==1;
        return new User(uuid,username,password,isAdmin,membership);
    }
}
