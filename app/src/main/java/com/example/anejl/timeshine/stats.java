package com.example.anejl.timeshine;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class stats extends AppCompatActivity {
    ListView listView;
    DBHelper database;
    ArrayList tasks;
    ArrayAdapter<String> tasksAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        database = new DBHelper(this);
        listView=findViewById(R.id.listView);
        tasks=new ArrayList();
        tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listView.setAdapter(tasksAdapter);
        showTasks();

    }

    public void showTasks() {
        tasks.clear();
        Cursor data = database.getStats();
        while (data.moveToNext()) {
                tasks.add(data.getString(0) + " " + data.getString(1) + " " + data.getString(2));
        }
        tasksAdapter.notifyDataSetChanged();
    }
}
