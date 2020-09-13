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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DONE#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DONE extends Fragment {
    private static final int REQUEST_CODE_ADD_TASK = 3;
    private static final String FRAGMENT_ADD_TASK_DIALOG ="Adding task" ;
    private User mUser =
            UserRepository.getInstance().getUserList().get(0);
    private TasksRepository mTasksRepository=
            mUser.getTasksRepository();
    private StateAdapter mStateAdapter;
    private StateView mStateView;

    public DONE() {
        // Required empty public constructor
    }

    public static DONE newInstance() {
        DONE fragment = new DONE();
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

        if(mTasksRepository.getDONETaskList().size()==0)
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
        mStateAdapter=
                new StateAdapter(mTasksRepository.getDONETaskList(),
                        getContext(),getActivity()
                        .getSupportFragmentManager());

        mStateView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        mStateView.getRecyclerView().setAdapter(mStateAdapter);
        return mStateView.getView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != Activity.RESULT_OK && data == null)
            return;
        if (requestCode == REQUEST_CODE_ADD_TASK) {
            Task task=(Task) data.getSerializableExtra(
                    AddTaskDialogFragment.EXTRA_NEW_TASK);
            mTasksRepository.getStateDONEList().add(task);
            task.setTaskState(TaskState.DONE);
        }
    }

    private User getUser() {
        return mUser;
    }

    private void setListener(StateView stateView){

        stateView.getButtonAddTask().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AddTaskDialogFragment addTaskDialogFragment
                            =AddTaskDialogFragment.newInstance();

                    addTaskDialogFragment.setTargetFragment(
                            DONE.this, REQUEST_CODE_ADD_TASK);

                    addTaskDialogFragment.show(
                            getActivity().getSupportFragmentManager(),
                            FRAGMENT_ADD_TASK_DIALOG);
                }
        });
    }
}