package com.example.taskmanagerapp.Controller.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerapp.Controller.Fragment.TaskManagerFragment;
import com.example.taskmanagerapp.Controller.SingleFragment;
import com.example.taskmanagerapp.Repository.UserRepository;

import java.util.UUID;

public class TaskManagerActivity extends SingleFragment {
    private UUID mUUID;
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
        mUUID= (UUID)
                intent.getSerializableExtra(TaskManagerActivity.EXTRA_USER_ID);
        return TaskManagerFragment.newInstance(mUUID);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setActionBar(@Nullable Toolbar toolbar) {
        super.setActionBar(toolbar);
    }
}