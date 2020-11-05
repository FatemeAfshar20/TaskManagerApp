package com.example.taskmanagerapp.Controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.taskmanagerapp.Controller.Fragment.AddTaskFragment;
import com.example.taskmanagerapp.Controller.Fragment.ShowTaskFragment;
import com.example.taskmanagerapp.Controller.SingleFragmentActivity;
import com.example.taskmanagerapp.Model.Task.TaskState;

import java.util.UUID;

public class AddTaskActivity extends SingleFragmentActivity {

    public static final String EXTRA_TASK_STATE =
            "com.example.taskmanagerapp.Task State";
    public static final String EXTRA_USER_ID =
            "com.example.taskmanagerapp.User Id";

    public static void start(Context context, UUID userId,String taskState) {
        Intent starter = new Intent(context, AddTaskActivity.class);
        starter.putExtra(EXTRA_USER_ID,userId);
        starter.putExtra(EXTRA_TASK_STATE,taskState);
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        Intent intent=getIntent();
        UUID userId= (UUID)
                intent.getSerializableExtra(EXTRA_USER_ID);
        String taskState=intent.getStringExtra(EXTRA_TASK_STATE);
        return AddTaskFragment.newInstance(userId, TaskState.valueOf(taskState));
    }
}