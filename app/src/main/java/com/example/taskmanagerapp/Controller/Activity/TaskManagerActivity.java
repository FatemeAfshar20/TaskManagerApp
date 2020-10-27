package com.example.taskmanagerapp.Controller.Activity;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.taskmanagerapp.Controller.Fragment.AddTaskFragment;
import com.example.taskmanagerapp.Controller.Fragment.BottomSheetFrag;
import com.example.taskmanagerapp.Controller.Fragment.EditFragment;
import com.example.taskmanagerapp.Controller.Fragment.ShowTaskFragment;
import com.example.taskmanagerapp.Controller.Fragment.StateFragment;
import com.example.taskmanagerapp.Controller.Fragment.TaskManagerFragment;
import com.example.taskmanagerapp.Controller.SingleFragment;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.R;


import java.util.UUID;

/**
 * for start this Activity you should pass uuid,
 * from uuid we can find , which user did login
 */

public class TaskManagerActivity extends SingleFragment
implements TaskManagerFragment.Callbacks,StateFragment.OnAddingTask
,AddTaskFragment.OnBackPressed,BottomSheetFrag.onClickItem{
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
            StateFragment.newInstance(
                    TaskState.TODO.toString(),mUserId);
    }

    @Override
    public Fragment getStateFragment(String taskState) {
        return StateFragment.newInstance(taskState,mUserId);
    }

    @Override
    public void onClickAddTask(TaskState taskState) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container,
                            AddTaskFragment.
                                    newInstance(mUserId,taskState)).
                    commit();
    }

    @Override
    public void onAddBottomSheetFragment(UUID taskId,String taskState) {
        BottomSheetFrag bottomSheetFrag=
                BottomSheetFrag.newInstance(taskId,TaskState.valueOf(taskState));

        String tag = " Fragment Bottom Sheet";
        bottomSheetFrag.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void finishFragment(TaskState taskState) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container,
                        TaskManagerFragment.
                                newInstance(mUserId)).
                commit();
    }

    @Override
    public void onClickMoreBtn(UUID taskId) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container,
                        ShowTaskFragment.
                                newInstance(taskId)).
                commit();
    }

    @Override
    public void onClickEditBtn(UUID taskId) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container,
                        EditFragment.
                                newInstance(taskId)).
                commit();
    }

    @Override
    public void onClickDeleteBtn(Task task) {
        StateFragment stateFragment= (StateFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        stateFragment.updateUI();
    }


/*    BottomSheetFrag bottomSheetFrag=BottomSheetFrag.newInstance(taskId,taskState);
        bottomSheetFrag.show(getSupportFragmentManager(),
    FRAGMENT_BOTTOM_SHEET_DIALOG);*/

/*    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setActionBar(@Nullable Toolbar toolbar) {
        super.setActionBar(toolbar);
    }*/
}