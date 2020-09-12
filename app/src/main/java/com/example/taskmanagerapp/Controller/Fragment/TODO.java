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
import com.example.taskmanagerapp.Repository.UserRepository;
import com.example.taskmanagerapp.Adapter.StateAdapter;
import com.example.taskmanagerapp.ViewElem.FragmentStateView;

import javax.xml.parsers.SAXParser;

public class TODO extends Fragment {

    public static final String FRAGMENT_ADD_TASK_DIALOG = "Add Task Dialog";
    public static final int REQUEST_CODE_ADD_TASK = 0;
    public UserRepository mUserRepository = UserRepository.getInstance();
    private StateAdapter mStateAdapter;

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
        if(mStateAdapter!=null)
            mStateAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentStateView stateView=new FragmentStateView(container,inflater,R.layout.fragment_state);
        stateView.findElem();
        setListener(stateView);
        mStateAdapter=new
                StateAdapter(getUser().getTODOTaskList()
                ,getContext(),getActivity().getSupportFragmentManager());
        stateView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        stateView.getRecyclerView().setAdapter(mStateAdapter);
        return stateView.getView();
    }

    private User getUser() {
        return mUserRepository.getUserList().get(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != Activity.RESULT_OK && data == null)
            return;
        if (requestCode == REQUEST_CODE_ADD_TASK) {
            Task task=(Task) data.getSerializableExtra(
                    AddTaskDialogFragment.EXTRA_NEW_TASK);
            mUserRepository.
                    getUserList().get(0).getStateTODO().add(task);
            task.setTaskState(TaskState.TODO);
        }
    }


    private void setListener(FragmentStateView stateView){

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