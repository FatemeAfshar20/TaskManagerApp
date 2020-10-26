package com.example.taskmanagerapp.Controller.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TaskBDRepository;
import com.example.taskmanagerapp.Repository.UserDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddTaskFragment extends Fragment {
    public static final String EXTRA_NEW_TASK = "com.example.taskmanagerapp.New Task";
    public static final String ARG_USER_ID = "User Id";
    public static final String ARGS_TASK_STATE = "task state";
    private User mUser = new User();
    private TaskBDRepository mTaskDBRepository;
    private Task mTask;

    private TextInputEditText mEditTitle, mEditContent;
    private AppCompatImageButton mButtonOK,
            mButtonCancel;

    private MaterialButton mBtnSetDate, mBtnSetTime;

    private Toolbar mToolbar;

    private OnBackPressed mCallback;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragment newInstance(UUID userId, TaskState taskState) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, userId);
        args.putString(ARGS_TASK_STATE, taskState.toString()
        );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof  OnBackPressed)
            mCallback=(OnBackPressed) context;
        else throw new
                ClassCastException(
                        "Must Be impelement OnBackPressed");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID uuid = (UUID)
                getArguments().getSerializable(ARG_USER_ID);
        mUser = UserDBRepository.getInstance(getContext()).get(uuid);
        mTaskDBRepository =
                TaskBDRepository.getInstance(
                        getContext());

        getActivity().setActionBar(mToolbar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task,
                container,
                false);
        findElem(view);
        setListener();
        return view;
    }

    public void findElem(View view) {
        mEditTitle = view.findViewById(R.id.set_title);
        mEditContent = view.findViewById(R.id.set_content);
        mButtonOK = view.findViewById(R.id.btn_ok);
        mButtonCancel = view.findViewById(R.id.btn_cancel);
        mBtnSetDate = view.findViewById(R.id.set_date);
        mBtnSetTime = view.findViewById(R.id.set_time);

        mToolbar = view.findViewById(R.id.toolbar);
    }

    private void setListener() {
        mButtonOK.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                mTask = returnNewTask();
                mTaskDBRepository.insert(mTask);
                mCallback.finishFragment(mTask.getTaskState());
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.finishFragment(mTask.getTaskState());
            }
        });

        mBtnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ...
            }
        });

        mBtnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ...
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Task returnNewTask() {
        Task newTask = new Task();
        if (!getEditTitle().equals(""))
            newTask.setTaskTitle(getEditTitle());
        else
            newTask.setTaskTitle("");

        if (!getEditContent().equals(""))
            newTask.setTaskContent(getEditContent());
        else
            newTask.setTaskContent("");
        newTask.setTaskState(TaskState.valueOf(getArguments().getString(ARGS_TASK_STATE)));

        newTask.setTaskDate(new Date());
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

    public String getEditTitle() {
        return mEditTitle.getText().toString();
    }

    public String getEditContent() {
        return mEditContent.getText().toString();
    }

    public interface OnBackPressed {
        void finishFragment(TaskState taskState);
    }

}