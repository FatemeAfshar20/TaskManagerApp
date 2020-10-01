package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerapp.Controller.Activity.LoginActivity;
import com.example.taskmanagerapp.Model.User.Admin;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TaskDBRepository;
import com.example.taskmanagerapp.Repository.UserDBRepository;
import com.example.taskmanagerapp.ViewElem.LoginView;

public class SignFragment extends Fragment {

    private User mUser = new User();
    private UserDBRepository mUserRepository;

    public SignFragment() {
        // Required empty public constructor
    }

    public static SignFragment newInstance() {
        SignFragment fragment = new SignFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserRepository=
                UserDBRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoginView loginView = new LoginView(container, inflater, R.layout.fragment_sign);
        loginView.findElemSign();
        setListener(loginView);
        return loginView.getView();
    }

    private void setListener(final LoginView loginView) {

        loginView.getButtonSign().setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (mUserRepository.
                        userExist(loginView.getUsername())==null) {
                    if (isTrueFormatInput(loginView)) {
                        mUser.setUserName(loginView.getUsername());
                        mUser.setPassword(loginView.getPasswordText());

                        // checking isAdmin or no and setting result
                        if (loginView.getAdminPassword()
                                .equals(Admin.getAdminPass()))
                            mUser.setAdmin(true);

                        mUserRepository.insert(mUser);
                        TaskDBRepository.getInstance(getContext(),mUser.getUUID());
                        LoginActivity.start(getContext(), mUser.getUUID());
                        getActivity().finish();
                    } else
                        LoginView.returnToast(getContext(), R.string.input_check);
                } else
                    LoginView.returnToast(getContext(), "User Already Exist");
            }
        });
    }

    private boolean isTrueFormatInput(LoginView loginView) {
        if (loginView.getUsername().equals("") || loginView.getPasswordText().equals("")) {
            LoginView.returnToast(getActivity(), "Username or password cant be null");
            return false;
        } else if (!isNumeric(loginView.getPasswordText())) {
            LoginView.returnToast(getActivity(), "Password should be number");
            return false;
        } else if (loginView.getPasswordText().length() < 8) {
            LoginView.returnToast(getActivity(), "Password should be more than 8");
            return false;
        } else
            return true;
    }

    private static boolean isNumeric(String strNum) {
        for (char c : strNum.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }
}