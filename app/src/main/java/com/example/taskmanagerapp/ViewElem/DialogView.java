package com.example.taskmanagerapp.ViewElem;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.example.taskmanagerapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class DialogView {
    private View mView;
    private TextInputEditText mEditTitle,mEditContent;
    private MaterialButton mButtonOK,
            mButtonCancel,mButtonClose,mButtonDelete;
    private MaterialRadioButton mTodo,mDoing,mDone;

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;

    private MaterialTextView mShowTitle,mShowContent,
    mShowTime,mShowDate,mShowState;

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
        mButtonOK=mView.findViewById(R.id.dialog_ok_btn);
        mButtonCancel=mView.findViewById(R.id.dialog_cancel_btn);
        mDatePicker=mView.findViewById(R.id.date_picker);
        mTimePicker=mView.findViewById(R.id.time_picker);

        mTodo=mView.findViewById(R.id.todo);
        mDoing=mView.findViewById(R.id.doing);
        mDone=mView.findViewById(R.id.done);
    }

    public void findElemAddDialog(){
        mEditTitle=mView.findViewById(R.id.exist_task_title);
        mEditContent=mView.findViewById(R.id.exist_task_content);
        mButtonOK=mView.findViewById(R.id.exist_dialog_ok_btn);
        mButtonCancel=mView.findViewById(R.id.exist_dialog_cancel_btn);
        mDatePicker=mView.findViewById(R.id.date_picker_exist);
        mTimePicker=mView.findViewById(R.id.time_picker_exist);
    }

    public void findElemShowTaskDialog(){
        mShowTitle=mView.findViewById(R.id.task_title_show);
        mShowContent=mView.findViewById(R.id.task_content_show);
        mShowTime=mView.findViewById(R.id.task_time_show);
        mShowDate=mView.findViewById(R.id.task_date_show);
        mShowState=mView.findViewById(R.id.task_state_show);
        mButtonClose=mView.findViewById(R.id.dialog_close_btn);
      //  mButtonDelete=mView.findViewById(R.id.dialog_delete_btn);
    }

    public MaterialButton getButtonClose() {
        return mButtonClose;
    }

    public MaterialButton getButtonDelete() {
        return mButtonDelete;
    }

    public void setShowTitle(String showTitle) {
        mShowTitle.setText(showTitle);
    }

    public void setShowContent(String showContent) {
        mShowContent.setText(showContent);
    }

    public void setShowTime(String showTime) {
        mShowTime.setText(showTime);
    }

    public void setShowDate(String showDate) {
        mShowDate.setText(showDate);
    }

    public void setShowState(String showState) {
        mShowState.setText(showState);
    }

    public String getEditTitle() {
        return mEditTitle.getText().toString();
    }

    public String getEditContent() {
        return mEditContent.getText().toString();
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

    public boolean isTodo() {
        return mTodo.isChecked();
    }

    public boolean isDoing() {
        return mDoing.isChecked();
    }

    public boolean isDone() {
        return mDone.isChecked();
    }

    public void setDatePicker(DatePicker datePicker) {
        mDatePicker = datePicker;
    }

    public void setTimePicker(TimePicker timePicker) {
        mTimePicker = timePicker;
    }
}
