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

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TasksRepository;
import com.example.taskmanagerapp.Repository.UserRepository;
import com.example.taskmanagerapp.Adapter.StateAdapter;
import com.example.taskmanagerapp.ViewElem.StateView;

public class TODO extends Fragment {

    public static final String FRAGMENT_ADD_TASK_DIALOG = "Add Task Dialog";
    public static final int REQUEST_CODE_ADD_TASK = 0;
    public User mUser =
            UserRepository.getInstance().getUserList().get(0);
    public TasksRepository mTasksRepository=
            mUser.getTasksRepository();
    private StateAdapter mStateAdapter;
    private StateView mStateView;

    public TODO() {
        // Required empty public constructor
    }

    public static TODO newInstance() {
        TODO fragment = new TODO();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        if(mTasksRepository.getTODOTaskList().size()==0)
            mStateView.getImgEmpty().setVisibility(View.VISIBLE);
        else
            mStateView.getImgEmpty().setVisibility(View.GONE);

        if(mStateAdapter!=null)
            mStateAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mStateView=new StateView(container,inflater,R.layout.fragment_state);
        mStateView.findElem();
        setListener(mStateView);
        mStateAdapter=new
                StateAdapter(mTasksRepository.getTODOTaskList()
                ,getContext(),getActivity().getSupportFragmentManager());
        mStateView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        mStateView.getRecyclerView().setAdapter(mStateAdapter);
        return mStateView.getView();
    }

    private User getUser() {
        return mUser;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != Activity.RESULT_OK && data == null)
            return;
        if (requestCode == REQUEST_CODE_ADD_TASK) {
            Task task=(Task) data.getSerializableExtra(
                    AddTaskDialogFragment.EXTRA_NEW_TASK);
            mTasksRepository.getStateTODOList().add(task);
            task.setTaskState(TaskState.TODO);
        }
    }


    private void setListener(StateView stateView){

        stateView.getButtonAddTask().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AddTaskDialogFragment addTaskDialogFragment
                            =AddTaskDialogFragment.newInstance();

                    addTaskDialogFragment.setTargetFragment(
                            TODO.this, REQUEST_CODE_ADD_TASK);

                    addTaskDialogFragment.show(
                            getActivity().getSupportFragmentManager(),
                            FRAGMENT_ADD_TASK_DIALOG);
            }
        });

    }
}