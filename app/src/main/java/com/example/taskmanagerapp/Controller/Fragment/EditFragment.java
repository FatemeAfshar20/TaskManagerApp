package com.example.taskmanagerapp.Controller.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TaskBDRepository;
import com.example.taskmanagerapp.Utils.DateUtils;
import com.example.taskmanagerapp.Utils.PhotoUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class EditFragment extends Fragment {
    public static final String ARG_TASK_ID = "Task Id";
    public static final String BUNDLE_DATE_SELECTED = "Date user Selected";
    public static final String BUNDLE_TIME_SELECTED = "Time user Selected";
    public static final String TAG_EDIT_FRAGMENT = "Edit Fragment Tag";
    public static final int REQUEST_CODE_CAMERA = 1;
    public static final String AUTHORITY = "com.example.taskmanagerapp.fileprovider";
    private Task mTask;
    private TaskBDRepository mTaskDBRepository;

    private TextInputEditText mEditTitle,mEditContent;
    private AppCompatImageButton mButtonOK,
            mButtonCancel,mButtonCamera;
    private AppCompatImageView mImageTask;
    private MaterialRadioButton mTodo,mDoing,mDone;

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;

    private MaterialButton mBtnDate,mBtnTime;

    private File mPhotoFile;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(UUID taskId) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID taskId= (UUID) getArguments().get(ARG_TASK_ID);
        saveInstance(savedInstanceState);
        mTaskDBRepository=TaskBDRepository.getInstance(
                getContext());

        mTask =mTaskDBRepository.get(taskId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_edit_task,
                container,
                false);
        findElem(view);
        setListener();
        initView();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!= Activity.RESULT_OK || data==null)
            return;
        if (requestCode==REQUEST_CODE_CAMERA){
            Uri photoUri = FileProvider.getUriForFile(getActivity(),
                    AUTHORITY, mPhotoFile);

            getActivity().revokeUriPermission(photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhoto();
        }
    }

    public void updatePhoto() {
        if (mPhotoFile == null || !mPhotoFile.exists())
            return;

        Bitmap image=  PhotoUtils.getScalePhoto(
                mPhotoFile.getAbsolutePath(), getActivity());

         mImageTask.setImageBitmap(image);
    }

    public void findElem(View view){
        mEditTitle=view.findViewById(R.id.set_title);
        mEditContent=view.findViewById(R.id.set_content);
        mButtonOK=view.findViewById(R.id.btn_ok);
        mButtonCancel=view.findViewById(R.id.btn_cancel);
        mBtnDate=view.findViewById(R.id.set_date);
        mBtnTime=view.findViewById(R.id.set_time);

        mButtonCamera=view.findViewById(R.id.camera);
        mImageTask=view.findViewById(R.id.image_task);

        mTodo=view.findViewById(R.id.todo);
        mDoing=view.findViewById(R.id.doing);
        mDone=view.findViewById(R.id.done);
    }


    private void initView(){
        mEditTitle.setText(mTask.getTaskTitle());
        mEditContent.setText(mTask.getTaskContent());
        mBtnDate.setText(DateUtils.getShortDateFormat(mTask.getTaskDate()));
        mBtnTime.setText(DateUtils.getShortTimeFormat(mTask.getTaskTime()));
        updatePhoto();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveInstance(Bundle bundle) {
        if(bundle!=null) {
            Calendar calendar= (Calendar) bundle.
                    getSerializable(BUNDLE_TIME_SELECTED);
            TimePicker timePicker =new TimePicker(getContext());
            timePicker.setHour(calendar.get(Calendar.HOUR));
            timePicker.setMinute(calendar.get(Calendar.MINUTE));
            mTimePicker=timePicker;
        }
    }

    private void setListener() {

        mButtonOK.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                userChangingTask();
                mTaskDBRepository.update(mTask);
                getActivity().getFragmentManager().popBackStack();
            }
        });

       mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO......
                getActivity().onBackPressed();
            }
        });

       mButtonCamera.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               intentPhoto();
           }
       });

       mBtnDate.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.N)
           @Override
           public void onClick(View v) {
               DatePickerDialog datePickerDialog=new DatePickerDialog(getContext());

               datePickerDialog.show();

               //TODO ....
           }
       });

       mBtnTime.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            //TODO ....
                   }
               },9,28,true);

               timePickerDialog.show();
           }
       });
    }


    private File createImageFile() throws IOException {
        String timeStamp=
                new SimpleDateFormat(
                        "yyyyMMdd_HHmmss").
                        format(new Date());
        String imageName="JPEG"+timeStamp+"_";
        File storageDir =getActivity().getFilesDir();
        File imageFile=File.createTempFile(imageName,
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
                Log.e(TAG_EDIT_FRAGMENT,
                        "Device for Take Picture not found");
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
    private void userChangingTask() {
        if (getEditTitle().equals(""))
            mTask.setTaskTitle(getEditTitle());

        if (!getEditContent().equals(""))
            mTask.setTaskContent(getEditContent());

        if (getTimePicker() != null)
            mTask.setTaskTime(getNewTime(getTimePicker()));

        if (getDatePicker() != null)
            mTask.setTaskDate(
                    getNewDate(getDatePicker()));

        if (isTodo())
            mTask.setTaskState(TaskState.TODO);
        else if (isDoing())
            mTask.setTaskState(TaskState.DOING);
        else if (isDone())
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        /*Date date=getNewDate(getDatePicker());
        outState.putSerializable(BUNDLE_DATE_SELECTED,date);
        TimePicker time=getTimePicker();
        outState.putSerializable(BUNDLE_TIME_SELECTED,
                getCalender(time));*/
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



    public String getEditTitle() {
        return mEditTitle.getText().toString();
    }

    public String getEditContent() {
        return mEditContent.getText().toString();
    }

    public DatePicker getDatePicker() {
        return mDatePicker;
    }

    public TimePicker getTimePicker() {
        return mTimePicker;
    }

    public boolean isTodo() {
        return mTodo.isChecked();
    }

    public boolean isDoing() {
        return mDoing.isChecked();
    }

    public boolean isDone() {
        return mDone.isChecked();
    }
}