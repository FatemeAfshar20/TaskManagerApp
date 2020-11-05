package com.example.taskmanagerapp.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapp.Controller.Fragment.BottomSheetAdminFragment;
import com.example.taskmanagerapp.Controller.Fragment.BottomSheetFrag;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.IRepository;
import com.example.taskmanagerapp.Repository.TaskBDRepository;
import com.example.taskmanagerapp.Repository.UserDBRepository;
import com.example.taskmanagerapp.Utils.DateUtils;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;
import java.util.UUID;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.Holder> {
    private List<User> mUserList;
    private Context mContext;
    private IRepository<User> mUserRepository;
    private BottomSheetFragmentShow mCallbacks;

    public AdminAdapter(List<User> userList, Context context
            ,BottomSheetFragmentShow callbacks) {
        mUserList = userList;
        mContext = context;
        mUserRepository = UserDBRepository.getInstance(mContext);
        mCallbacks=callbacks;
    }

    public List<User> getUserList() {
        return mUserList;
    }

    public void setUserList(List<User> userList) {
        mUserList = userList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_admin_view, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private MaterialTextView mUsername, mNumOfTask,
                mDateMemberShip;
        private AppCompatImageButton mButtonMenu;

        private User mUser = new User();

        public Holder(@NonNull View itemView) {
            super(itemView);
            findElem(itemView);
            setListener();
        }

        private void findElem(View view) {
            mUsername = view.findViewById(R.id.user_name);
            mNumOfTask = view.findViewById(R.id.num_task_user);
            mDateMemberShip = view.findViewById(R.id.date_membership);
            mButtonMenu=view.findViewById(R.id.btn_menu);
        }

        public void bind(User user) {
            mUser = user;
            TaskBDRepository taskDBRepository =
                    TaskBDRepository.getInstance(mContext);
            int numOfTask = taskDBRepository.
                    getUserTask(mUser.getUUID()).size();
            mUsername.setText(user.getUsername());
            mNumOfTask.setText(
                    mNumOfTask.getText().toString() + numOfTask);
            mDateMemberShip.setText(
                    mDateMemberShip.getText().toString()+
                            DateUtils.getShortDateFormat(user.getMemberShip()));
        }

        private void setListener() {
            mButtonMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        mCallbacks.getBottomSheetFrag(mUser.getUUID());
                }
            });
        }
    }
    public interface BottomSheetFragmentShow{
        void getBottomSheetFrag(UUID userId);
    }
}
