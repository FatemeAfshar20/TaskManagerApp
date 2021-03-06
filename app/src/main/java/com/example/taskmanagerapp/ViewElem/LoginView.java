package com.example.taskmanagerapp.ViewElem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanagerapp.R;
import com.google.android.material.button.MaterialButton;

public class LoginView {
    private View mView;
    private MaterialButton mButtonLogin,mButtonSign;
    private EditText mUsername,mPassword,mAdminPassword;

    public LoginView(ViewGroup container
            , LayoutInflater layoutInflater,int layoutId) {
        setView(container,layoutInflater,layoutId);
    }

    private void setView(ViewGroup container,
                         LayoutInflater layoutInflater,int layoutId){
        mView= layoutInflater.inflate(layoutId
                ,container, false);
    }

    public View getView(){
        return mView;
    }

    public MaterialButton getButtonLogin() {
        return mButtonLogin;
    }

    public MaterialButton getButtonSign() {
        return mButtonSign;
    }

    public String getUsername() {
        return mUsername.getText().toString();
    }

    public String getPasswordText() {
        return mPassword.getText().toString();
    }

 public EditText getPassword() {
        return mPassword;
    }

    public String getAdminPassword() {
        return mAdminPassword.getText().toString();
    }

    public void findElemLogin(){
        mButtonLogin=mView.findViewById(R.id.btn_login);
        mButtonSign=mView.findViewById(R.id.btn_sign);
        mUsername=mView.findViewById(R.id.username);
        mPassword=mView.findViewById(R.id.password);
    }

    public void findElemSign(){
        mButtonSign=mView.findViewById(R.id.btn_sign_up);
        mUsername=mView.findViewById(R.id.user_name);
        mPassword=mView.findViewById(R.id.pass);
        mAdminPassword=mView.findViewById(R.id.admin_pass);
    }

    public static void returnToast(Context context,int msgId){
        Toast.makeText(context,msgId,Toast.LENGTH_LONG)
                .show();;
    }

    public static void returnToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG)
                .show();;
    }

}
