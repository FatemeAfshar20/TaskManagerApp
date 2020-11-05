package com.example.taskmanagerapp.Controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.taskmanagerapp.Controller.Fragment.AdminFragment;
import com.example.taskmanagerapp.Controller.SingleFragmentActivity;

public class AdminActivity extends SingleFragmentActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, AdminActivity.class);
        //starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        return AdminFragment.newInstance();
    }
}