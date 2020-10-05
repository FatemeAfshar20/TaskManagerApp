package com.example.taskmanagerapp.Repository;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanagerapp.Databese.TaskManagerSchema.Task.TaskColumns;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask(){
        UUID uuid=UUID.fromString(
                getString(getColumnIndex(TaskColumns.UUID)));
        String title=getString(
                getColumnIndex(TaskColumns.TITLE));
        String content=getString(
                getColumnIndex(TaskColumns.CONTENT));
        TaskState taskState=getTaskState();
        Date date=new Date(getColumnIndex(
                TaskColumns.DATE));
        Date time=new Date(getColumnIndex(
                TaskColumns.DATE));
        UUID userId=UUID.fromString(
                getString(getColumnIndex(TaskColumns.USERID)));

        return new Task(uuid,title,content,taskState,date,time,userId);
    }

    private TaskState getTaskState(){
        String taskState=getString(
                getColumnIndex(TaskColumns.STATE));

        switch (taskState){
            case "TODO":
                return TaskState.TODO;
            case "DOING":
                return TaskState.DOING;
            case "DONE":
                return TaskState.DONE;
            default:
                return null;
        }
    }
}
