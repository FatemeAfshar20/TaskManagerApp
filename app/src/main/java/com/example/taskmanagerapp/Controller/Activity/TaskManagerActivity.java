package com.example.taskmanagerapp.Controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.taskmanagerapp.Controller.Fragment.TaskManagerFragment;
import com.example.taskmanagerapp.Controller.SingleFragment;

public class TaskManagerActivity extends SingleFragment {

    public static void start(Context context) {
        Intent starter = new Intent(context, TaskManagerActivity.class);
      //  starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        return TaskManagerFragment.newInstance();
    }

}