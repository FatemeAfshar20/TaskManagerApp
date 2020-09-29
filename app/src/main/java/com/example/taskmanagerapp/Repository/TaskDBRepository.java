package com.example.taskmanagerapp.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanagerapp.Databese.TaskManagerDBHelper;
import com.example.taskmanagerapp.Databese.TaskManagerSchema;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.Model.User.User;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.example.taskmanagerapp.Databese.TaskManagerSchema.Task.TaskColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskDBRepository implements IRepository<Task> {
    private static TaskDBRepository sInstance;
    private String mTableName =
            TaskManagerSchema.Task.NAME;
    private SQLiteDatabase mDatabase;

    public static TaskDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new TaskDBRepository(context);
        return sInstance;
    }

    private TaskDBRepository(Context context) {
        TaskManagerDBHelper dbHelper =
                new TaskManagerDBHelper(context);

        mDatabase = dbHelper.getWritableDatabase();
    }

    @Override
    public void insert(Task task) {
        ContentValues values = getContentValue(task);
        mDatabase.insert(mTableName,
                null,
                values);
    }


    @Override
    public void delete(Task task) {
        String whereClause = TaskColumns.UUID + " =? ";
        String[] whereArgs = new String[]{
                task.getUUID().toString()
        };
        mDatabase.delete(mTableName,
                whereClause,
                whereArgs);
    }

    @Override
    public void update(Task task, Task e2) {

    }

    @Override
    public void update(Task task) {
        String whereClause = TaskColumns.UUID + " =? ";
        String[] whereArgs = new String[]{
                task.getUUID().toString()
        };

        mDatabase.update(mTableName,
                getContentValue(task),
                whereClause,
                whereArgs);
    }

    @Override
    public Task get(UUID uuid) {
        String[] columns = new String[]{
                TaskColumns.TITLE,
                TaskColumns.CONTENT,
                TaskColumns.TIME,
                TaskColumns.DATE,
                TaskColumns.STATE,
        };

        String selections = TaskColumns.UUID + " =? ";
        String[] selectionArgs = new String[]{
                uuid.toString()
        };

        Cursor cursor = mDatabase.query(mTableName,
                columns,
                selections,
                selectionArgs,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return new Task();

        try {
            cursor.moveToFirst();
            return extractCursor(uuid, cursor);

        } finally {
            cursor.close();
        }
    }

    @Override
    public Task get(Task task) {
        Cursor cursor = mDatabase.query(mTableName,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return new Task();

        try {
            cursor.moveToFirst();
            return extractCursor(cursor);

        } finally {
            cursor.close();
        }
    }

    @Override
    public List<Task> getLists() {
        List<Task> taskList = new ArrayList<>();
        Cursor cursor = mDatabase.query(mTableName,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return taskList;

        try {

            while (!cursor.isAfterLast()) {
                cursor.moveToFirst();
                Task task = extractCursor(cursor);
                taskList.add(task);
                cursor.moveToNext();
            }

            return taskList;

        } finally {
            cursor.close();
        }
    }

    private ContentValues getContentValue(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskColumns.UUID, task.getUUID().toString());
        values.put(TaskColumns.TITLE, task.getTaskTitle());
        values.put(TaskColumns.CONTENT, task.getTaskContent());
        values.put(TaskColumns.STATE, task.getTaskState().toString());
        values.put(TaskColumns.DATE, task.getTaskDate().toString());
        values.put(TaskColumns.TIME, task.getTaskTime().toString());

        return values;
    }

    @NotNull
    private Task extractCursor(UUID uuid, Cursor cursor) {
        String taskTitle = cursor.getString(
                cursor.getColumnIndex(TaskColumns.TITLE));
        String taskContent = cursor.getString(cursor.getColumnIndex(TaskColumns.CONTENT));
        TaskState taskState = getTaskState(cursor);
        Date date = new Date(cursor.getColumnIndex(TaskColumns.DATE));
        Date time = new Date(cursor.getColumnIndex(TaskColumns.TIME));


        return new Task(uuid, taskTitle, taskContent, date, time, taskState);
    }

    @Nullable
    private TaskState getTaskState(Cursor cursor) {
        String taskStateText = cursor.getString(cursor.getColumnIndex(TaskColumns.STATE));
        TaskState taskState;
        switch (taskStateText){
            case "TODO":
                taskState=TaskState.TODO;
                break;

            case "DOING":
                taskState=TaskState.DOING;
                break;

            case "DONE":
                taskState=TaskState.DONE;
                break;

            default:
                taskState=null;
                break;
        }
        return taskState;
    }

    @NotNull
    private Task extractCursor(Cursor cursor) {
        UUID uuid = UUID.fromString(cursor.getString(
                cursor.getColumnIndex(TaskColumns.UUID)));
        String taskTitle = cursor.getString(
                cursor.getColumnIndex(TaskColumns.TITLE));
        String taskContent = cursor.getString(cursor.getColumnIndex(TaskColumns.CONTENT));
        TaskState taskState = getTaskState(cursor);
        Date date = new Date(cursor.getColumnIndex(TaskColumns.DATE));
        Date time = new Date(cursor.getColumnIndex(TaskColumns.TIME));


        return new Task(uuid, taskTitle, taskContent, date, time, taskState);
    }
}
