package com.example.taskmanagerapp.ViewElem;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.taskmanagerapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class DialogView {
    private View mView;
    private TextInputEditText mEditTitle,mEditContent,
    mEditState;
    private MaterialButton mButtonOK,mButtonCancel;

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;

    public DialogView(ViewGroup container
            , LayoutInflater layoutInflater, int layoutId) {
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

    public void findElemEditDialog(){
        mEditTitle=mView.findViewById(R.id.edit_title);
        mEditContent=mView.findViewById(R.id.edit_content);
        mEditState=mView.findViewById(R.id.edit_state);
        mButtonOK=mView.findViewById(R.id.dialog_ok_btn);
        mButtonCancel=mView.findViewById(R.id.dialog_cancel_btn);
        mDatePicker=mView.findViewById(R.id.date_picker);
        mTimePicker=mView.findViewById(R.id.time_picker);
    }

    public void findElemAddDialog(){
        mEditTitle=mView.findViewById(R.id.exist_task_title);
        mEditContent=mView.findViewById(R.id.exist_task_content);
        mButtonOK=mView.findViewById(R.id.exist_dialog_ok_btn);
        mButtonCancel=mView.findViewById(R.id.exist_dialog_cancel_btn);
        mDatePicker=mView.findViewById(R.id.date_picker_exist);
        mTimePicker=mView.findViewById(R.id.time_picker_exist);
    }

    public String getEditTitle() {
        return mEditTitle.getText().toString();
    }

    public String getEditContent() {
        return mEditContent.getText().toString();
    }

    public String getEditState() {
        return mEditState.getText().toString();
    }

    public MaterialButton getButtonOK() {
        return mButtonOK;
    }

    public MaterialButton getButtonCancel() {
        return mButtonCancel;
    }

    public DatePicker getDatePicker() {
        return mDatePicker;
    }

    public TimePicker getTimePicker() {
        return mTimePicker;
    }

}
