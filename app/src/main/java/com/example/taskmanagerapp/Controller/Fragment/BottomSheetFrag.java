package com.example.taskmanagerapp.Controller.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TaskBDRepository;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetFrag extends BottomSheetDialogFragment {

    public static final String ARGS_TASK_ID = "Task Id";
    private UUID mTaskId;

    private MaterialButton mBtnMore,
            mBtnEdit,mBtnShare,mBtnDelete;

    private onClickItem mCallback;

    private TaskBDRepository mRepository;
    private Task mTask=new Task();

    public BottomSheetFrag() {
        // Required empty public constructor
    }

    public static BottomSheetFrag newInstance(UUID taskId, TaskState taskState) {
        BottomSheetFrag fragment = new BottomSheetFrag();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_ID,taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof onClickItem)
            mCallback=(onClickItem) context;
        else
            throw new ClassCastException(
                    "Must be implement OnAddingTask interface");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskId= (UUID) getArguments().get(ARGS_TASK_ID);

        mRepository= TaskBDRepository.getInstance(getContext());
        mTask=mRepository.get(mTaskId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=
                inflater.inflate(
                        R.layout.bottom_sheet_fragment,
                        container,
                        false);

        findView(view);
        setListener();
        return view;
    }

    private void findView(View view){
        mBtnMore =view.findViewById(R.id.more);
        mBtnShare=view.findViewById(R.id.share);
        mBtnEdit=view.findViewById(R.id.edit);
        mBtnDelete=view.findViewById(R.id.delete);
    }

    private void setListener(){
        mBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mCallback.onClickMoreBtn(mTaskId);
                    dismiss();
            }
        });

        mBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=ShareCompat.IntentBuilder
                        .from(getActivity()).
                                setText(getInfoTask()).
                                setType("text/plain").getIntent();

                if (intent.resolveActivity(getActivity().getPackageManager())!=null)
                    startActivity(intent);

                dismiss();
            }
        });

        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onClickEditBtn(mTaskId);
                dismiss();
            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mCallback.onClickDeleteBtn(mTask);
                dismiss();
            }
        });
    }

    private String getInfoTask(){

        return getString(R.string.all_task_info,
                mTask.getTaskTitle(),
                mTask.getTaskContent(),
                mTask.getTaskState().toString(),
                DateFormat.getDateInstance(DateFormat.SHORT).
                        format(mTask.getTaskDate()),
                DateFormat.getTimeInstance(DateFormat.SHORT).
                        format(mTask.getTaskTime()));
    }

    public interface onClickItem{
        void onClickMoreBtn(UUID taskId);
        void onClickEditBtn(UUID taskId);
        void onClickDeleteBtn(Task task);
    }
}