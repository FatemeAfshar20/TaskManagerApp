package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.UserRepository;
import com.example.taskmanagerapp.StateAdapter.TODOAdapter;
import com.example.taskmanagerapp.ViewElem.FragmentStateView;

public class TODO extends Fragment {


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentStateView stateView=new FragmentStateView(container,inflater,R.layout.fragment_state);
        stateView.findElem();
        setListener(stateView);

        stateView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        stateView.getRecyclerView().setAdapter(new TODOAdapter(UserRepository.getInstance().getUserList().get(0),getContext()));
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