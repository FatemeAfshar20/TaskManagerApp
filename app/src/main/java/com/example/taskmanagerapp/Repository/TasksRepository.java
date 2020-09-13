package com.example.taskmanagerapp.Repository;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;


import java.util.ArrayList;
import java.util.Date;
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
                removeOldTaskInListFromState(oldTask);
                break;
            case DONE:
                mStateDONEList.add(newTask);
                removeOldTaskInListFromState(oldTask);
                break;
            case DOING:
                mStateDOINGList.add(newTask);
                removeOldTaskInListFromState(oldTask);
                break;
            default:
                break;
        }
    }

    private void removeOldTaskInListFromState(Task oldList){
        switch (oldList.getTaskState()){
            case TODO:
                mStateTODOList.remove(oldList);
                return;
            case DOING:
                mStateDOINGList.remove(oldList);
                return;
            case DONE:
                mStateDONEList.remove(oldList);
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
