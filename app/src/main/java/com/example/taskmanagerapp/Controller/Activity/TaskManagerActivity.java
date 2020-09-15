package com.example.taskmanagerapp.Controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.taskmanagerapp.Controller.Fragment.TaskManagerFragment;
import com.example.taskmanagerapp.Controller.SingleFragment;

import java.util.UUID;

public class TaskManagerActivity extends SingleFragment {

    public static final String EXTRA_USER_ID =
            "com.example.taskmanagerapp.User Id";

    public static void start(Context context, UUID uuid) {
        Intent starter = new Intent(context, TaskManagerActivity.class);
        starter.putExtra(EXTRA_USER_ID,uuid);
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        Intent intent=getIntent();
        UUID uuid= (UUID)
                intent.getSerializableExtra(TaskManagerActivity.EXTRA_USER_ID);
        return TaskManagerFragment.newInstance(uuid);
    }

}