package com.example.taskmanagerapp.Controller.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskmanagerapp.Controller.Fragment.BottomSheetFrag;
import com.example.taskmanagerapp.Controller.Fragment.EditFragment;
import com.example.taskmanagerapp.Controller.Fragment.StateFragment;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TaskBDRepository;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StateActivity extends AppCompatActivity implements
        BottomSheetFrag.onClickItem, StateFragment.OnAddingTask, EditFragment.OnUpdateTask {
    public static final String EXTRA_USER_ID =
            "com.example.taskmanagerapp.User Id";

    private UUID mUserId;

    private TaskAdapter mTaskAdapter;

    private TaskBDRepository mTasksRepository;
    private List<StateFragment> mFragments = new ArrayList<>();

    private String[] mState = {TaskState.TODO.toString(),
            TaskState.DOING.toString(), TaskState.DONE.toString()};

    public static void start(Context context, UUID userId) {
        Intent starter = new Intent(context, StateActivity.class);
        starter.putExtra(EXTRA_USER_ID, userId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_task_maneger);
        mUserId= (UUID) getIntent().getSerializableExtra(EXTRA_USER_ID);
        addFragment();
        setupAdapter();
    }


    @Override
    public void onClickAddTask(TaskState taskState) {
        AddTaskActivity.start(this, mUserId, taskState.toString());
    }

    @Override
    public void onAddBottomSheetFragment(UUID taskId, String taskState) {
        BottomSheetFrag bottomSheetFrag =
                BottomSheetFrag.newInstance(taskId, TaskState.valueOf(taskState));

        String tag = " Fragment Bottom Sheet";
        bottomSheetFrag.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void onClickMoreBtn(UUID taskId) {
        ShowTaskActivity.start(this, taskId);
    }

    @Override
    public void onClickEditBtn(UUID taskId) {
        EditActivity.start(this, taskId);
    }

    @Override
    public void onClickDeleteBtn(Task task) {
        mTasksRepository.delete(task);
        mTaskAdapter.getStateFragment().updateUI();
    }

    @Override
    public void onUpdateTask(String taskState, UUID userId) {

        mTaskAdapter.getStateFragment().updateUI();
    }

    private class TaskAdapter extends FragmentStateAdapter {
        private StateFragment mStateFragment;
        List<StateFragment> mFragmentList;

        public TaskAdapter(@NonNull FragmentActivity fragmentActivity, List<StateFragment> fragmentList) {
            super(fragmentActivity);
            mFragmentList = fragmentList;
        }

        public StateFragment getStateFragment() {
            return mStateFragment;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            mStateFragment=mFragmentList.get(position);
            return mFragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    private void setupAdapter() {
        ViewPager2 viewPager2 = findViewById(R.id.view_pager2);
        mTaskAdapter = new TaskAdapter(this, mFragments);
        viewPager2.setAdapter(mTaskAdapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(mState[position])
        ).attach();
    }

    private void addFragment() {
        mTasksRepository = TaskBDRepository.getInstance(this);
        mFragments.add(0, StateFragment.newInstance(mState[0], mUserId));
        mFragments.add(1, StateFragment.newInstance(mState[1], mUserId));
        mFragments.add(2, StateFragment.newInstance(mState[2], mUserId));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_delete:
                getDialogOk();
                return true;
            default:
                return false;
        }
    }

    private void getDialogOk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).
                setMessage("Are you sure?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTasksRepository.deleteAll(mUserId);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}