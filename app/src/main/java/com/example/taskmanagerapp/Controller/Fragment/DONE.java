package com.example.taskmanagerapp.Controller.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.ViewElem.StateView;

public class DONE extends StateManagerFragment {
    private static final int REQUEST_CODE_ADD_TASK = 0;
    private static final String FRAGMENT_ADD_TASK_DIALOG = "Adding task";

    public DONE() {
        // Required empty public constructor
    }

    public static DONE newInstance() {
        DONE fragment = new DONE();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        manageOnResumed(
                getTasksRepository().getDONETaskList());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        manageView(inflater, container);
        setListener(getStateView());
        manageRecyclerView(
                getTasksRepository().getDONETaskList());
        return getStateView().getView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != Activity.RESULT_OK && data == null)
            return;
        if (requestCode == REQUEST_CODE_ADD_TASK) {
            manageReceiveDataFromAddDialog(data,
                    getTasksRepository().getDONETaskList(),
                    TaskState.DONE);
        }
    }

    @Override
    public void setListener(StateView stateView) {
        stateView.getButtonAddTask().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageDialogFragment(DONE.this
                        ,AddTaskDialogFragment.newInstance(),
                        REQUEST_CODE_ADD_TASK
                        ,FRAGMENT_ADD_TASK_DIALOG);
            }
        });
    }
}