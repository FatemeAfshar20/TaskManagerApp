package com.example.taskmanagerapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapp.Controller.Fragment.BottomSheetFrag;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.util.List;
import java.util.UUID;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.Holder> {
    public static final String
            FRAGMENT_EDIT_DIALOG_FRAGMENT =
            "Edit Dialog Fragment";
    public static final String
            FRAGMENT_SHOW_DIALOG_FRAGMENT =
            "Show Task Dialog Fragment";

    private List<Task> mUserTasks;

    public Context mContext;

    public FragmentManager mFragmentManager;

    private User mUser;

    private AdapterCallbacks mCallback;

    public StateAdapter(List<Task> userTasks, Context context,
                        FragmentManager fragmentManager, AdapterCallbacks callbacks) {
        mUserTasks = userTasks;
        mContext = context;
        mFragmentManager=fragmentManager;
        mCallback =callbacks;
    }

    public List<Task> getUserTasks() {
        return mUserTasks;
    }

    public void setUserTasks(List<Task> userTasks) {
        mUserTasks = userTasks;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_view, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mUserTasks.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserTasks.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private DateFormat mDateFormat =
                DateFormat.getDateInstance(DateFormat.SHORT);
        private AppCompatImageView mTaskImg;
        private AppCompatImageButton mButtonMenu;
        private MaterialTextView mTaskTitle, mTaskContent,
                mTaskInitDate;

        private Task mTask;

        public Holder(@NonNull View itemView) {
            super(itemView);

            findElem(itemView);
            setListener();
        }

        public void findElem(View view) {
            mTaskImg = view.findViewById(R.id.img_task);
            mTaskTitle = view.findViewById(R.id.task_title);
            mTaskContent = view.findViewById(R.id.content);
            mTaskInitDate = view.findViewById(R.id.task_date);
            mButtonMenu=view.findViewById(R.id.btn_menu);
        }

        private void setListener(){
            mButtonMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 mCallback.addBottomSheetFrag(mTask.getUUID());
                }
            });

/*            mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onTaskDeleted(mTask);
                    mOnUpdateUICallback.updateUI();
                }
            });*/
        }


        private void bind(Task task) {
            mTask = task;
            updateUI(task);
        }

        private void updateUI(Task task) {
            mTaskTitle.setText(task.getTaskTitle());
            mTaskContent.setText(task.getTaskContent());
            mTaskInitDate.setText(mDateFormat.format(
                    task.getTaskDate()
            ));
            mCallback.onTaskUpdated(task);
        }
    }


    public interface AdapterCallbacks {

        void onTaskDeleted(Task task);
        void onTaskUpdated(Task task);
        void updateUI();
        void addBottomSheetFrag(UUID taskId);
    }

}
