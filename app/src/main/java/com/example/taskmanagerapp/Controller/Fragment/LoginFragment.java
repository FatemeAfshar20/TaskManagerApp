package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.ViewElem.LoginView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private User mUser=new User();

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        // Inflate the layout for this fragment
        LoginView loginView = new LoginView(container,inflater,R.layout.fragment_login);
        loginView.findElemLogin();
        setListener(loginView);
        return loginView.getView();
    }

private void setListener(final LoginView loginView){

        loginView.getButtonLogin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.setUserName(loginView.getUsername().getText().toString());
                mUser.setPassword(loginView.getPassword().getText().toString());
            }
        });
}

}