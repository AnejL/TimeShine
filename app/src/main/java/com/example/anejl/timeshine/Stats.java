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
    ArrayList stats;
    //ArrayAdapter<String> statsAdapter;

    //
    StatsAdapter sadp ;
    ArrayList statsID;
    ArrayList<StatsItem> statsItems;
    //

    ProgressBar pb;
    FloatingActionButton home;

    Intent intentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        database = new DBHelper(this);
        listView = findViewById(R.id.listView);
        stats = new ArrayList();


        home = (FloatingActionButton) findViewById(R.id.home);


        statsID = new ArrayList();

        //
        statsItems = new ArrayList<>();
        sadp = new StatsAdapter(this, statsItems);
        listView.setAdapter(sadp);
        //


        pb = findViewById(R.id.pb);
        listView.setAdapter(sadp);


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
        statsItems.clear();
        statsID.clear();
        Cursor dbStats = database.getStats();

        int rating;
        int ratingSum = 0;
        int count = 0;

        String stars;
        //
        Cursor thisTask;

        if (dbStats.moveToFirst()){
            while (dbStats.moveToNext()) {
                StatsItem si = new StatsItem();

                //dobi id taska, ki pripada trenutnemu statsu
                int tid = dbStats.getInt(3);

                //dobi row iz tasks tabele
                thisTask = database.getTask(tid + "");
                thisTask.moveToNext();
                //nastavi ime statsitema, tako kot je ime taska



                si.setName(thisTask.getString(0));

                if (thisTask.getString(1).equals("Workout")) {
                    si.setType("Workout");
                } else {
                    si.setType("Rest");
                }

                statsID.add(dbStats.getString(0));
                si.setComment(dbStats.getString(1));

                stars = "";
                rating = Integer.parseInt(dbStats.getString(2));
                ratingSum += rating;
                count ++;

                for (int i = 0; i < rating; i++){
                    stars += "*";
                }


                si.setRating(stars);
                statsItems.add(si);

            }
            int progress = (ratingSum / count) * 100 / 5;
            pb.setProgress(progress);
            sadp.notifyDataSetChanged();
        }


    }

    /*
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
    }*/
}
