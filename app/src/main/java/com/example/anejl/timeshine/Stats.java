package com.example.anejl.timeshine;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class Stats extends AppCompatActivity {
    ListView listView;
    DBHelper database;
    ArrayList tasks;
    ArrayAdapter<String> tasksAdapter;
    ProgressBar pb;
    FloatingActionButton home;

    Intent intentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        database = new DBHelper(this);
        listView = findViewById(R.id.listView);
        tasks = new ArrayList();
        home = (FloatingActionButton) findViewById(R.id.home);
        tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        pb = findViewById(R.id.pb);
        listView.setAdapter(tasksAdapter);
        showTasks();
        intentMain = new Intent(this, MainActivity.class);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentMain);
            }
        });

    }

    public void showTasks() {
        float rating = 0;
        int count = 0;
        tasks.clear();
        Cursor data = database.getStats();
        while (data.moveToNext()) {
            Cursor data1 = database.getTask(data.getString(3));
            data1.moveToFirst();

            //rating = rating + data1.getInt(2);
            count ++;

            String task=data1.getString(0) + " " + data1.getString(1) + " " + data1.getString(2) + " " + data1.getString(3);
            tasks.add(data.getString(0) + " " + data.getString(1) + " " + task);

        }

        tasksAdapter.notifyDataSetChanged();

        pb.setProgress(33);
    }
}
