package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerapp.Controller.Activity.AdminActivity;
import com.example.taskmanagerapp.Controller.Activity.SignActivity;
import com.example.taskmanagerapp.Controller.Activity.TaskManagerActivity;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.UserDBRepository;
import com.example.taskmanagerapp.ViewElem.LoginView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    public UserDBRepository mUserRepository ;
    private LoginView mLoginView;
    private User mUser;

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
        mUserRepository= UserDBRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mLoginView = new LoginView(container, inflater, R.layout.fragment_login);
        mLoginView.findElemLogin();
        setListener(mLoginView);
        return mLoginView.getView();
    }


    private void setListener(final LoginView loginView) {

        loginView.getButtonLogin().setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (checkUserInfo())
                    if (mUser.isAdmin())
                        AdminActivity.start(getContext());
                    else
                        TaskManagerActivity.start(getContext(), mUser.getUUID());
                getActivity().finish();
            }
        });

        loginView.getButtonSign().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignActivity.start(getContext());
                getActivity().finish();
            }
        });

    }

    private boolean isCorrectPass(String userPassword, String loginPassword) {
        if (loginPassword.equals(userPassword))
            return true;
        return false;
    }

    private boolean checkUserInfo() {
        if (mUserRepository.userExist(mLoginView.getUsername())!=null) {
            mUser=mUserRepository.userExist(mLoginView.getUsername());
            if (isCorrectPass(mUser.getPassword(),
                    mLoginView.getPasswordText()))
                return true;
            else
                LoginView.returnToast(getContext(), R.string.invalid_input);
        } else
            LoginView.returnToast(getContext(), "At first Sign Up");
        return false;
    }
}