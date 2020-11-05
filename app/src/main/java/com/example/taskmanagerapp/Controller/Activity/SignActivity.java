package com.example.taskmanagerapp.Controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.taskmanagerapp.Controller.Fragment.SignFragment;
import com.example.taskmanagerapp.Controller.SingleFragmentActivity;

public class SignActivity extends SingleFragmentActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SignActivity.class);
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        return SignFragment.newInstance();
    }
}