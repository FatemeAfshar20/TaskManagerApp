package com.example.taskmanagerapp.Controller.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagerapp.Controller.Fragment.BottomSheetFrag;
import com.example.taskmanagerapp.Controller.Fragment.StateFragment;
import com.example.taskmanagerapp.Controller.SingleFragment;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.R;

import java.util.UUID;

public class StateActivity extends SingleFragment implements
        BottomSheetFrag.onClickItem {

    public static final String EXTRA_TASK_STATE =
            "com.example.taskmanagerapp.Task State";
    public static final String EXTRA_USER_ID =
            "com.example.taskmanagerapp.User Id";

    public static void start(Context context, UUID userId, String taskState) {
        Intent starter = new Intent(context, StateActivity.class);
        starter.putExtra(EXTRA_USER_ID,userId);
        starter.putExtra(EXTRA_TASK_STATE,taskState);
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        Intent intent=getIntent();
       UUID userId= (UUID)
               intent.getSerializableExtra(EXTRA_USER_ID);
        String taskState=
                intent.getStringExtra(EXTRA_TASK_STATE);
        return StateFragment.newInstance(taskState,userId);
    }

    @Override
    public void onClickMoreBtn(UUID taskId) {

    }

    @Override
    public void onClickEditBtn(UUID taskId) {

    }

    @Override
    public void onClickDeleteBtn(Task task) {
        StateFragment stateFragment=
                (StateFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        stateFragment.updateUI();
    }
}