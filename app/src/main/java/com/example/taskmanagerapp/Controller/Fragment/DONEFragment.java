package com.example.taskmanagerapp.Controller.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DONEFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DONEFragment extends Fragment {
    public static final String FRAGMENT_ADD_TASK_DIALOG = "Add Task Dialog";
    public static final String ARG_USER_LOGIN_ID = "User Login Id";
    public static final int REQUEST_CODE_ADD_Task = 1;
    private UUID mUserId;
    private User mUser;
    private StateView mStateView;
    private StateAdapter mStateAdapter;
    private TaskBDRepository mTaskRepository;


    public DONEFragment() {
        // Required empty public constructor
    }

    public static DONEFragment newInstance(UUID userId) {
        DONEFragment fragment = new DONEFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_LOGIN_ID,userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mUserId= (UUID) getArguments().
                getSerializable(ARG_USER_LOGIN_ID);

        mTaskRepository=
                TaskBDRepository.getInstance(getContext());

        mUser= UserDBRepository.
                getInstance(getContext()).get(mUserId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mStateView=new StateView(container,inflater,
                R.layout.fragment_state);

        mStateView.findElem();
        setListener();
        mStateAdapter=new StateAdapter(
                mTaskRepository.
                        getDONELists(mUserId),getContext(),
                getFragmentManager());

        mStateView.getRecyclerView().setLayoutManager(
                new LinearLayoutManager(getContext()));

        mStateView.getRecyclerView().setAdapter(mStateAdapter);
        return mStateView.getView();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        if (mTaskRepository.getDONELists(mUserId).size() == 0)
            mStateView.getImgEmpty().setVisibility(View.VISIBLE);
        else
            mStateView.getImgEmpty().setVisibility(View.GONE);
        if (mStateAdapter != null)
            mStateAdapter.setUserTasks(mTaskRepository.getDONELists(mUserId));
        mStateAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_CANCELED || data==null)
            return;
        if(requestCode==REQUEST_CODE_ADD_Task){
            Task task = (Task) data.getSerializableExtra(
                    AddTaskDialogFragment.EXTRA_NEW_TASK);
            task.setTaskState(TaskState.DONE);
            mTaskRepository.update(task);
            updateUI();
        }
    }

    private void setListener(){
        mStateView.getButtonAddTask().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskDialogFragment addTask=
                        AddTaskDialogFragment.newInstance(mUserId);
                addTask.setTargetFragment(
                        DONEFragment.this, REQUEST_CODE_ADD_Task);
                addTask.show(getActivity().getSupportFragmentManager(),
                        FRAGMENT_ADD_TASK_DIALOG);
            }
        });
    }
}