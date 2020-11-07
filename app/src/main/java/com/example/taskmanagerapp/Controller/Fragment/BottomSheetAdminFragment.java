package com.example.taskmanagerapp.Controller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.Controller.Activity.StateActivity;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.IRepository;
import com.example.taskmanagerapp.Repository.UserDBRepository;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.UUID;

public class BottomSheetAdminFragment extends BottomSheetDialogFragment {
    public static final String ARGS_USER_ID = "User Id";
    private MaterialButton mBtnUserTasks,mBtnDelete;
    private IRepository<User> mRepository;

    private User mUser;
    public BottomSheetAdminFragment() {
        // Required empty public constructor
    }

    public static BottomSheetAdminFragment newInstance(UUID userId) {
        BottomSheetAdminFragment fragment = new BottomSheetAdminFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER_ID,userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository= UserDBRepository.getInstance(getContext());

        UUID uuid= (UUID) getArguments().get(ARGS_USER_ID);
        mUser=mRepository.get(uuid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(
                R.layout.bottom_sheet_admin,
                container,
                false);
        findViews(view);
        setListener();
        return view;
    }

    private void findViews(View view){
        mBtnUserTasks=view.findViewById(R.id.tasks_user);
        mBtnDelete=view.findViewById(R.id.delete);
    }

    private void setListener(){
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mRepository.delete(mUser);
                    dismiss();
            }
        });

        mBtnUserTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StateActivity.start(getContext(),mUser.getUUID());
            }
        });
    }
}