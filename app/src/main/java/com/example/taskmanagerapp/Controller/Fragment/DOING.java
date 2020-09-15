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

import java.util.UUID;

public class DOING extends StateManagerFragment {
    public static final String ARG_USER_ID = "User Id";
    private static final int REQUEST_CODE_ADD_TASK = 0;
    private static final String FRAGMENT_ADD_TASK_DIALOG = "Adding new task";
    private UUID mUUID;
    public DOING() {
        // Required empty public constructor
    }

    public static DOING newInstance(UUID uuid) {
        DOING fragment = new DOING();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID,uuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

    manageOnResumed(
            getTasksRepository().getDOINGTaskList());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUUID=(UUID) getArguments().
                getSerializable(ARG_USER_ID);
        setUser(mUUID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        manageView(inflater, container);
        setListener(getStateView());
        manageRecyclerView(
                getTasksRepository().getDOINGTaskList());
        return getStateView().getView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != Activity.RESULT_OK && data == null)
            return;
        if (requestCode == REQUEST_CODE_ADD_TASK) {
            manageReceiveDataFromAddDialog(data,
                    getTasksRepository().getDOINGTaskList(),
                    TaskState.DOING);
        }
    }

    @Override
    public void setListener(StateView stateView) {
            stateView.getButtonAddTask().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manageDialogFragment(DOING.this
                            ,AddTaskDialogFragment.newInstance(mUUID),
                            REQUEST_CODE_ADD_TASK
                            ,FRAGMENT_ADD_TASK_DIALOG);
                }
            });
    }
}