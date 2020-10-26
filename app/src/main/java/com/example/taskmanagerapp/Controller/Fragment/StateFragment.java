package com.example.taskmanagerapp.Controller.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.Adapter.StateAdapter;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TaskBDRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.UUID;

public class StateFragment extends Fragment {
    private static final String ARG_STATE = "State";
    public static final String ARG_USER_LOGIN_ID = "User Login Id";
    public static final int REQUEST_CODE_ADD_Task = 1;
    private UUID mUserId;
    private StateAdapter mStateAdapter;
    private TaskBDRepository mTaskRepository;

    private String mStrTaskState;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mButtonAddTask;
    private AppCompatImageView mImgEmpty;

    private OnAddingTask mCallbacks;

    public StateFragment() {
        // Required empty public constructor
    }

    public static StateFragment newInstance(String taskState,UUID userLoginId) {
        StateFragment fragment = new StateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STATE, taskState);
        args.putSerializable(ARG_USER_LOGIN_ID,userLoginId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof  OnAddingTask)
            mCallbacks=(OnAddingTask) context;
        else
            throw new ClassCastException(
                    "Must be implement OnAddingTask interface");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId= (UUID) getArguments().
                getSerializable(ARG_USER_LOGIN_ID);

        mTaskRepository=
                TaskBDRepository.getInstance(getContext());

        mStrTaskState=
                getArguments().getString(ARG_STATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_state,
               container,
               false);

        findElem(view);
        setListener();
        setupAdapter();
        return view;
    }

    public void findElem(View view){
        mButtonAddTask=view.findViewById(R.id.btn_add_task);
        mRecyclerView=view.findViewById(R.id.recycler_view);
        mImgEmpty=view.findViewById(R.id.img_empty);
        mImgEmpty.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        if (mTaskRepository.getTaskStateList(mUserId,mStrTaskState).size() == 0)
            mImgEmpty.setVisibility(View.VISIBLE);
        else
            mImgEmpty.setVisibility(View.GONE);
        updateAdapter();
    }

    private void updateAdapter() {
        if (mStateAdapter != null)
            mStateAdapter.setUserTasks(mTaskRepository.
                    getTaskStateList(mUserId,mStrTaskState));
        mStateAdapter.notifyDataSetChanged();
    }


    private void setupAdapter() {
        mStateAdapter=new StateAdapter(
                mTaskRepository.getTaskStateList(mUserId,mStrTaskState)
                , getContext(),
                new StateAdapter.AdapterCallbacks() {
                    @Override
                    public void onTaskUpdated(Task task) {

                    }

                    @Override
                    public void addBottomSheetFrag(UUID taskId) {
                        BottomSheetFrag bottomSheetFrag=
                                BottomSheetFrag.newInstance(taskId,TaskState.valueOf(mStrTaskState));

                        String tag = " Fragment Bottom Sheet";
                        bottomSheetFrag.show(getActivity().getSupportFragmentManager(), tag);
                    }
                });

        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        mRecyclerView.setAdapter(mStateAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_CANCELED || data==null)
            return;
        if(requestCode==REQUEST_CODE_ADD_Task){
            Task task = (Task) data.getSerializableExtra(
                    AddTaskFragment.EXTRA_NEW_TASK);
            mTaskRepository.update(task);
            updateUI();
        }
    }

    private void setListener(){
        mButtonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onClickAddTask(TaskState.valueOf(mStrTaskState));
            }
        });
    }

   public interface OnAddingTask{
        void onClickAddTask(TaskState taskState);
    }
}