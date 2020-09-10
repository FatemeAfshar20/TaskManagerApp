package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.ViewElem.EditDialogView;

import java.util.Calendar;
import java.util.Date;

public class EditDialogFragment extends DialogFragment {
    private EditDialogView mDialogView;
    private Task mTask=new Task();

    public EditDialogFragment() {
        // Required empty public constructor
    }

    public static EditDialogFragment newInstance() {
        EditDialogFragment fragment = new EditDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDialogView = new EditDialogView(container, inflater, R.layout.fragment_edit_dialog);
        mDialogView.findElem();
        setListener(mDialogView);
        return mDialogView.getView();
    }

    private void setListener(EditDialogView dialogView) {

        dialogView.getButtonOK().setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                userChangingTask(dialogView);
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
    private void userChangingTask(EditDialogView dialogView) {
        if (dialogView.getEditTitle() != null)
            mTask.setTaskTitle(dialogView.getEditTitle().
                    getText().toString());
        if (dialogView.getEditContent() != null)
            mTask.setTaskContent(dialogView.getEditContent().
                    getText().toString());
        if (dialogView.getTimePicker() != null)
            mTask.setTaskTime(getNewTime(dialogView.
                    getTimePicker()));
        if (dialogView.getDatePicker() != null)
            mTask.setTaskDate(
                    getNewDate(dialogView.getDatePicker()));
        if(dialogView.getEditState() != null)
            mTask.setTaskState(getNewState(
                    dialogView.getEditState().toString()));
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

    private TaskState getNewState(String str){
        switch (str.toLowerCase()){
            case "todo":
                return TaskState.TODO;
            case  "doing":
                return TaskState.DOING;
            case "done":
                return TaskState.DONE;
            default:
                return null;
        }
    }
}