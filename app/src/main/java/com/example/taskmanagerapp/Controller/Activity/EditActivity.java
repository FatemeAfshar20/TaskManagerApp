package com.example.taskmanagerapp.Controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.taskmanagerapp.Controller.Fragment.EditFragment;
import com.example.taskmanagerapp.Controller.Fragment.StateFragment;
import com.example.taskmanagerapp.Controller.SingleFragmentActivity;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.R;

import java.util.UUID;

public class EditActivity extends SingleFragmentActivity implements EditFragment.OnUpdateTask {

    public static final String EXTRA_TASK_ID =
            "com.example.taskmanagerapp.Task Id";

    public static void start(Context context, UUID taskId) {
        Intent starter = new Intent(context, EditActivity.class);
        starter.putExtra(EXTRA_TASK_ID,taskId);
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        Intent intent=getIntent();
        UUID taskId= (UUID)
                intent.getSerializableExtra(EXTRA_TASK_ID);
        return EditFragment.newInstance(taskId);
    }

    @Override
    public void onUpdateTask(String taskState,UUID userId) {

    }
}