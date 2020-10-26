package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetFrag extends BottomSheetDialogFragment {

    public static final String ARGS_TASK_ID = "Task Id";
    private UUID mTaskId;
    public BottomSheetFrag() {
        // Required empty public constructor
    }

    public static BottomSheetFrag newInstance(UUID taskId, TaskState taskState) {
        BottomSheetFrag fragment = new BottomSheetFrag();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_ID,taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskId= (UUID) getArguments().get(ARGS_TASK_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_sheet_fragment, container, false);
    }
}