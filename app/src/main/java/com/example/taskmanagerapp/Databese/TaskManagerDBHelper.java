package com.example.taskmanagerapp.Databese;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.lang.UCharacter;

import static com.example.taskmanagerapp.Databese.TaskManagerSchema.User.UserColumns;
import static com.example.taskmanagerapp.Databese.TaskManagerSchema.Task.TaskColumns;
import androidx.annotation.Nullable;

public class TaskManagerDBHelper extends SQLiteOpenHelper {

    public TaskManagerDBHelper(@Nullable Context context) {
        super(context, TaskManagerSchema.NAME, null, TaskManagerSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //---- create user table
        StringBuilder queryUser=new StringBuilder();
        queryUser.append("CREATE TABLE "+TaskManagerSchema.User.NAME+" ( ");
        queryUser.append(UserColumns.ID+"INTEGER PRIMARY KEY NOT NULL , ");
        queryUser.append(UserColumns.UUID+"TEXT NOT NULL , ");
        queryUser.append(UserColumns.USERNAME+"TEXT NOT NULL UNIQUE, ");
        queryUser.append(UserColumns.PASSWORD+"TEXT NOT NULL, ");
        queryUser.append(UserColumns.MEMBERSHIP+"TEXT, ");
        queryUser.append(UserColumns.ISADMIN+"INTEGER, ");
        queryUser.append(UserColumns.TASK+" , ");
        queryUser.append(" ); ");

        db.execSQL(queryUser.toString());

        //---- create task table
        StringBuilder queryTask=new StringBuilder();
        queryUser.append("CREATE TABLE "+TaskManagerSchema.Task.NAME+" ( ");
        queryUser.append(TaskColumns.ID+"INTEGER PRIMARY KEY NOT NULL , ");
        queryUser.append(TaskColumns.UUID+"TEXT NOT NULL , ");
        queryUser.append(TaskColumns.TITLE+"TEXT NOT NULL UNIQUE, ");
        queryUser.append(TaskColumns.CONTENT+"TEXT NOT NULL, ");
        queryUser.append(TaskColumns.DATE+"TEXT, ");
        queryUser.append(TaskColumns.TIME+"TEXT, ");
        queryUser.append(TaskColumns.STATE+"TEXT ");
        queryUser.append(" ); ");

        db.execSQL(queryTask.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
