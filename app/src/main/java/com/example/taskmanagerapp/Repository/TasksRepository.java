package com.example.taskmanagerapp.Repository;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TasksRepository implements IRepository<Task> {

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
    public Task get(UUID uuid) {
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
}
