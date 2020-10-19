package com.example.taskmanagerapp.Controller.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.Adapter.StateAdapter;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TaskBDRepository;
import com.example.taskmanagerapp.Repository.UserDBRepository;
import com.example.taskmanagerapp.ViewElem.StateView;

import java.util.UUID;

public class StateFragment extends Fragment {
    private static final String ARG_STATE = "State";
    public static final String ARG_USER_LOGIN_ID = "User Login Id";
    public static final String FRAGMENT_ADD_TASK_DIALOG = "Add Task Dialog";
    public static final int REQUEST_CODE_ADD_Task = 1;
    private UUID mUserId;
    private StateView mStateView;
    private StateAdapter mStateAdapter;
    private TaskBDRepository mTaskRepository;

    private String mStrTaskState;

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
        mStateView=new StateView(container,inflater,
                R.layout.fragment_state);

        mStateView.findElem();
        setListener();
        setupAdapter();
        return mStateView.getView();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        if (mTaskRepository.getTaskStateList(mUserId,mStrTaskState).size() == 0)
            mStateView.getImgEmpty().setVisibility(View.VISIBLE);
        else
            mStateView.getImgEmpty().setVisibility(View.GONE);
        if (mStateAdapter != null)
            mStateAdapter.setUserTasks(mTaskRepository.
                    getTaskStateList(mUserId,mStrTaskState));
        mStateAdapter.notifyDataSetChanged();
    }


    private void setupAdapter() {
        mStateAdapter=new StateAdapter(
                mTaskRepository.getTaskStateList(mUserId,mStrTaskState)
                , getContext(), getFragmentManager(),
                new StateAdapter.OnTaskClickedListener() {
            @Override
            public void onTaskDeleted(Task task) {
                mTaskRepository.delete(task);
                mTaskRepository.notifyAll();
            }

            @Override
            public void onTaskUpdated(Task task) {
                mTaskRepository.update(task);
            }
        }, new StateAdapter.OnUpdateUI() {
            @Override
            public void updateUI() {

            }
        });

        mStateView.getRecyclerView().setLayoutManager(
                new LinearLayoutManager(getContext()));

        mStateView.getRecyclerView().setAdapter(mStateAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_CANCELED || data==null)
            return;
        if(requestCode==REQUEST_CODE_ADD_Task){
            Task task = (Task) data.getSerializableExtra(
                    AddTaskDialogFragment.EXTRA_NEW_TASK);
            mTaskRepository.update(task);
            updateUI();
        }
    }

    private void setListener(){
        mStateView.getButtonAddTask().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialogFragment addTask=
                        AddTaskDialogFragment.newInstance(mUserId,TaskState.valueOf(mStrTaskState));
                addTask.setTargetFragment(
                        StateFragment.this, REQUEST_CODE_ADD_Task);
                addTask.show(getActivity().getSupportFragmentManager(),
                        FRAGMENT_ADD_TASK_DIALOG);
            }
        });
    }
}