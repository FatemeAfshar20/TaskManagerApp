package com.example.taskmanagerapp.Controller.Fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.taskmanagerapp.Adapter.StateAdapter;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TasksRepository;
import com.example.taskmanagerapp.Repository.UserRepository;
import com.example.taskmanagerapp.ViewElem.StateView;

import java.util.List;

/**
 * this class is for MANAGING All State Fragment
 * and Restrain Repeating yourself in each State Fragment
 */
public abstract class StateManagerFragment extends Fragment {
    public static final String FRAGMENT_ADD_TASK_DIALOG = "Add Task Dialog";
    public static final int REQUEST_CODE_ADD_TASK = 0;
    private User mUser =
            UserRepository.getInstance().getUserList().get(0);
    private TasksRepository mTasksRepository =
            mUser.getTasksRepository();
    private StateAdapter mStateAdapter;
    private StateView mStateView;


    /**
     * use this method in onResumed method,
     * the method give {List<Task>} for deciding
     * about {Image Empty} Visibility,
     * and updating Adapter
     * @param taskList
     */
    public void manageOnResumed(List<Task> taskList) {
        if (taskList.size() == 0)
            mStateView.getImgEmpty().setVisibility(View.VISIBLE);
        else
            mStateView.getImgEmpty().setVisibility(View.GONE);

        if (mStateAdapter != null)
            mStateAdapter.notifyDataSetChanged();
    }

    public void manageRecyclerView(List<Task> taskList) {
        mStateAdapter = new
                StateAdapter(taskList
                , getContext(), getActivity().getSupportFragmentManager());
        mStateView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        mStateView.getRecyclerView().setAdapter(mStateAdapter);
    }

    public void manageReceiveDataFromAddDialog(
            Intent data, List<Task> taskList, TaskState taskState) {
        Task task = (Task) data.getSerializableExtra(
                AddTaskDialogFragment.EXTRA_NEW_TASK);
        taskList.add(task);
        task.setTaskState(taskState);
    }

    public void manageDialogFragment(Fragment fragment,
                                     DialogFragment dialogFragment,
                                     int requestCode, String tag) {
        dialogFragment.setTargetFragment(
                fragment, requestCode);

        dialogFragment.show(
                getActivity().getSupportFragmentManager(),
                tag);
    }

    public void manageView(LayoutInflater inflater, ViewGroup container) {
        mStateView = new StateView(container, inflater, R.layout.fragment_state);
        mStateView.findElem();
    }

    public abstract void setListener(StateView stateView);

    public TasksRepository getTasksRepository() {
        return mTasksRepository;
    }

    public StateView getStateView() {
        return mStateView;
    }

    public User getUser() {
        return mUser;
    }
}
