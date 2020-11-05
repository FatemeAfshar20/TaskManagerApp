package com.example.taskmanagerapp.Controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmanagerapp.Controller.Fragment.EditFragment;
import com.example.taskmanagerapp.Controller.Fragment.ShowTaskFragment;
import com.example.taskmanagerapp.Controller.SingleFragmentActivity;

import java.util.UUID;

public class ShowTaskActivity extends SingleFragmentActivity {

    public static final String EXTRA_TASK_ID =
            "com.example.taskmanagerapp.Task Id";

    public static void start(Context context, UUID taskId) {
        Intent starter = new Intent(context, ShowTaskActivity.class);
        starter.putExtra(EXTRA_TASK_ID,taskId);
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        Intent intent=getIntent();
        UUID taskId= (UUID)
                intent.getSerializableExtra(EXTRA_TASK_ID);
        return ShowTaskFragment.newInstance(taskId);
    }
}