package com.example.taskmanagerapp.ViewElem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public  class FragmentStateView {

    private View mView;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mButtonAddTask;

    public FragmentStateView(ViewGroup container
            , LayoutInflater layoutInflater, int layoutId) {
        setView(container,layoutInflater,layoutId);
    }

    private void setView(ViewGroup container,
                         LayoutInflater layoutInflater,int layoutId){
        mView= layoutInflater.inflate(layoutId
                ,container, false);
    }

    public View getView(){
        return mView;
    }

    public void findElem(){
        mButtonAddTask=mView.findViewById(R.id.btn_add_task);
        mRecyclerView=mView.findViewById(R.id.recycler_view);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public FloatingActionButton getButtonAddTask() {
        return mButtonAddTask;
    }

    public void setButtonAddTask(FloatingActionButton buttonAddTask) {
        mButtonAddTask = buttonAddTask;
    }

}
