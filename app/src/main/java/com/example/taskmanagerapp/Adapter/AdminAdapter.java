package com.example.taskmanagerapp.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapp.Controller.Activity.TaskManagerActivity;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.IRepository;
import com.example.taskmanagerapp.Repository.UserDBRepository;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.Holder> {
    private List<User> mUserList;
    private Context mContext;
    private IRepository<User> mUserRepository;
    public AdminAdapter(List<User> userList, Context context) {
        mUserList = userList;
        mContext = context;
        mUserRepository= UserDBRepository.getInstance(mContext);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View view=layoutInflater.inflate(R.layout.item_admin_view,parent,false);
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

    class Holder extends RecyclerView.ViewHolder{
        private MaterialTextView mUsername,mNumOfTask,
                mDateMemberShip;
        private AppCompatImageButton mButtonTaskShow,
                mButtonTrashcan;

        private User mUser=new User();

        public Holder(@NonNull View itemView) {
            super(itemView);
            findElem(itemView);
            setListener();
        }

        private void findElem(View view){
            mUsername=view.findViewById(R.id.user__name);
            mNumOfTask=view.findViewById(R.id.num_of_task_show);
            mDateMemberShip=view.findViewById(R.id.date_membership);
            mButtonTaskShow=view.findViewById(R.id.show_all_task);
            mButtonTrashcan=view.findViewById(R.id.delete_task_admin);
        }

        public void bind(User user){
            mUser=user;
            //    TasksRepository tasksRepository=user.getTasksRepository();
/*            int numOfTask=tasksRepository.getTODOTaskList().size()+
                    tasksRepository.getDONETaskList().size()+
                    tasksRepository.getDOINGTaskList().size();*/
            mUsername.setText(user.getUserName());
            //mNumOfTask.setText("Number Of Task : "+numOfTask);
            mDateMemberShip.setText(user.getMembership());
        }

        private void setListener(){
            mButtonTaskShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskManagerActivity.start(mContext,mUser.getUUID());
                }
            });

            mButtonTrashcan.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    mUserRepository.delete(mUser);
                }
            });
        }
    }
}
