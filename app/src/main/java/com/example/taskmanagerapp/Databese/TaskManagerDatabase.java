package com.example.taskmanagerapp.Databese;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.taskmanagerapp.Databese.DAO.TaskTableDAO;
import com.example.taskmanagerapp.Databese.DAO.UserTableDAO;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.User.User;

@Database(entities = {Task.class, User.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class TaskManagerDatabase extends RoomDatabase {

    public abstract UserTableDAO getUserDao();
    public abstract TaskTableDAO getTaskDao();
}
