package com.example.taskmanagerapp.Controller.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskmanagerapp.Controller.Activity.LoginActivity;
import com.example.taskmanagerapp.Model.Task.TaskState;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TaskBDRepository;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskManagerFragment extends Fragment {
    public static final String ARG_USER_ID = "User Id";
    private List<Fragment> mFragments = new ArrayList<>();

    private User mUser = new User();

    private TaskBDRepository mTasksRepository;

    private String[] mState ={TaskState.TODO.toString(),
            TaskState.DOING.toString(),TaskState.DONE.toString()};
    private Callbacks mCallbacks;

    public TaskManagerFragment() {
        // Required empty public constructor
    }


    public static TaskManagerFragment newInstance(UUID uuid) {
        TaskManagerFragment fragment = new TaskManagerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, uuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_task_maneger,
                container,
                false);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Callbacks)
            mCallbacks = (Callbacks) context;
        else
            throw new ClassCastException(context.toString()
                    + " must implement Callbacks");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAdapter(view);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_delete:
                getDialogOk();
                return true;
            default:
                return false;
        }
    }

    private void getDialogOk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).
                setMessage("Are you sure?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTasksRepository.deleteAll(mUser.getUUID());
                        mCallbacks.updateUI();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class TaskAdapter extends FragmentStateAdapter {

        List<Fragment> mFragmentList;

        public TaskAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
            super(fragmentActivity);
            mFragmentList = fragmentList;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragmentList.size();
        }
    }

    private void setupAdapter(@NonNull View view) {
        ViewPager2 viewPager2 = view.findViewById(R.id.view_pager2);
        TaskAdapter taskAdapter = new TaskAdapter(getActivity(), mFragments);
        viewPager2.setAdapter(taskAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(mState[position])
        ).attach();
    }

    private void addFragment() {
        mTasksRepository = TaskBDRepository.getInstance(getActivity());
        mFragments.add(0, mCallbacks.getStateFragment(mState[0]));
        mFragments.add(1, mCallbacks.getStateFragment(mState[1]));
        mFragments.add(2, mCallbacks.getStateFragment(mState[2]));
    }

    public interface Callbacks {
        void updateUI();
        Fragment getStateFragment(String taskState);
    }
}