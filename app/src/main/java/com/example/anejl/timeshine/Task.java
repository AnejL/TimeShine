package com.example.anejl.timeshine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Task extends AppCompatActivity {
    int h;
    int m;
    String name;
    String type;
    TextView countdown;
    TextView percentage;
    int seconds;
    int fixedSeconds;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent=getIntent();
        h=intent.getIntExtra("h",0);
        m=intent.getIntExtra("m",0);
        name=intent.getStringExtra("name");
        type=intent.getStringExtra("type");
        countdown=findViewById(R.id.countdown);
        percentage=findViewById(R.id.percentage);
        seconds = h*3600+m*60+1;
        fixedSeconds=seconds;
        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                countDown();
            }
        },0,1000);

    }

    public void countDown(){
        seconds--;
        countdown.setText(String.format("%02d",seconds/3600)+":"+String.format("%02d",(seconds%3600)/60)+":"+String.format("%02d",seconds%60));
        if(seconds==0){
            timer.cancel();
            timer.purge();
        }
        percentage.setText((seconds*100)/fixedSeconds+"%");

    }
}
