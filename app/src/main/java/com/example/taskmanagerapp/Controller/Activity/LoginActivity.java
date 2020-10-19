package com.example.taskmanagerapp.Controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmanagerapp.Controller.Fragment.LoginFragment;

import com.example.taskmanagerapp.Controller.SingleFragment;

import java.util.UUID;

public class LoginActivity extends SingleFragment {
    public static final String EXTRA_USER_ID =
            "com.example.taskmanagerapp.User Id";

    public static void start(Context context, UUID userId) {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.putExtra(EXTRA_USER_ID, userId);
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        return LoginFragment.newInstance();
    }
}