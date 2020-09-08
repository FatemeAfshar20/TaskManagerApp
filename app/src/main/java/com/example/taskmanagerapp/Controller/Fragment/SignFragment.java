package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.Controller.Activity.LoginActivity;
import com.example.taskmanagerapp.Model.User.Admin;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.ViewElem.LoginView;

public class SignFragment extends Fragment {

    private User mUser=new User();

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoginView loginView=new LoginView(container,inflater,R.layout.fragment_sign);
        loginView.findElemSign();
        setListener(loginView);
        return loginView.getView();
    }

    private void setListener(final LoginView loginView){

        loginView.getButtonSign().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTrueFormatInput(loginView)){
                    mUser.setUserName(loginView.getUsername().getText().toString());
                    mUser.setPassword(loginView.getPassword().getText().toString());

                    // checking isAdmin or no and setting result
                    if(loginView.getAdminPassword().getText().
                            toString().equals(Admin.getAdminPass()))
                        mUser.setAdmin(true);

                    User.addInRepository(mUser);
                    LoginActivity.start(getContext(),mUser.getUUID());
                }else
                    LoginView.returnToast(getContext(),R.string.input_check);
            }
        });

        loginView.getPassword().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LoginView.returnToast(getContext(),R.string.notify_pass_text);
            }
        });
    }

    private boolean isTrueFormatInput(LoginView loginView) {
        if(loginView.getUsername().getText().toString().equals("") || loginView.getPassword().getText().toString().equals("")
        || !isNumeric(loginView.getPassword().getText().toString()))
            return false;
        else
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