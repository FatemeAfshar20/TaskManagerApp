package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

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

public class EditDialogFragment extends DialogFragment {
    public static final String ARG_OLD_TASK = "Old Task";
    public static final String ARG_USER = "user";
    public static final String BUNDLE_DATE_SELECTED = "Date user Selected";
    public static final String BUNDLE_TIME_SELECTED = "Time user Selected";
    private Task mTask = new Task();
    private Task mOldTask;
    private User mUser ;
    private TaskBDRepository mTaskDBRepository;
    private DialogView mDialogView;
    public EditDialogFragment() {
        // Required empty public constructor
    }

    public static EditDialogFragment newInstance(Task task, UUID uuid) {
        EditDialogFragment fragment = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_OLD_TASK, task);
        args.putSerializable(ARG_USER, uuid);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOldTask = (Task) getArguments().get(ARG_OLD_TASK);
        mUser = UserDBRepository.getInstance(getContext()).get((UUID) getArguments().get(ARG_USER));
        saveInstance(savedInstanceState);
        mTaskDBRepository=TaskBDRepository.getInstance(
                getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDialogView = new DialogView(container, inflater, R.layout.fragment_edit_dialog);
        mDialogView.findElemEditDialog();
        setListener();
        return mDialogView.getView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveInstance(Bundle bundle) {
        if(bundle!=null) {
            Calendar calendar= (Calendar) bundle.
                    getSerializable(BUNDLE_TIME_SELECTED);
            TimePicker timePicker =new TimePicker(getContext());
            timePicker.setHour(calendar.get(Calendar.HOUR));
            timePicker.setMinute(calendar.get(Calendar.MINUTE));
            mDialogView.setTimePicker(timePicker);
        }
    }

    private void setListener() {

        mDialogView.getButtonOK().setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                userChangingTask(mDialogView);

                mTaskDBRepository.update(mOldTask);
                dismiss();
            }
        });

        mDialogView.getButtonCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void userChangingTask(DialogView dialogView) {
        if (!dialogView.getEditTitle().equals(""))
            mOldTask.setTaskTitle(dialogView.getEditTitle());

        if (!dialogView.getEditContent().equals(""))
            mOldTask.setTaskContent(dialogView.getEditContent());

        if (dialogView.getTimePicker() != null)
            mOldTask.setTaskTime(getNewTime(dialogView.
                    getTimePicker()));

        if (dialogView.getDatePicker() != null)
            mOldTask.setTaskDate(
                    getNewDate(dialogView.getDatePicker()));

        if (dialogView.isTodo())
            mOldTask.setTaskState(TaskState.TODO);
        else if (dialogView.isDoing())
            mOldTask.setTaskState(TaskState.DOING);
        else if (dialogView.isDone())
            mOldTask.setTaskState(TaskState.DONE);

      //  mOldTask.setUserId(mUser.getUUID());
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Date date=getNewDate(mDialogView.getDatePicker());
        outState.putSerializable(BUNDLE_DATE_SELECTED,date);
        TimePicker time=mDialogView.getTimePicker();
        outState.putSerializable(BUNDLE_TIME_SELECTED,
                getCalender(time));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Calendar getCalender(TimePicker timePicker){
        Calendar cl=Calendar.getInstance();
        timePicker.setCurrentHour(cl.get(Calendar.HOUR));
        timePicker.setCurrentMinute(cl.get(Calendar.MINUTE));
        return cl;
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