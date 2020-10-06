package com.example.taskmanagerapp.Databese.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.Model.User.User;

import java.util.List;
import java.util.UUID;

@Dao
public interface TaskTableDAO {

    @Query(value = "SELECT * FROM Task")
    List<Task> getList();
    @Query(value = "SELECT * FROM Task WHERE uuid=:uuid")
    Task get(UUID uuid);
    @Query(value = "SELECT * FROM Task WHERE userId=:userId")
    List<Task> getUserTask(UUID userId);
    @Query(value = "SELECT * FROM Task WHERE userId=:userUUID and state=:taskState")
    List<Task> getStateTaskLists(UUID userUUID, TaskState taskState);
    @Delete
    void delete(Task task);
    @Insert
    void insert(Task task);
    @Update
    void update(Task task);

}
