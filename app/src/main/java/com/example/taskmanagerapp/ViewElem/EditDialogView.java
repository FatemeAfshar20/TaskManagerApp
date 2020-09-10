package com.example.taskmanagerapp.ViewElem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.taskmanagerapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditDialogView {
    private View mView;
    private TextInputEditText mEditTitle,mEditContent,
    mEditState;
    private MaterialButton mButtonOK,mButtonCancel;

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;

    public EditDialogView(ViewGroup container
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

    public void findElem(){
        mEditTitle=mView.findViewById(R.id.edit_title);
        mEditContent=mView.findViewById(R.id.edit_content);
        mEditState=mView.findViewById(R.id.edit_state);
        mButtonOK=mView.findViewById(R.id.dialog_ok_btn);
        mButtonCancel=mView.findViewById(R.id.dialog_cancel_btn);
        mDatePicker=mView.findViewById(R.id.date_picker);
        mTimePicker=mView.findViewById(R.id.time_picker);
    }

    public TextInputEditText getEditTitle() {
        return mEditTitle;
    }

    public void setEditTitle(TextInputEditText editTitle) {
        mEditTitle = editTitle;
    }

    public TextInputEditText getEditContent() {
        return mEditContent;
    }

    public void setEditContent(TextInputEditText editContent) {
        mEditContent = editContent;
    }

    public TextInputEditText getEditState() {
        return mEditState;
    }

    public void setEditState(TextInputEditText editState) {
        mEditState = editState;
    }

    public MaterialButton getButtonOK() {
        return mButtonOK;
    }

    public void setButtonOK(MaterialButton buttonOK) {
        mButtonOK = buttonOK;
    }

    public MaterialButton getButtonCancel() {
        return mButtonCancel;
    }

    public void setButtonCancel(MaterialButton buttonCancel) {
        mButtonCancel = buttonCancel;
    }

    public DatePicker getDatePicker() {
        return mDatePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        mDatePicker = datePicker;
    }

    public TimePicker getTimePicker() {
        return mTimePicker;
    }

    public void setTimePicker(TimePicker timePicker) {
        mTimePicker = timePicker;
    }
}
