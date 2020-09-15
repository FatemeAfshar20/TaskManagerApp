package com.example.taskmanagerapp.Controller.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagerapp.Controller.Fragment.AdminFragment;
import com.example.taskmanagerapp.Controller.SingleFragment;
import com.example.taskmanagerapp.R;

public class AdminActivity extends SingleFragment {

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