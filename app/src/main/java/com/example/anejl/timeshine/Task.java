package com.example.anejl.timeshine;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
    ImageView iw;
    int percents;
    int seconds;
    int fixedSeconds;
    int id;

    int iwHeight = 10;

    Timer timer;
    PopupWindow popUp;
    View parent;
    View popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Intent intent=getIntent();
        parent=findViewById(R.id.taskView);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_stats,null);
        popUp = new PopupWindow(popupView, 800, 1000, true);


        h=intent.getIntExtra("h",0);
        m=intent.getIntExtra("m",0);
        name=intent.getStringExtra("name");
        type=intent.getStringExtra("type");
        id=intent.getIntExtra("id",0);
        countdown=findViewById(R.id.countdown);
        percentage=findViewById(R.id.percentage);
        task_name=findViewById(R.id.taskname);
        seconds = h*3600+m*60;
        fixedSeconds=seconds;
        database = new DBHelper(this);
<<<<<<< HEAD
=======

        iw = (ImageView) findViewById(R.id.iw2);



>>>>>>> cbb6b20858c48cfc951afe10838076b236568d23
        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                countDown();
                moveImageUp();
            }
        },0,1000);


    }

    public void moveImageUp(){
        percents = Integer.parseInt(percentage.getText().toString().split("%")[0]);


        iwHeight = 2000 * percents / 100 - 300;
        iw.setY(iwHeight);

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
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                popUp.showAtLocation(parent, Gravity.CENTER, 0, 0);
            }
        });


        database.insertStat(id,"neki");
        Intent intent = new Intent(this, Stats.class);

    }
}
