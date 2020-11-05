package com.example.taskmanagerapp.Controller.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerapp.Controller.Activity.AdminActivity;
import com.example.taskmanagerapp.Controller.Activity.SignActivity;
import com.example.taskmanagerapp.Controller.Activity.StateActivity;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.UserDBRepository;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    public UserDBRepository mUserRepository ;
    private User mUser;

    private MaterialButton mButtonLogin,mButtonSign;
    private EditText mUsername,mPassword;

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
        View view = inflater.inflate(R.layout.fragment_login,
                container,
                false);
        findElem(view);
        setListener();
        return view;
    }

private void findElem(View view){
    mButtonLogin=view.findViewById(R.id.btn_login);
    mButtonSign=view.findViewById(R.id.btn_sign);
    mUsername=view.findViewById(R.id.username);
    mPassword=view.findViewById(R.id.password);
}

    private void setListener() {

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (checkUserInfo())
                    if (mUser.isAdmin())
                        AdminActivity.start(getContext());
                    else
                        StateActivity.start(getContext(), mUser.getUUID());
            }
        });

        mButtonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignActivity.start(getContext());
            }
        });

    }

    private boolean isCorrectPass(String userPassword, String loginPassword) {
        if (loginPassword.equals(userPassword))
            return true;
        return false;
    }

    private boolean checkUserInfo() {
        if (mUserRepository.userExist(getUsername())) {

            mUser=mUserRepository.get(getUsername());
            if (isCorrectPass(mUser.getPassword(),
                   getPasswordText()))
                return true;
            else
               returnToast(getContext(), R.string.invalid_input);
        } else
            returnToast(getContext(), "At first Sign Up");
        return false;
    }

    public String getUsername() {
        return mUsername.getText().toString();
    }

    public String getPasswordText() {
        return mPassword.getText().toString();
    }

    public static void returnToast(Context context, int msgId){
        Toast.makeText(context,msgId,Toast.LENGTH_LONG)
                .show();
    }

    public static void returnToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG)
                .show();;
    }
}