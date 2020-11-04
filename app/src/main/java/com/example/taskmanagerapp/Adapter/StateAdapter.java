package com.example.taskmanagerapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapp.Controller.Fragment.EditDialogFragment;
import com.example.taskmanagerapp.Controller.Fragment.ShowTaskDialogFragment;
import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.Model.User.User;
import com.example.taskmanagerapp.R;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.Holder> implements Filterable {
    public static final String
            FRAGMENT_EDIT_DIALOG_FRAGMENT =
            "Edit Dialog Fragment";
    public static final String
            FRAGMENT_SHOW_DIALOG_FRAGMENT =
            "Show Task Dialog Fragment";

    private List<Task> mUserTasks;
    private List<Task> mSearchTasks;

    public Context mContext;

    public FragmentManager mFragmentManager;

    private  OnTaskClickedListener mCallback;

    private OnUpdateUI mOnUpdateUICallback;

    public StateAdapter(List<Task> userTasks, Context context,
                        FragmentManager fragmentManager, OnTaskClickedListener callback,OnUpdateUI onUpdateUICallback) {
        mUserTasks = userTasks;
        mContext = context;
        mFragmentManager=fragmentManager;
        mCallback =callback;
        mOnUpdateUICallback =onUpdateUICallback;
        mSearchTasks=new ArrayList<>(userTasks);
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


    private Filter mFilterTask=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Task> filterTaskList=new ArrayList<>();
            if (constraint==null || constraint.length()==0)
                filterTaskList.addAll(mSearchTasks);
            else {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (Task task : mSearchTasks) {
                    if (task.getTaskTitle().contains(filterPattern) || task.getTaskContent().contains(filterPattern))
                        filterTaskList.add(task);
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filterTaskList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                mUserTasks.clear();
                mUserTasks.addAll((List) results.values);

                notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return mFilterTask;
    }

    public class Holder extends RecyclerView.ViewHolder {

        private DateFormat mDateFormat =
                DateFormat.getDateInstance(DateFormat.SHORT);
        private DateFormat mTimeFormat =
                DateFormat.getTimeInstance(DateFormat.SHORT);

        private AppCompatImageButton mButtonEdit
                ,mButtonShow,mButtonDelete;
        private Task mTask;
        private MaterialTextView mTaskTitle, mTaskContent,
                mTaskInitTime, mTaskInitDate,mTaskImg;

        public Holder(@NonNull View itemView) {
            super(itemView);

            findElem(itemView);
            setListener();
        }

        public void findElem(View view) {
            mTaskImg = view.findViewById(R.id.task_img);
            mTaskTitle = view.findViewById(R.id.task_title);
            mTaskContent = view.findViewById(R.id.task_content);
            mTaskInitDate = view.findViewById(R.id.task_txt_date);
            mTaskInitTime = view.findViewById(R.id.task_txt_time);
            mButtonEdit = view.findViewById(R.id.btn_edit);
            mButtonShow=view.findViewById(R.id.btn_show);
            mButtonDelete=view.findViewById(R.id.btn_delete);
        }

        private void setListener(){
            mButtonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditDialogFragment editDialogFragment=
                            EditDialogFragment.newInstance(mTask);

                    editDialogFragment.show(mFragmentManager,
                            FRAGMENT_EDIT_DIALOG_FRAGMENT);
                }
            });

            mButtonShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowTaskDialogFragment showTaskDialogFragment=
                            ShowTaskDialogFragment.newInstance(mTask.getUUID());


                    showTaskDialogFragment.show(mFragmentManager,
                            FRAGMENT_SHOW_DIALOG_FRAGMENT);
                    mOnUpdateUICallback.updateUI();
                }
            });


            mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onTaskDeleted(mTask);
                    mOnUpdateUICallback.updateUI();
                }
            });
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
            mTaskInitTime.setText(mTimeFormat.format(
                    task.getTaskTime()
            ));
            mTaskImg.setText(task.getTaskTitle().substring(0,1));

            mCallback.onTaskUpdated(task);
        }

    }


    public interface OnTaskClickedListener{

        void onTaskDeleted(Task task);
        void onTaskUpdated(Task task);

    }

    public interface OnUpdateUI{
        void updateUI();
    }
}
