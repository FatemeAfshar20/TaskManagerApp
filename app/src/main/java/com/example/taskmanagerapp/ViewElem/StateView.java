package com.example.taskmanagerapp.ViewElem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public  class StateView {

    private View mView;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mButtonAddTask;
    private AppCompatImageView mImgEmpty;

    public StateView(ViewGroup container
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

    public AppCompatImageView getImgEmpty() {
        return mImgEmpty;
    }

    public void findElem(){
        mButtonAddTask=mView.findViewById(R.id.btn_add_task);
        mRecyclerView=mView.findViewById(R.id.recycler_view);
        mImgEmpty=mView.findViewById(R.id.img_empty);
        mImgEmpty.setVisibility(View.GONE);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public FloatingActionButton getButtonAddTask() {
        return mButtonAddTask;
    }


}
