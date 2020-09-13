package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskManegerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskManegerFragment extends Fragment {

    private ViewPager2 mViewPager2;
    private List<Fragment> mFragments=new ArrayList<>();

    public TaskManegerFragment() {
        // Required empty public constructor
    }


    public static TaskManegerFragment newInstance() {
        TaskManegerFragment fragment = new TaskManegerFragment();
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
        mViewPager2.setAdapter(new TaskAdapter(getActivity(),mFragments));
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, mViewPager2,
                (tab, position) -> tab.setText(mFragments.get(position).getClass().getSimpleName())
        ).attach();

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