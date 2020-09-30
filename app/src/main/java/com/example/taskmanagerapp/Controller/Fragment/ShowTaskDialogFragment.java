package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.UserRepository;
import com.example.taskmanagerapp.ViewElem.DialogView;

import java.text.DateFormat;


public class ShowTaskDialogFragment extends DialogFragment {
    public static final String ARG_TASK_FOR_SHOW = "Task for show";
    private Task mTask=new Task();
    private DialogView mDialogView;

    public ShowTaskDialogFragment() {
        // Required empty public constructor
    }

    public static ShowTaskDialogFragment newInstance(Task task) {
        ShowTaskDialogFragment fragment = new ShowTaskDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_FOR_SHOW,task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTask= (Task) getArguments().
                getSerializable(ARG_TASK_FOR_SHOW);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDialogView=
                new DialogView(container,inflater,R.layout.fragment_show_task);
        mDialogView.findElemShowTaskDialog();
        setListener(mDialogView);
        initView(mTask);
        return mDialogView.getView();
    }

    private void setListener(DialogView dialogView){
        dialogView.getButtonClose().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        dialogView.getButtonDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initView(Task task){
        mDialogView.setShowTitle(task.getTaskTitle());
        mDialogView.setShowContent(task.getTaskContent());
        mDialogView.setShowState(task.getTaskState().toString());
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        mDialogView.setShowDate(dateFormat.format(task.getTaskDate()));
        dateFormat =DateFormat.getTimeInstance(DateFormat.SHORT);
        mDialogView.setShowTime(dateFormat.format(task.getTaskTime()));
    }
}