package com.example.taskmanagerapp.Repository;

import com.example.taskmanagerapp.Model.Task.Task;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TasksRepository  implements IRepository<Task> {

    private Task mTask;
    private List<Task> mStateTODOList =new ArrayList<>();
    private List<Task> mStateDOINGList =new ArrayList<>();
    private List<Task> mStateDONEList =new ArrayList<>();


    public void updateTask(Task oldTask,Task newTask) {
        updateList(oldTask,newTask);
        oldTask.setTaskTitle(newTask.getTaskTitle());
        oldTask.setTaskContent(newTask.getTaskContent());
        oldTask.setTaskState(newTask.getTaskState());
        oldTask.setTaskDate(newTask.getTaskDate());
        oldTask.setTaskTime(newTask.getTaskTime());

    }

    private void updateList(Task oldTask, Task newTask) {
        switch (newTask.getTaskState()){
            case TODO:
                mStateTODOList.add(newTask);
                removeTask(oldTask);
                break;
            case DONE:
                mStateDONEList.add(newTask);
                removeTask(oldTask);
                break;
            case DOING:
                mStateDOINGList.add(newTask);
                removeTask(oldTask);
                break;
            default:
                break;
        }
    }

    public void removeTask(Task task){
        switch (task.getTaskState()){
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

    public List<Task> getTODOTaskList(){
        return mStateTODOList;
    }

    public List<Task> getDONETaskList(){
        return mStateDONEList;
    }

    public List<Task> getDOINGTaskList(){
        return mStateDOINGList;
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

    @Override
    public Task get(UUID uuid) {
        return null;
    }

}
