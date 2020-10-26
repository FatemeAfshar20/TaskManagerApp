package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TaskBDRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.util.UUID;


public class ShowTaskFragment extends DialogFragment {
    public static final String ARG_TASK_FOR_SHOW = "Task for show";
    private Task mTask;
    private UUID mTaskId;

    private MaterialTextView mShowTitle,mShowContent,
            mShowTime,mShowDate,mShowState;
    private MaterialButton mButtonClose;

    public ShowTaskFragment() {
        // Required empty public constructor
    }

    public static ShowTaskFragment newInstance(UUID taskId) {
        ShowTaskFragment fragment = new ShowTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_FOR_SHOW,taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskId= (UUID) getArguments().
                getSerializable(ARG_TASK_FOR_SHOW);

        mTask= TaskBDRepository.
                getInstance(getContext()).get(mTaskId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_show_task,
                container,
                false);
        findElem(view);
        setListener();
        initView(mTask);
        return view;
    }

    public void findElem(View view){
        mShowTitle=view.findViewById(R.id.task_title_show);
        mShowContent=view.findViewById(R.id.task_content_show);
        mShowTime=view.findViewById(R.id.task_time_show);
        mShowDate=view.findViewById(R.id.task_date_show);
        mShowState=view.findViewById(R.id.task_state_show);
        mButtonClose=view.findViewById(R.id.dialog_close_btn);
    }

    private void setListener(){
        mButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initView(Task task){
        mShowTitle.setText(task.getTaskTitle());
        mShowContent.setText(task.getTaskContent());
        mShowState.setText(task.getTaskState().toString());
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        mShowDate.setText(dateFormat.format(task.getTaskDate()));
        dateFormat =DateFormat.getTimeInstance(DateFormat.SHORT);
        mShowTime.setText(dateFormat.format(task.getTaskTime()));
    }
}