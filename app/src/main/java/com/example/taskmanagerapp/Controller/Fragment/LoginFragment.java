package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.Controller.Activity.LoginActivity;
import com.example.taskmanagerapp.Controller.Activity.SignActivity;
import com.example.taskmanagerapp.Controller.SingleFragment;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.UserRepository;
import com.example.taskmanagerapp.ViewElem.LoginView;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    public static final String ARG_USER_ID = "User Id";
    public UserRepository mUserRepository = UserRepository.getInstance();
    private UUID mUserId;
    private User mUser;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(UUID userId) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = (UUID) getArguments().getSerializable(ARG_USER_ID);
        if(mUserId != null)
            mUser=mUserRepository.get(mUserId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoginView loginView = new LoginView(container, inflater, R.layout.fragment_login);
        loginView.findElemLogin();
        setListener(loginView);
        return loginView.getView();
    }


    private void setListener(final LoginView loginView) {

        loginView.getButtonLogin().setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (getNewUser(loginView).equals(mUser)) {
                    if(mUser.isAdmin())
                        LoginView.returnToast(getContext(),"You are Admin");
                } else
                    LoginView.returnToast(getContext(), R.string.invalid_input);
            }
        });

        loginView.getButtonSign().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignActivity.start(getContext());
            }
        });

    }

    private User getNewUser(LoginView loginView) {

        return new User(loginView.getUsername().getText().toString(),
                loginView.getPassword().getText().toString());
    }
}