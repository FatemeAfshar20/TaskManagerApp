package com.example.taskmanagerapp.Controller.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.Repository.TaskDBRepository;
import com.example.taskmanagerapp.ViewElem.StateView;

import java.util.UUID;

public class TODO extends StateManagerFragment {
    public static final String ARG_USER_ID = "User Id";
    public static final int REQUEST_CODE_ADD_TASK = 0;
    public static final String FRAGMENT_ADD_TASK_DIALOG = "Add Task Dialog";
    private UUID mUUID;
    private TaskDBRepository mTaskDBRepository;

    public TODO() {
        // Required empty public constructor
    }

    public static TODO newInstance(UUID uuid) {
        TODO fragment = new TODO();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID,uuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mUUID=(UUID) getArguments().
                getSerializable(ARG_USER_ID);
        super.onCreate(savedInstanceState);
        setUser(mUUID);
        mTaskDBRepository=TaskDBRepository.getInstance(getContext()
                ,mUUID);
    }

    @Override
    public void onResume() {
        super.onResume();
        manageOnResumed(mTaskDBRepository
                .getTODOTasks());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        manageView(inflater,container);
        setListener(getStateView());
        manageRecyclerView(mTaskDBRepository.getTODOTasks());
        return getStateView().getView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != Activity.RESULT_OK && data == null)
            return;
        if (requestCode == REQUEST_CODE_ADD_TASK) {
            manageReceiveDataFromAddDialog(data,
                    mTaskDBRepository.getTODOTasks(),TaskState.TODO);
            // updateUI(getTasksRepository().getTODOTaskList());
        }
    }

    @Override
    public void setListener(StateView stateView) {
        stateView.getButtonAddTask().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageDialogFragment(TODO.this,
                        AddTaskDialogFragment.newInstance(mUUID),
                        REQUEST_CODE_ADD_TASK,
                        FRAGMENT_ADD_TASK_DIALOG);
            }
        });
    }
}