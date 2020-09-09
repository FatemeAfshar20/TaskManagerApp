package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.ViewElem.FragmentStateView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DONE#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DONE extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentStateView stateView=new FragmentStateView(container,inflater,R.layout.fragment_state);
        stateView.findElem();
        setListener(stateView);
        return stateView.getView();
    }

    private void setListener(FragmentStateView stateView){

        stateView.getButtonAddTask().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}