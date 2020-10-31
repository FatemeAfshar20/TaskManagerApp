package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TaskBDRepository;
import com.example.taskmanagerapp.Utils.DateUtils;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.util.UUID;


public class ShowTaskFragment extends DialogFragment {
    public static final String ARG_TASK_FOR_SHOW = "Task for show";
    private Task mTask;
    private UUID mTaskId;

    private AppCompatImageView mImgTask;
    private MaterialTextView mShowTitle,mShowContent,
            mShowTime,mShowDate,mShowState;

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
        initView(mTask);
        return view;
    }

    public void findElem(View view){
        mShowTitle=view.findViewById(R.id.title_show);
        mShowContent=view.findViewById(R.id.content_show);
        mShowTime=view.findViewById(R.id.time_show);
        mShowDate=view.findViewById(R.id.date_show);
        mShowState=view.findViewById(R.id.state_show);
        mImgTask=view.findViewById(R.id.img_show);
    }

    private void initView(Task task){
        mShowTitle.setText(task.getTaskTitle());
        mShowContent.setText(task.getTaskContent());
        mShowState.setText(task.getTaskState().toString());
        mShowDate.setText(DateUtils.getShortDateFormat(task.getTaskDate()));
        mShowTime.setText(DateUtils.getShortTimeFormat(task.getTaskTime()));
    }
}