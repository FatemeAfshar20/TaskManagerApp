package com.example.taskmanagerapp.Controller.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TaskBDRepository;
import com.example.taskmanagerapp.Repository.UserDBRepository;
import com.example.taskmanagerapp.Utils.PhotoUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddTaskFragment extends Fragment {
    public static final String EXTRA_NEW_TASK = "com.example.taskmanagerapp.New Task";
    public static final int REQUEST_CODE_CAMERA = 1;
    public static final String AUTHORITY = "com.example.taskmanagerapp.fileprovider";
    public static final String TAG = "Add Task Fragment Tag";
    public static final String ARG_USER_ID = "User Id";
    public static final String ARGS_TASK_STATE = "task state";
    private User mUser = new User();
    private TaskBDRepository mTaskDBRepository;
    private Task mTask = new Task();

    private TextInputEditText mEditTitle, mEditContent;
    private AppCompatImageButton mButtonOK,
            mButtonCancel, mButtonCamera;

    private AppCompatImageView mImageTask;

    private File mPhotoFile;

    private MaterialButton mBtnSetDate, mBtnSetTime;

    private Toolbar mToolbar;

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

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task,
                container,
                false);
        findElem(view);
        setListener();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        getActivity().setActionBar(toolbar);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_CAMERA) {
            Uri photoUri = FileProvider.getUriForFile(getActivity(),
                    AUTHORITY, mPhotoFile);

            getActivity().revokeUriPermission(photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            mTask.setImgAddress(mPhotoFile.getAbsolutePath());
            updatePhoto();
        }
    }

    public void updatePhoto() {
        if (mPhotoFile == null || !mPhotoFile.exists())
            return;

        Bitmap image = PhotoUtils.getScalePhoto(
                mPhotoFile.getAbsolutePath(),
                mImageTask.getHeight(),
                mImageTask.getWidth());

        mImageTask.setImageBitmap(image);
    }

    public void findElem(View view) {
        mEditTitle = view.findViewById(R.id.set_title);
        mEditContent = view.findViewById(R.id.set_content);
        mButtonOK = view.findViewById(R.id.btn_ok);
        mButtonCancel = view.findViewById(R.id.btn_cancel);
        mBtnSetDate = view.findViewById(R.id.set_date);
        mBtnSetTime = view.findViewById(R.id.set_time);

        mButtonCamera = view.findViewById(R.id.camera);
        mImageTask = view.findViewById(R.id.image_task);

        mToolbar = view.findViewById(R.id.toolbar);
    }

    private void setListener() {
        mButtonOK.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                mTask = returnNewTask();
                mTaskDBRepository.insert(mTask);
                getActivity().finish();
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mBtnSetDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        return false;
                    }
                });
                datePickerDialog.show();
            }
        });

        mBtnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mTask.setTaskTime(getTime(view));
                    }
                }, 9, 28, true);

                timePickerDialog.show();
            }
        });

        mButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentPhoto();
            }
        });
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat(
                        "yyyyMMdd_HHmmss").
                        format(new Date());
        String imageName = "JPEG" + timeStamp + "_";
        File storageDir = getActivity().getFilesDir();
        File imageFile = File.createTempFile(imageName,
                ".jpg",
                storageDir);
        mTask.setImgAddress(imageFile.getAbsolutePath());
        return imageFile;
    }

    private void intentPhoto() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getActivity().getPackageManager()) != null) {
            mPhotoFile = null;
            try {
                mPhotoFile = createImageFile();
            } catch (IOException e) {
                Log.e(TAG,
                        "Error occurred while creating the File : "+e.getMessage());
            }
        }
        if (mPhotoFile != null) {
            Uri photoUri = FileProvider.getUriForFile(getActivity(),
                    AUTHORITY, mPhotoFile);
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePicture, REQUEST_CODE_CAMERA);
        }
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
        if (!mPhotoFile.getAbsolutePath().equals(null))
            newTask.setImgAddress(mPhotoFile.getAbsolutePath());
        else
            newTask.setImgAddress("");
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

}