package com.example.taskmanagerapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapp.Controller.Fragment.EditDialogFragment;
import com.example.taskmanagerapp.Controller.Fragment.ShowTaskDialogFragment;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;

import com.example.taskmanagerapp.Repository.UserDBRepository;
import com.example.taskmanagerapp.Repository.UserRepository;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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

    public StateAdapter(List<Task> userTasks, Context context,
                        FragmentManager fragmentManager) {
        mUserTasks = userTasks;
        mContext = context;
        mFragmentManager = fragmentManager;
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
        private DateFormat mTimeFormat =
                DateFormat.getTimeInstance(DateFormat.SHORT);

        private AppCompatImageButton mButtonEdit, mButtonShow, mButtonDelete;
        private CircleImageView mTaskImage;
        private Task mTask;
        private MaterialTextView mTaskTitle, mTaskContent,
                mTaskInitTime, mTaskInitDate;

        public Holder(@NonNull View itemView) {
            super(itemView);

            findElem(itemView);
            setListener();
        }

        public void findElem(View view) {
            mTaskImage = view.findViewById(R.id.task_img);
            mTaskTitle = view.findViewById(R.id.task_title);
            mTaskContent = view.findViewById(R.id.task_content);
            mTaskInitDate = view.findViewById(R.id.task_txt_date);
            mTaskInitTime = view.findViewById(R.id.task_txt_time);
            mButtonEdit = view.findViewById(R.id.btn_edit);
            mButtonShow = view.findViewById(R.id.btn_show);
            mButtonDelete = view.findViewById(R.id.btn_delete);
        }

        private void setListener() {
            mButtonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditDialogFragment editDialogFragment =
                            EditDialogFragment.newInstance(mTask);


                    editDialogFragment.show(mFragmentManager,
                            FRAGMENT_EDIT_DIALOG_FRAGMENT);
                }
            });

            mButtonShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowTaskDialogFragment showTaskDialogFragment =
                            ShowTaskDialogFragment.newInstance(mTask);


                    showTaskDialogFragment.show(mFragmentManager,
                            FRAGMENT_SHOW_DIALOG_FRAGMENT);
                }
            });

            mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   mUser.getTaskDBRepository().delete(mTask);
                }
            });
        }

        private void bind(Task task) {
            mTask = task;
            mTaskTitle.setText(task.getTaskTitle());
            mTaskContent.setText(task.getTaskContent());
            mTaskInitDate.setText(mDateFormat.format(
                    task.getTaskDate()
            ));
            mTaskInitTime.setText(mTimeFormat.format(
                    task.getTaskTime()
            ));
        }


        public AppCompatImageButton getButtonEdit() {
            return mButtonEdit;
        }

        public void setButtonEdit(AppCompatImageButton buttonEdit) {
            mButtonEdit = buttonEdit;
        }

        public CircleImageView getTaskImage() {
            return mTaskImage;
        }

        public void setTaskImage(CircleImageView taskImage) {
            mTaskImage = taskImage;
        }
    }
}
