package com.example.taskmanagerapp.Model.User;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.Repository.TasksRepository;
import com.example.taskmanagerapp.Repository.UserRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class User implements Serializable {
    private UUID mUUID=UUID.randomUUID();
    private String mUserName;
    private String mPassword;
    private boolean isAdmin;
    private List<Task> mTaskList=new ArrayList<>();
    List<Task> stateTODO=new ArrayList<>();
    List<Task> stateDOING=new ArrayList<>();
    List<Task> stateDONE=new ArrayList<>();

    public User(String userName, String password) {
        mUserName = userName;
        mPassword = password;
    }

    public User() {
    }

    {
        Task task=new Task();
        task.setTaskContent("This is Default Task");
        task.setTaskTitle("Maktab Task");
        task.setTaskDate(new Date());
        task.setTaskTime(new Date());
        task.setTaskState(TaskState.TODO);
        mTaskList.add(task);

        Task task1=new Task();
        task1.setTaskContent("This is Default Task");
        task1.setTaskTitle("Maktab Task");
        task1.setTaskDate(new Date());
        task1.setTaskTime(new Date());
        task1.setTaskState(TaskState.DOING);
        mTaskList.add(task1);

        Task task2=new Task();
        task2.setTaskContent("This is Default Task");
        task2.setTaskTitle("Maktab Task");
        task2.setTaskDate(new Date());
        task2.setTaskTime(new Date());
        task2.setTaskState(TaskState.DONE);
        mTaskList.add(task2);
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }


    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void addInRepository(User user){
        UserRepository.getInstance().insert(user);
    }

    public void addTask(Task task){
        for (int i = 0; i <mTaskList.size()+1 ; i++) {
            if(!mTaskList.get(i).equals(task))
                mTaskList.add(task);
        }
    }

    public void removeTask(Task task){
        for (int i = 0; i <mTaskList.size()+1 ; i++) {
            if(mTaskList.get(i).equals(task))
                mTaskList.remove(task);
        }
    }

    public Task getTask(UUID id) {
        for (Task task: mTaskList) {
            if (task.getUUID().equals(id))
                return task;
        }

        return null;
    }

    public void updateTask(Task task) {
        Task findTask = getTask(task.getUUID());
        findTask.setTaskTitle(task.getTaskTitle());
        findTask.setTaskContent(task.getTaskContent());
        findTask.setTaskState(task.getTaskState());
        findTask.setTaskDate(task.getTaskDate());
        findTask.setTaskTime(task.getTaskTime());
    }

    public List<Task> getTaskList() {
        extractTaskList(stateTODO, stateDOING, stateDONE);
        return stateTODO;
    }

    public List<Task> getTODOTaskList(){
        return stateTODO;
    }

    public List<Task> getDONETaskList(){
        return stateDONE;
    }

    public List<Task> getDOINGTaskList(){
        return stateDOING;
    }

    private void extractTaskList(List<Task> stateTODO, List<Task> stateDOING, List<Task> stateDONE) {
        TaskState taskState;
        for (int i = 0; i <mTaskList.size() ; i++) {
            taskState=mTaskList.get(i).getTaskState();
           switch (taskState){
               case TODO:
                       stateTODO.add(mTaskList.get(i));
                       break;
               case DONE:
                   stateDONE.add(mTaskList.get(i));
                   break;
               case DOING:
                   stateDOING.add(mTaskList.get(i));
                   break;
           }
        }
    }

    public void setTaskList(List<Task> taskList) {
        mTaskList = taskList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(mUserName, user.mUserName) &&
                Objects.equals(mPassword, user.mPassword);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(mUserName, mPassword);
    }
}
