package com.example.taskmanagerapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapp.Model.Task.Task;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Utils.DateUtils;
import com.example.taskmanagerapp.Utils.PhotoUtils;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.Holder> implements Filterable {
    private List<Task> mUserTasks;
    private List<Task> mSearchTask;

    public Context mContext;

    private AdapterCallbacks mCallback;

    public StateAdapter(List<Task> userTasks, Context context, AdapterCallbacks callbacks) {
        mUserTasks = userTasks;
        mContext = context;
        mCallback =callbacks;
        mSearchTask=new ArrayList<>(userTasks);
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

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List<Task> filterTaskList=new ArrayList<>();
           if (constraint==null || constraint.length()==0){
               filterTaskList.addAll(mSearchTask);
           }else {
               String filterPattern=constraint.toString().toLowerCase().trim();

               for (Task task : mSearchTask) {
                   if (task.getTaskTitle().toLowerCase().
                           contains(filterPattern) ||
                           task.getTaskTitle().toLowerCase().
                                   contains(filterPattern)){
                       filterTaskList.add(task);
                   }
               }
           }

           FilterResults results=new FilterResults();
           results.values=filterTaskList;

           return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                mUserTasks.clear();
                mUserTasks.addAll((List)results.values);
                notifyDataSetChanged();
        }
    };

    public class Holder extends RecyclerView.ViewHolder {

        private AppCompatImageView mTaskImg;
        private AppCompatImageButton mButtonMenu;
        private MaterialTextView mTaskTitle, mTaskContent,
                mTaskInitDate;
        private AppCompatImageView mImageTask;

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
            mImageTask=view.findViewById(R.id.img_task);
        }

        private void setListener(){
            mButtonMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 mCallback.addBottomSheetFrag(mTask.getUUID());
                }
            });
        }


        private void bind(Task task) {
            mTask = task;
            updateUI(task);
        }

        public void updateUI(Task task) {
            mTaskTitle.setText(task.getTaskTitle());
            mTaskContent.setText(task.getTaskContent());
            mTaskInitDate.setText(DateUtils.getShortDateFormat(
                    task.getTaskDate()
            ));
            Bitmap bitmap=PhotoUtils.getScalePhoto(
                    mTask.getImgAddress(),
                    (Activity) mContext);
            mImageTask.setImageBitmap(bitmap);
            mCallback.onTaskUpdated(task);
        }
    }

    public interface AdapterCallbacks {
        void onTaskUpdated(Task task);
        void addBottomSheetFrag(UUID taskId);
    }

}
