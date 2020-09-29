package com.example.taskmanagerapp.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanagerapp.Databese.TaskManagerDBHelper;
import com.example.taskmanagerapp.Databese.TaskManagerSchema;
import com.example.taskmanagerapp.Model.User.User;

import org.jetbrains.annotations.NotNull;

import static com.example.taskmanagerapp.Databese.TaskManagerSchema.User.UserColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserDBRepository implements IRepository<User> {
    private static UserDBRepository sInstance;
    private SQLiteDatabase mDatabase;
    private String mTaleName = TaskManagerSchema.User.NAME;

    public static UserDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new UserDBRepository(context);
        return sInstance;
    }

    public UserDBRepository(Context context) {
        TaskManagerDBHelper dbHelper =
                new TaskManagerDBHelper(context);
        mDatabase = dbHelper.getWritableDatabase();
    }

    @Override
    public void insert(User user) {
        ContentValues values = getContentValues(user);
        mDatabase.insert(mTaleName,
                null,
                values);
    }


    @Override
    public void delete(User user) {
        String whereClause = UserColumns.UUID + " =?";
        String[] whereArgs = new String[]{user.getUUID().toString()};

        mDatabase.delete(mTaleName,
                whereClause,
                whereArgs);
    }

    @Override
    public void update(User user, User e2) {

    }

    @Override
    public void update(User user) {
        String whereClause = UserColumns.UUID + " =? ";
        String[] whereArgs = new String[]{user.getUUID().toString()};

        mDatabase.update(mTaleName,
                getContentValues(user),
                whereClause,
                whereArgs);
    }

    @Override
    public User get(UUID uuid) {
        String[] columns = new String[]{
                UserColumns.USERNAME,
                UserColumns.PASSWORD,
                UserColumns.MEMBERSHIP,
                UserColumns.TASK,
                UserColumns.ISADMIN
        };

        String whereClause = UserColumns.UUID + " =? ";
        String[] whereArgs = new String[]{uuid.toString()};

        Cursor cursor = mDatabase.query(mTaleName,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return new User(uuid);

        try {
            cursor.moveToFirst();
            return extractCursor(uuid, cursor);

        } finally {
            cursor.close();
        }
    }

    @Override
    public User get(User user) {
        Cursor cursor = mDatabase.query(mTaleName,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return new User();

        try {
            cursor.moveToFirst();
            return extractCursor(cursor);
        } finally {
            cursor.getCount();
        }
    }

    @Override
    public List<User> getLists() {
        List<User> userList = new ArrayList<>();
        Cursor cursor = mDatabase.query(mTaleName,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return new ArrayList<>();

        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                User user = extractCursor(cursor);
                userList.add(user);
                cursor.moveToNext();
            }

            return userList;
        } finally {
            cursor.close();
        }
    }

    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UserColumns.UUID, user.getUUID().toString());
        values.put(UserColumns.USERNAME, user.getUserName());
        values.put(UserColumns.PASSWORD, user.getPassword());
        values.put(UserColumns.ISADMIN, user.isAdmin() ? 1 : 0);
        values.put(UserColumns.MEMBERSHIP, user.getMembership());
        return values;
    }

    @NotNull
    private User extractCursor(UUID uuid, Cursor cursor) {
        String username = cursor.getString(
                cursor.getColumnIndex(UserColumns.USERNAME));
        String password = cursor.getString(cursor.getColumnIndex(UserColumns.PASSWORD));
        Date membership = new Date(cursor.getColumnIndex(UserColumns.PASSWORD));
        boolean isAdmin = cursor.getInt(cursor.getColumnIndex(UserColumns.ISADMIN)) == 1;

        return new User(uuid, username, password, isAdmin, membership);
    }

    @NotNull
    private User extractCursor(Cursor cursor) {
        UUID uuid = UUID.fromString(
                cursor.getString(
                        cursor.getColumnIndex(UserColumns.UUID)));
        String username = cursor.getString(
                cursor.getColumnIndex(UserColumns.USERNAME));
        String password = cursor.getString(cursor.getColumnIndex(UserColumns.PASSWORD));
        Date membership = new Date(cursor.getColumnIndex(UserColumns.PASSWORD));
        boolean isAdmin = cursor.getInt(cursor.getColumnIndex(UserColumns.ISADMIN)) == 1;

        return new User(uuid, username, password, isAdmin, membership);
    }
}
