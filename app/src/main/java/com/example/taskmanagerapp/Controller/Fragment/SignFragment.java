package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerapp.Controller.Activity.LoginActivity;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.UserDBRepository;
import com.example.taskmanagerapp.ViewElem.LoginView;

import java.util.UUID;

public class SignFragment extends Fragment {

    private User mUser;
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
        mUser = new User();
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
                if (!mUserRepository.
                        userExist(loginView.getUsername())) {
                    if (isTrueFormatInput(loginView)) {
                        mUser.setUsername(loginView.getUsername());
                        mUser.setPassword(loginView.getPasswordText());

                        // checking isAdmin
                        if (loginView.getAdminPassword()
                                .equals("@utab"))
                            mUser.setAdmin(true);

                        mUserRepository.insert(mUser);
                        LoginActivity.start(getContext(), mUser.getUUID());
                        getActivity().finish();
                    } else
                        LoginView.returnToast(getContext(), R.string.input_check);
                } else
                    LoginView.returnToast(getContext(), R.string.user_exist);
            }
        });
    }

    private boolean isTrueFormatInput(LoginView loginView) {
        if (loginView.getUsername().equals("") || loginView.getPasswordText().equals("")) {
            LoginView.returnToast(getActivity(), R.string.cant_null);
            return false;
        } else if (!isNumeric(loginView.getPasswordText())) {
            LoginView.returnToast(getActivity(), R.string.numeric_pass);
            return false;
        } else if (loginView.getPasswordText().length() < 8) {
            LoginView.returnToast(getActivity(), R.string.length_pass);
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