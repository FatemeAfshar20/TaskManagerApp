package com.example.taskmanagerapp.Repository;

import com.example.taskmanagerapp.Model.Task.Task;

import java.util.List;
import java.util.UUID;

public class TaskDBRepository implements IRepository<Task> {
    @Override
    public void insert(Task task) {

    }

    @Override
    public void delete(Task task) {

    }

    @Override
    public void update(Task task, Task e2) {

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
}
