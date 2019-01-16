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
        int rating = 0;
        int count = 0;
        tasks.clear();
        Cursor data = database.getStats();
        while (data.moveToNext()) {
            Cursor data1 = database.getTask(data.getString(3));
            data1.moveToFirst();

            rating = rating + data.getInt(2);
            count ++;

            String wr = data1.getString(1);
            if (wr.equals("Workout")){
                wr = "Work:   ";
            }
            else {
                wr = "Rest:     ";
            }

            String task=data1.getString(2) + "h " + data1.getString(3) + "m        " + data1.getString(0) ;
            tasks.add(wr + data.getString(2) + "/5" + "     " + task + "      " + data.getString(1));

        }

        tasksAdapter.notifyDataSetChanged();
        int progress = (rating / count) * 100 / 5;
        pb.setProgress(progress);
    }
}
