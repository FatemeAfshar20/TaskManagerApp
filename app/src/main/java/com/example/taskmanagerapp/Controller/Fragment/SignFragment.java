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

import com.example.taskmanagerapp.Controller.Activity.LoginActivity;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.UserDBRepository;
import com.google.android.material.button.MaterialButton;

public class SignFragment extends Fragment {

    private User mUser;
    private UserDBRepository mUserRepository;

    private MaterialButton mButtonLogin, mButtonSign;
    private EditText mUsername, mPassword, mAdminPassword;

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
        mUserRepository =
                UserDBRepository.getInstance(getActivity());
        mUser = new User();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign,
                container,
                false);
        findElem(view);
        setListener();
        return view;
    }

    public void findElem(View view) {
        mButtonSign = view.findViewById(R.id.btn_sign_up);
        mUsername = view.findViewById(R.id.user_name);
        mPassword = view.findViewById(R.id.pass);
        mAdminPassword = view.findViewById(R.id.admin_pass);
    }

    private void setListener() {

        mButtonSign.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (!mUserRepository.
                        userExist(getUsername())) {
                    if (isTrueFormatInput()) {
                        mUser.setUsername(getUsername());
                        mUser.setPassword(getPasswordText());

                        // checking isAdmin
                        if (getAdminPassword()
                                .equals("@utab"))
                            mUser.setAdmin(true);

                        mUserRepository.insert(mUser);
                        LoginActivity.start(getContext(), mUser.getUUID());
                        getActivity().finish();
                    } else
                        returnToast(getContext(), R.string.input_check);
                } else
                    returnToast(getContext(), R.string.user_exist);
            }
        });
    }

    private boolean isTrueFormatInput() {
        if (getUsername().equals("") || getPasswordText().equals("")) {
            returnToast(getActivity(), R.string.cant_null);
            return false;
        } else if (!isNumeric(getPasswordText())) {
            returnToast(getActivity(), R.string.numeric_pass);
            return false;
        } else if (getPasswordText().length() < 8) {
            returnToast(getActivity(), R.string.length_pass);
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

    public String getUsername() {
        return mUsername.getText().toString();
    }

    public String getPasswordText() {
        return mPassword.getText().toString();
    }

    public String getAdminPassword() {
        return mAdminPassword.getText().toString();
    }

    public static void returnToast(Context context, int msgId) {
        Toast.makeText(context, msgId, Toast.LENGTH_LONG)
                .show();
        ;
    }

    public static void returnToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG)
                .show();
        ;
    }
}