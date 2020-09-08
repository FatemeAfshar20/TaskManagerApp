package com.example.taskmanagerapp.Controller.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagerapp.Controller.Fragment.SignFragment;
import com.example.taskmanagerapp.Controller.SingleFragment;

import java.util.UUID;

public class SignActivity extends SingleFragment {

    public static void start(Context context) {
        Intent starter = new Intent(context, SignActivity.class);
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        return SignFragment.newInstance();
    }
}