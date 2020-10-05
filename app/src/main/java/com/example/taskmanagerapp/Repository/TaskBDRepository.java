package com.example.taskmanagerapp.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanagerapp.Databese.TaskManagerDBHelper;
import com.example.taskmanagerapp.Databese.TaskManagerSchema;
import com.example.taskmanagerapp.Databese.TaskManagerSchema.Task.TaskColumns;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskBDRepository implements IRepository<Task> {
    private static TaskBDRepository sInstance;
    private String mTableName=TaskManagerSchema.Task.NAME;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    private TaskBDRepository(Context context) {
        mContext=context.getApplicationContext();
        TaskManagerDBHelper taskManagerDBHelper=
                new TaskManagerDBHelper(mContext);

        mDatabase=taskManagerDBHelper.getWritableDatabase();
    }

    public static TaskBDRepository getInstance(Context context) {
        if(sInstance==null)
            sInstance=new TaskBDRepository(context);
        return sInstance;
    }

    @Override
    public List<Task> getList() {
        List<Task> taskList=new ArrayList<>();
        TaskCursorWrapper cursorWrapper=
                queryTaskCursor(null,null);

        if(cursorWrapper.getCount() == 0)
            return taskList;
        try {
            cursorWrapper.moveToFirst();

            while (!cursorWrapper.isAfterLast()){
                Task task=cursorWrapper.getTask();
                taskList.add(task);
                cursorWrapper.moveToNext();
            }
            return taskList;
        }finally {
            cursorWrapper.close();
        }
    }

    @Override
    public Task get(UUID uuid) {
        String whereClause=TaskColumns.UUID+" =? ";
        String[] whereArgs=new String[]{uuid.toString()};

        TaskCursorWrapper cursorWrapper=
                queryTaskCursor(whereClause,whereArgs);

        if(cursorWrapper.getCount() == 0)
            return new Task();
        try {
            cursorWrapper.moveToFirst();

                Task task=cursorWrapper.getTask();
            return task;
        }finally {
            cursorWrapper.close();
        }
    }

    @Override
    public void delete(Task task) {
        String whereClause=TaskColumns.UUID+" =? ";
        String[] whereArgs=new String[]{task.getUUID().toString()};
        mDatabase.delete(mTableName,whereClause,whereArgs);
    }

    @Override
    public void insert(Task task) {
        mDatabase.insert(mTableName,
                null,
                getTaskContentValues(task));
    }

    @Override
    public void update(Task task) {
        ContentValues contentValues = getTaskContentValues(task);
        String whereClause=TaskColumns.UUID+" =? ";
        String[] whereArgs=new String[]{task.getUUID().toString()};
        mDatabase.update(mTableName,contentValues,whereClause,whereArgs);
    }

    public List<Task> getUserTask(UUID uuid){
        List<Task> userTaskList=new ArrayList<>();
        String whereClause=TaskColumns.USERID+" =? ";
        String[] whereArgs=new String[]{uuid.toString()};

        TaskCursorWrapper cursorWrapper=
                queryTaskCursor(whereClause,whereArgs);

        if(cursorWrapper.getCount() == 0)
            return userTaskList;
        try {
            cursorWrapper.moveToFirst();

            while (!cursorWrapper.isAfterLast()){
                Task task=cursorWrapper.getTask();
                userTaskList.add(task);
                cursorWrapper.moveToNext();
            }
            return userTaskList;
        }finally {
            cursorWrapper.close();
        }
    }

    public List<Task> getDOINGLists(UUID userUUID){
        List<Task> userTaskList=new ArrayList<>();
        String whereClause=TaskColumns.USERID+" =? and "
                +TaskColumns.STATE+" =? ";
        String[] whereArgs=new String[]{userUUID.toString(),
                TaskState.DOING.toString()};

        TaskCursorWrapper cursorWrapper=
                queryTaskCursor(whereClause,whereArgs);

        if(cursorWrapper.getCount() == 0)
            return userTaskList;
        try {
            cursorWrapper.moveToFirst();

            while (!cursorWrapper.isAfterLast()){
                Task task=cursorWrapper.getTask();
                userTaskList.add(task);
                cursorWrapper.moveToNext();
            }
            return userTaskList;
        }finally {
            cursorWrapper.close();
        }
    }

    public List<Task> getDONELists(UUID userUUID){
        List<Task> userTaskList=new ArrayList<>();
        String whereClause=TaskColumns.USERID+" =? and "
                +TaskColumns.STATE+" =? ";
        String[] whereArgs=new String[]{userUUID.toString(),
                TaskState.DONE.toString()};

        TaskCursorWrapper cursorWrapper=
                queryTaskCursor(whereClause,whereArgs);

        if(cursorWrapper.getCount() == 0)
            return userTaskList;
        try {
            cursorWrapper.moveToFirst();

            while (!cursorWrapper.isAfterLast()){
                Task task=cursorWrapper.getTask();
                userTaskList.add(task);
                cursorWrapper.moveToNext();
            }
            return userTaskList;
        }finally {
            cursorWrapper.close();
        }
    }

    public List<Task> getTODOLists(UUID userUUID){
        List<Task> userTaskList=new ArrayList<>();
        String whereClause=TaskColumns.USERID+" =? and "
                +TaskColumns.STATE+" =? ";
        String[] whereArgs=new String[]{userUUID.toString(),
                TaskState.TODO.toString()};

        TaskCursorWrapper cursorWrapper=
                queryTaskCursor(whereClause,whereArgs);

        if(cursorWrapper.getCount() == 0)
            return userTaskList;
        try {
            cursorWrapper.moveToFirst();

            while (!cursorWrapper.isAfterLast()){
                Task task=cursorWrapper.getTask();
                userTaskList.add(task);
                cursorWrapper.moveToNext();
            }
            return userTaskList;
        }finally {
            cursorWrapper.close();
        }
    }


    @NotNull
    private ContentValues getTaskContentValues(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskColumns.UUID, task.getUUID().toString());
        contentValues.put(TaskColumns.TITLE, task.getTaskTitle());
        contentValues.put(TaskColumns.CONTENT, task.getTaskContent());
        contentValues.put(TaskColumns.DATE, task.getTaskDate().toString());
        contentValues.put(TaskColumns.TIME, task.getTaskTime().toString());
        contentValues.put(TaskColumns.STATE, task.getTaskState().toString());
        contentValues.put(TaskColumns.USERID, task.getUserId().toString());
        return contentValues;
    }

    private TaskCursorWrapper queryTaskCursor
            (String where, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                mTableName,
                null,
                where,
                whereArgs,
                null,
                null,
                null);

        TaskCursorWrapper taskCursorWrapper =
                new TaskCursorWrapper(cursor);
        return taskCursorWrapper;
    }
}
