package com.example.taskmanagerapp.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import com.example.taskmanagerapp.Databese.TaskManagerDBHelper;
import com.example.taskmanagerapp.Databese.TaskManagerSchema;
import com.example.taskmanagerapp.Databese.TaskManagerSchema.User.UserColumns;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.User.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDBRepository implements IRepository<User> {
    private static UserDBRepository sInstance;
    private SQLiteDatabase mDatabase;
    private String mTableName= TaskManagerSchema.User
            .NAME;
    private Context mContext;

    public UserDBRepository(Context context) {
        mContext = context.getApplicationContext();
        TaskManagerDBHelper taskManagerDBHelper =
                new TaskManagerDBHelper(mContext);

        mDatabase = taskManagerDBHelper.getWritableDatabase();
    }

    public static UserDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new UserDBRepository(context);
        return sInstance;
    }

    @Override
    public List<User> getList() {
        List<User> userList = new ArrayList<>();

        UserCursorWrapper cursorWrapper =
                queryUserCursor(null, null);

        if (cursorWrapper.getCount() == 0)
            return userList;
        try {
            cursorWrapper.moveToFirst();

            while (!cursorWrapper.isAfterLast()) {
                User user = cursorWrapper.getUser();
                userList.add(user);
                cursorWrapper.moveToNext();
            }
            return userList;
        } finally {
            cursorWrapper.close();
        }
    }

    @Override
    public User get(UUID uuid) {
        String whereClause = UserColumns.UUID + " =? ";
        String[] whereArgs = new String[]{uuid.toString()};
        UserCursorWrapper cursorWrapper =
                queryUserCursor(whereClause, whereArgs);

        if (cursorWrapper.getCount() == 0)
            return new User();
        try {
            cursorWrapper.moveToFirst();
            User user = cursorWrapper.getUser();
            return user;
        } finally {
            cursorWrapper.close();
        }
    }

    public User get(String username) {
        String whereClause = UserColumns.USERNAME + " =? ";
        String[] whereArgs = new String[]{username};
        UserCursorWrapper cursorWrapper =
                queryUserCursor(whereClause, whereArgs);

        if (cursorWrapper.getCount() == 0)
            return new User();
        try {
            cursorWrapper.moveToFirst();
            User user = cursorWrapper.getUser();
            return user;
        } finally {
            cursorWrapper.close();
        }
    }

    @Override
    public void delete(User user) {
        String whereClause = UserColumns.UUID + " =? ";
        String[] whereArgs = new String[]{user.getUUID().toString()};

        mDatabase.delete(mTableName, whereClause, whereArgs);
    }

    @Override
    public void insert(User user) {
        String whereClause = UserColumns.UUID + " =? ";
        String[] whereArgs = new String[]{user.getUUID().toString()};

        mDatabase.insert(mTableName,
                null,
                getUserContentValues(user));
    }

    @Override
    public void update(User user) {
        String whereClause = UserColumns.UUID + " =? ";
        String[] whereArgs = new String[]{user.getUUID().toString()};

        mDatabase.update(mTableName,getUserContentValues(user),
                whereClause,whereArgs);
    }

    public boolean userExist(String username){
        String whereClause = UserColumns.USERNAME + " =? ";
        String[] whereArgs = new String[]{username};
        UserCursorWrapper cursorWrapper =
                queryUserCursor(whereClause, whereArgs);

        if (cursorWrapper.getCount() == 0)
            return false;
        try {
            cursorWrapper.moveToFirst();
            User user = cursorWrapper.getUser();
            return user.getUsername().equals("") ? false:true;
        } finally {
            cursorWrapper.close();
        }
    }

    @NotNull
    private ContentValues getUserContentValues(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserColumns.UUID, user.getUUID().toString());
        contentValues.put(UserColumns.USERNAME, user.getUsername());
        contentValues.put(UserColumns.PASSWORD, user.getPassword());
        contentValues.put(UserColumns.MEMBERSHIP, user.getMemberShip().toString());
        contentValues.put(UserColumns.ISADMIN, user.isAdmin() ? 1 : 0);
        return contentValues;
    }


    private UserCursorWrapper queryUserCursor
            (String where, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                mTableName,
                null,
                where,
                whereArgs,
                null,
                null,
                null);

        UserCursorWrapper userCursorWrapper =
                new UserCursorWrapper(cursor);
        return userCursorWrapper;
    }
}
