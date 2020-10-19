package com.example.taskmanagerapp.Controller.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TaskBDRepository;
import com.example.taskmanagerapp.Repository.UserDBRepository;
import com.example.taskmanagerapp.ViewElem.DialogView;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddTaskDialogFragment extends DialogFragment {
    public static final String EXTRA_NEW_TASK = "com.example.taskmanagerapp.New Task";
    public static final String ARG_USER_ID = "User Id";
    public static final String ARGS_TASK_STATE = "task state";
    private User mUser = new User();
    private TaskBDRepository mTaskDBRepository;
    private Task mTask;

    public AddTaskDialogFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddTaskDialogFragment newInstance(UUID uuid,TaskState taskState) {
        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, uuid);
        args.putString(ARGS_TASK_STATE,taskState.toString()
        );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID uuid = (UUID)
                getArguments().getSerializable(ARG_USER_ID);
        mUser = UserDBRepository.getInstance(getContext()).get(uuid);
        mTaskDBRepository=
                TaskBDRepository.getInstance(
                        getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DialogView dialogView = new DialogView(container, inflater, R.layout.fragment_add_task_dialog);
        dialogView.findElemAddDialog();
        setListener(dialogView);
        return dialogView.getView();
    }

    private void setListener(DialogView dialogView) {
        dialogView.getButtonOK().setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                mTask = returnNewTask(dialogView);
                mTaskDBRepository.insert(mTask);
                sendData();
                dismiss();
            }
        });

        dialogView.getButtonCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void sendData() {
        Fragment fragment = getTargetFragment();
        Intent data = new Intent();
        data.putExtra(EXTRA_NEW_TASK, mTask);
        fragment.onActivityResult(
                getTargetRequestCode(), Activity.RESULT_OK, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Task returnNewTask(DialogView dialogView) {
        Task newTask = new Task();
        if (!dialogView.getEditTitle().equals(""))
            newTask.setTaskTitle(dialogView.getEditTitle());
        else
            newTask.setTaskTitle("");

        if (!dialogView.getEditContent().equals(""))
            newTask.setTaskContent(dialogView.getEditContent());
        else
            newTask.setTaskContent("");
        //this is  default
        newTask.setTaskState(TaskState.valueOf(getArguments().getString(ARGS_TASK_STATE)));

        if (dialogView.getDatePicker() != null)
            newTask.setTaskDate(getDate(dialogView.getDatePicker()));
        else
            newTask.setTaskDate(new Date());

        if (dialogView.getTimePicker() != null)
            newTask.setTaskTime(getTime(dialogView.getTimePicker()));
        else
            newTask.setTaskTime(new Date());

        newTask.setUserId(mUser.getUUID());
        return newTask;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Date getTime(TimePicker timePicker) {
        Calendar calendar = Calendar.getInstance();
        int hour = timePicker.getHour();
        calendar.set(Calendar.HOUR, hour);
        int minute = timePicker.getMinute();
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTime();
    }

    private Date getDate(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        int year = datePicker.getYear();
        calendar.set(Calendar.YEAR, year);
        int month = datePicker.getMonth();
        calendar.set(Calendar.MONTH, month);
        int dayOfMonth = datePicker.getDayOfMonth();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        return calendar.getTime();
    }
}