package com.example.taskmanagerapp.Controller;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerapp.Databese.TaskManagerSchema;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.TaskDBRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SearchResultActivity extends AppCompatActivity {
    TaskDBRepository mTaskDBRepository = new
            TaskDBRepository(this, UUID.randomUUID());
    private List<String> mTaskTitles = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        handleIntent(getIntent());
    }

    private List<String> getLists(Cursor cursor) {
        List<String> taskList = new ArrayList<>();

        if (cursor == null || cursor.getCount() == 0)
            return taskList;

        try {

            while (!cursor.isAfterLast()) {
                cursor.moveToFirst();
                String results = cursor.getString(
                        cursor.getColumnIndex(TaskManagerSchema.Task.TaskColumns.TITLE)
                );
                taskList.add(results);
                cursor.moveToNext();
            }

            return taskList;

        } finally {
            cursor.close();
        }
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor cursor = mTaskDBRepository.getWordMatches(query, null);
            mTaskTitles = getLists(cursor);
        }
    }
}