package com.example.taskmanagerapp.Controller;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.taskmanagerapp.R;

public abstract class SingleFragment extends AppCompatActivity {

    public abstract Fragment getFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.fragment_container);

        if(fragment==null)
            fragmentManager.
                    beginTransaction().
                    add(R.id.fragment_container,getFragment()).
                   commit() ;
    }
}
