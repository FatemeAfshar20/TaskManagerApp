package com.example.taskmanagerapp.Controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmanagerapp.Controller.Fragment.BottomSheetFrag;
import com.example.taskmanagerapp.Controller.Fragment.StateFragment;
import com.example.taskmanagerapp.Controller.Fragment.TaskManagerFragment;
import com.example.taskmanagerapp.Controller.SingleFragment;
import com.example.taskmanagerapp.Model.Task.TaskState;


import java.util.UUID;

/**
 * for start this Activity you should pass uuid,
 * from uuid we can find , which user did login
 */

public class TaskManagerActivity extends SingleFragment
implements TaskManagerFragment.Callbacks,StateFragment.OnAddingTask {
    private UUID mUserId;
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
        mUserId = (UUID)
                intent.getSerializableExtra(TaskManagerActivity.EXTRA_USER_ID);
        return TaskManagerFragment.newInstance(mUserId);
    }

    @Override
    public void updateUI() {
        mUserId = (UUID)
                getIntent().getSerializableExtra(
                        TaskManagerActivity.EXTRA_USER_ID);
    }

    @Override
    public Fragment getStateFragment(String taskState) {
        return StateFragment.newInstance(taskState,mUserId);
    }

    @Override
    public void onClickAddTask(UUID taskId, TaskState taskState) {
        BottomSheetFrag.newInstance(taskId,taskState);
    }

/*    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setActionBar(@Nullable Toolbar toolbar) {
        super.setActionBar(toolbar);
    }*/
}