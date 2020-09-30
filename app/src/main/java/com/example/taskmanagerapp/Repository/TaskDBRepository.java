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
        context=context.getApplicationContext();
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
    public void update(Task task, Task task2) {

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

    /**
     *
     * @param selection: whereClause
     * @param column:
     * @return
     */

    public Task select(String selection,String column){
        String[] columns = new String[]{
                TaskColumns.STATE,
        };

        String selections =column + " =? ";
        String[] selectionArgs = new String[]{
                selection
        };

        Cursor cursor = mDatabase.query(mTableName,
                null,
                selections,
                selectionArgs,
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

    public List<Task> getTODOList(){
        List<Task> todoList=new ArrayList<>();

        for (int i = 0; i <getLists().size(); i++) {
            todoList.add(select("TODO",TaskColumns.STATE));
        }

        return todoList;
    }

//////-------------------------------------------------------///////////////////////////
/*    public static class TaskDBStateManager implements IRepository<Task>{
        private List<Task> mStateTODOList = new ArrayList<>();
        private List<Task> mStateDOINGList = new ArrayList<>();
        private List<Task> mStateDONEList = new ArrayList<>();

        @Override
        public void insert(Task task) {
            switch (task.getTaskState()) {
                case TODO:
                    mStateTODOList.add(task);
                    return;
                case DOING:
                    mStateDOINGList.add(task);
                    return;
                case DONE:
                    mStateDONEList.add(task);
                    return;
                default:
                    break;
            }
        }

        @Override
        public void delete(Task task) {
            switch (task.getTaskState()) {
                case TODO:
                    mStateTODOList.remove(task);
                    return;
                case DOING:
                    mStateDOINGList.remove(task);
                    return;
                case DONE:
                    mStateDONEList.remove(task);
                    return;
                default:
                    break;

            }
        }

        @Override
        public void update(Task oldTask, Task newTask) {
            updateList(oldTask, newTask);
            oldTask.setTaskTitle(newTask.getTaskTitle());
            oldTask.setTaskContent(newTask.getTaskContent());
            oldTask.setTaskState(newTask.getTaskState());
            oldTask.setTaskDate(newTask.getTaskDate());
            oldTask.setTaskTime(newTask.getTaskTime());
        }

        @Override
        public void update(Task task) {

        }

        @Override
        public Task get(UUID uuid) {
            return null;
        }

        @Override
        public Task get(Task task) {
            return null;
        }

        @Override
        public List<Task> getLists() {
            return null;
        }

        private void updateList(Task oldTask, Task newTask) {
            switch (newTask.getTaskState()) {
                case TODO:
                    mStateTODOList.add(newTask);
                    delete(oldTask);
                    break;
                case DONE:
                    mStateDONEList.add(newTask);
                    delete(oldTask);
                    break;
                case DOING:
                    mStateDOINGList.add(newTask);
                    delete(oldTask);
                    break;
                default:
                    break;
            }
        }

        public void deleteAll() {
            if (mStateTODOList.size() != 0 &&
                    mStateDONEList.size() != 0 &&
                    mStateDOINGList.size() != 0) {
                mStateTODOList.clear();
                mStateDONEList.clear();
                mStateDOINGList.clear();
            }
        }

        private TaskState returnState(Task task) {
            switch (task.getTaskState()) {
                case TODO:
                    return TaskState.TODO;
                case DOING:
                    return TaskState.DOING;
                case DONE:
                    return TaskState.DONE;
                default:
                    return null;
            }
        }


        public Task get(TaskState taskState, UUID uuid) {
            switch (taskState) {
                case TODO:
                    for (Task task : mStateTODOList) {
                        if (task.getUUID().equals(uuid))
                            return task;
                    }
                    break;
                case DOING:
                    for (Task task : mStateDOINGList) {
                        if (task.getUUID().equals(uuid))
                            return task;
                    }
                    break;
                case DONE:
                    for (Task task : mStateDONEList) {
                        if (task.getUUID().equals(uuid))
                            return task;
                    }
                    break;
                default:
                    break;
            }
            return null;
        }

        public List<Task> getTODOTaskList() {
            return mStateTODOList;
        }

        public List<Task> getDONETaskList() {
            return mStateDONEList;
        }

        public List<Task> getDOINGTaskList() {
            return mStateDOINGList;
        }
    }*/
}
