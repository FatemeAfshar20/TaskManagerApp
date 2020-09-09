package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskManegerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskManegerFragment extends Fragment {

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_maneger, container, false);
    }
}