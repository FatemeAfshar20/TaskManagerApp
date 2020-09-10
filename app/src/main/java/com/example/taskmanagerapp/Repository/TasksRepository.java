package com.example.taskmanagerapp.Repository;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.User.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TasksRepository  implements IRepository<Task> {

    private Task mTask;
    private List<Task> mTaskList=new ArrayList<>();

    public TasksRepository() {

    }


    @Override
    public void insert(Task task) {

    }

    @Override
    public void delete(Task task) {

    }

    @Override
    public void update(Task task) {

    }

    public List<Task> getTaskList() {

        return mTaskList;
    }

    @Override
    public Task get(UUID uuid) {
        return null;
    }

}
