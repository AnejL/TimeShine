package com.example.anejl.timeshine;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DBHelper database;
    ListView listView;
    ArrayAdapter<String> tasksAdapter;
    ArrayList tasks;
    EditText name;
    EditText duration;

    Button start;
    Button saveStart;
    PopupWindow popUp;
    RadioButton rb1;
    String dbName = "";
    String dbH = "";
    String dbM = "";
    String dbType = "";
    Intent intent;
    private TimePickerDialog.OnTimeSetListener timeSetListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = new DBHelper(this);
        listView = findViewById(R.id.listView);
        tasks = new ArrayList();
        tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listView.setAdapter(tasksAdapter);
        popUp = new PopupWindow(this);
        intent = new Intent(this, Task.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window, null);
                popUp = new PopupWindow(popupView, 800, 1000, true);
                popUp.showAtLocation(view, Gravity.CENTER, 0, 0);

                start = popupView.findViewById(R.id.start);
                saveStart = popupView.findViewById(R.id.saveStart);
                name = popupView.findViewById(R.id.name);
                duration = popupView.findViewById(R.id.duration);
                rb1 = popupView.findViewById(R.id.workout);

                duration.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog dialog = new TimePickerDialog(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth, timeSetListener, 0, 0, true);
                        dialog.show();
                    }
                });

                timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + "h " + minute + "m";
                        duration.setText(time);
                        dbH = hourOfDay + "";
                        dbM = minute + "";
                    }
                };
                saveStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(setupTask()){
                        insertTask(dbName, dbType, dbH, dbM);
                        showTasks();
                        intent.putExtra("name", dbName);
                        intent.putExtra("type", dbType);
                        intent.putExtra("h", Integer.parseInt(dbH));
                        intent.putExtra("m", Integer.parseInt(dbM));
                        startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Please fill out all information!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(setupTask()){
                            intent.putExtra("name", dbName);
                            intent.putExtra("type", dbType);
                            intent.putExtra("h", Integer.parseInt(dbH));
                            intent.putExtra("m", Integer.parseInt(dbM));
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Please fill out all information!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

        showTasks();
    }

    public boolean setupTask() {
        boolean pass=false;
        dbName = name.getText().toString();
        if (rb1.isChecked()) {
            dbType = "Workout";
        } else
            dbType = "Rest";
        if(dbH.length()>0 && dbM.length()>0 && dbName.length()>0){
            pass=true;
        }

        return pass;
    }

    public void insertTask(String name, String type, String h, String m) {
        database.insertTask(name, type, h, m);
    }

    public void showTasks() {
        tasks.clear();
        Cursor data = database.getTasks();
        while (data.moveToNext()) {
            tasks.add(data.getString(0) + " " + data.getString(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4));
        }
        tasksAdapter.notifyDataSetChanged();
    }

    public void deleteTask() {
        //pogledaš kater je biu kliknjen na listView, uzameš uni index, npr 2 (3ji vnos), ta index primerjaš z arralist tasks (tasks.get(2)),
        // in kličeš database.deleteTask(id);
    }

    public void editTask() {
        //isto kot v deleteTask, samo da da zravn daš še duration,name in type.
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
