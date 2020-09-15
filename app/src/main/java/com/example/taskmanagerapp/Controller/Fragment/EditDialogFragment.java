package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TasksRepository;
import com.example.taskmanagerapp.Repository.UserRepository;
import com.example.taskmanagerapp.ViewElem.DialogView;

import java.util.Calendar;
import java.util.Date;

public class EditDialogFragment extends DialogFragment {
    public static final String ARG_OLD_USER = "Old User";
    private Task mTask = new Task();
    private Task mOldTask;
    private User mUser =
            UserRepository.getInstance().getUserList().get(0);
    private TasksRepository mTasksRepository =
            mUser.getTasksRepository();

    public EditDialogFragment() {
        // Required empty public constructor
    }

    public static EditDialogFragment newInstance(Task task) {
        EditDialogFragment fragment = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_OLD_USER, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOldTask = (Task) getArguments().get(ARG_OLD_USER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DialogView dialogView = new DialogView(container, inflater, R.layout.fragment_edit_dialog);
        dialogView.findElemEditDialog();
        setListener(dialogView);
        return dialogView.getView();
    }

    private void setListener(DialogView dialogView) {

        dialogView.getButtonOK().setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                userChangingTask(dialogView);

                mTasksRepository.update(mOldTask, mTask);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void userChangingTask(DialogView dialogView) {
        if (dialogView.getEditTitle() != null)
            mTask.setTaskTitle(dialogView.getEditTitle());
        else
            mTask.setTaskTitle(mOldTask.getTaskTitle());

        if (dialogView.getEditContent() != null)
            mTask.setTaskContent(dialogView.getEditContent());
        else
            mTask.setTaskContent(mOldTask.getTaskContent());

        if (dialogView.getTimePicker() != null)
            mTask.setTaskTime(getNewTime(dialogView.
                    getTimePicker()));
        else
            mTask.setTaskTime(mOldTask.getTaskTime());

        if (dialogView.getDatePicker() != null)
            mTask.setTaskDate(
                    getNewDate(dialogView.getDatePicker()));
        else
            mTask.setTaskDate(mOldTask.getTaskDate());

        if (dialogView.isTodo())
            mTask.setTaskState(TaskState.TODO);
       else if (dialogView.isDoing())
            mTask.setTaskState(TaskState.DOING);
       else if (dialogView.isDone())
            mTask.setTaskState(TaskState.DONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Date getNewTime(TimePicker timePicker) {
        Calendar calendar = Calendar.getInstance();
        int hour = timePicker.getHour();
        calendar.set(Calendar.HOUR, hour);
        int minute = timePicker.getMinute();
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTime();
    }

    private Date getNewDate(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        int year = datePicker.getYear();
        calendar.set(Calendar.YEAR, year);
        int month = datePicker.getMonth();
        calendar.set(Calendar.MONTH, month);
        int dayOfMonth = datePicker.getDayOfMonth();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        return calendar.getTime();
    }

    private TaskState getNewState(String str) {
        switch (str.toLowerCase()) {
            case "todo":
                return TaskState.TODO;
            case "doing":
                return TaskState.DOING;
            case "done":
                return TaskState.DONE;
            default:
                return null;
        }
    }
}