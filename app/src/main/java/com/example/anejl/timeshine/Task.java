package com.example.anejl.timeshine;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Task extends AppCompatActivity {
    DBHelper database;
    int h;
    int m;
    String name;
    String type;
    TextView countdown;
    TextView percentage;
    TextView task_name;
    int seconds;
    int fixedSeconds;
    int id;
    Timer timer;
    PopupWindow popUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Intent intent=getIntent();
        h=intent.getIntExtra("h",0);
        m=intent.getIntExtra("m",0);
        name=intent.getStringExtra("name");
        type=intent.getStringExtra("type");
        id=intent.getIntExtra("id",0);
        countdown=findViewById(R.id.countdown);
        percentage=findViewById(R.id.percentage);
        task_name=findViewById(R.id.name);
        seconds = h*3600+m*60;
        fixedSeconds=seconds;
        database = new DBHelper(this);

        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                countDown();
            }
        },0,1000);


    }

    public void countDown(){
        seconds-=10;
        countdown.setText(String.format("%02d",seconds/3600)+":"+String.format("%02d",(seconds%3600)/60)+":"+String.format("%02d",seconds%60));
        if(seconds==0){
            stats();
        }
        percentage.setText((seconds*100)/fixedSeconds+"%");
        task_name.setText(name);
    }

    public void stats(){
        timer.cancel();
        timer.purge();
        database.insertStat(id,"neki");
        Intent intent = new Intent(this, stats.class);
        startActivity(intent);
    }
}
