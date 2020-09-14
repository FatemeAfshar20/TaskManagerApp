package com.example.taskmanagerapp.Controller.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.taskmanagerapp.Controller.Activity.LoginActivity;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TasksRepository;
import com.example.taskmanagerapp.Repository.UserRepository;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskManagerFragment extends Fragment{

    private ViewPager2 mViewPager2;
    private List<Fragment> mFragments=new ArrayList<>();
    private TaskAdapter mTaskAdapter;
    private User mUser=
            UserRepository.getInstance().getUserList().get(0);
    private TasksRepository mTasksRepository=
            mUser.getTasksRepository();

    public TaskManagerFragment() {
        // Required empty public constructor
    }


    public static TaskManagerFragment newInstance() {
        TaskManagerFragment fragment = new TaskManagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments.add(0, TODO.newInstance());
        mFragments.add(1, DOING.newInstance());
        mFragments.add(2, DONE.newInstance());
        setHasOptionsMenu(true);

        if(mTaskAdapter!=null)
            mTaskAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_maneger, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mViewPager2=view.findViewById(R.id.view_pager2);
/*     mViewPager2.setAdapter(new TaskAdapter(getActivity(),mFragments));*/
        mTaskAdapter=new TaskAdapter(getActivity(),mFragments);
        mViewPager2.setAdapter(mTaskAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, mViewPager2,
                (tab, position) -> tab.setText(mFragments.get(position).getClass().getSimpleName())
        ).attach();

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_logout:
                Intent intent=new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_delete:
                getDialogOk();
                return true;
        }
        return false;
    }

    private void getDialogOk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).
                setMessage("Do you sure?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTasksRepository.deleteAll();
                    }
                })
                .setNegativeButton("Cancel",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class TaskAdapter extends FragmentStateAdapter {

        List<Fragment> mFragmentList;

        public List<Fragment> getFragmentList() {
            return mFragmentList;
        }

        public TaskAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
            super(fragmentActivity);
            mFragmentList=fragmentList;
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
}