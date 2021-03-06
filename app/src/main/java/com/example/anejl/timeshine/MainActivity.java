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
import android.widget.AdapterView;
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
    ArrayList<TaskItem> taskItems;
    ArrayList tasksID;
    EditText name;
    EditText duration;

    Button insert;
    Button start;
    Button saveStart;
    PopupWindow popUp;
    RadioButton rb1;
    RadioButton rb2;
    String dbName = "";
    String dbH = "";
    String dbM = "";
    String dbType = "";
    Intent intent;
    Intent intentStat;

    CustomListAdapter cadp;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = new DBHelper(this);

        listView = findViewById(R.id.listView);
        tasksID = new ArrayList();

        //
        taskItems = new ArrayList<>();
        cadp = new CustomListAdapter(this, taskItems);
        listView.setAdapter(cadp);
        //

        popUp = new PopupWindow(this);
        intent = new Intent(this, Task.class);
        intentStat = new Intent(this, Stats.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton stats = (FloatingActionButton) findViewById(R.id.stats);
        FloatingActionButton clear = (FloatingActionButton) findViewById(R.id.clear);

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentStat);

            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.restart();
                showTasks();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window, null);
                popUp = new PopupWindow(popupView, 800, 1100, true);
                popUp.showAtLocation(view, Gravity.CENTER, 0, 0);

                start = popupView.findViewById(R.id.start);
                saveStart = popupView.findViewById(R.id.saveStart);
                name = popupView.findViewById(R.id.name);
                duration = popupView.findViewById(R.id.duration);
                rb1 = popupView.findViewById(R.id.workout);
                insert = popupView.findViewById(R.id.insert);

                declarePicker();

                saveStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (setupTask()) {
                            insertTask(dbName, dbType, dbH, dbM, "true");
                            showTasks();
                            int index = database.getMaxID();
                            intent.putExtra("id", index);
                            intent.putExtra("name", dbName);
                            intent.putExtra("type", dbType);
                            intent.putExtra("h", Integer.parseInt(dbH));
                            intent.putExtra("m", Integer.parseInt(dbM));
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Please fill out all information!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (setupTask()) {
                            insertTask(dbName, dbType, dbH, dbM, "false");
                            int index = database.getMaxID();
                            startTask(index);
                        } else {
                            Toast.makeText(MainActivity.this, "Please fill out all information!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                insert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (setupTask()) {
                            insertTask(dbName, dbType, dbH, dbM, "true");
                            popUp.dismiss();
                            showTasks();
                        } else {
                            Toast.makeText(MainActivity.this, "Please fill out all information!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        showTasks();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor data = database.getTask(tasksID.get(position) + "");
                data.moveToFirst();
                String n = data.getString(0);
                String t = data.getString(1);
                dbH = data.getString(2);
                dbM = data.getString(3);
                final int ind = Integer.parseInt(tasksID.get(position).toString());

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = inflater.inflate(R.layout.popup_window, null);
                popUp = new PopupWindow(popupView, 800, 1100, true);
                popUp.showAtLocation(view, Gravity.CENTER, 0, 0);

                start = popupView.findViewById(R.id.start);
                saveStart = popupView.findViewById(R.id.saveStart);
                name = popupView.findViewById(R.id.name);
                duration = popupView.findViewById(R.id.duration);
                rb1 = popupView.findViewById(R.id.workout);
                rb2 = popupView.findViewById(R.id.rest);
                insert = popupView.findViewById(R.id.insert);

                saveStart.setText("Edit and Save");
                insert.setText("Delete");
                name.setText(n);
                String time = dbH + "h " + dbM + "m";
                duration.setText(time);

                if (t.equals("Rest")) {
                    rb1.toggle();
                    rb2.toggle();
                }

                declarePicker();

                saveStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (setupTask()) {
                            database.editTask(ind, dbName, dbType, dbH, dbM);
                            popUp.dismiss();
                            showTasks();
                        } else {
                            Toast.makeText(MainActivity.this, "Please fill out all information!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (setupTask()) {
                            startTask(ind);
                        } else {
                            Toast.makeText(MainActivity.this, "Please fill out all information!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                insert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.deleteTask(ind);
                        popUp.dismiss();
                        showTasks();
                    }
                });

            }
        });

    }

    public void startTask(int ind) {
        intent.putExtra("id", ind);
        intent.putExtra("name", dbName);
        intent.putExtra("type", dbType);
        intent.putExtra("h", Integer.parseInt(dbH));
        intent.putExtra("m", Integer.parseInt(dbM));
        startActivity(intent);
    }

    public void declarePicker() {
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
    }

    public boolean setupTask() {
        boolean pass = false;
        dbName = name.getText().toString();
        if (rb1.isChecked()) {
            dbType = "Workout";
        } else
            dbType = "Rest";
        if (dbH.length() > 0 && dbM.length() > 0 && dbName.length() > 0) {
            pass = true;
        }

        return pass;
    }

    public void insertTask(String name, String type, String h, String m, String saved) {
        database.insertTask(name, type, h, m, saved);
    }

    public void showTasks() {
        taskItems.clear();
        tasksID.clear();
        Cursor data = database.getTasks();
        while (data.moveToNext()) {
            if (data.getString(5).equals("true")) {
                TaskItem ti = new TaskItem();
                ti.setName(data.getString(1));

                if (data.getString(2).equals("Workout")) {
                    ti.setType("Workout");
                } else {
                    ti.setType("Rest");
                }

                tasksID.add(data.getString(0));
                ti.setDuration(data.getString(3) + " h " + data.getString(4) + " m");
                taskItems.add(ti);
            }

        }
        cadp.notifyDataSetChanged();
    }
    /*public void resetTasks() {
        Cursor data = database.getTasks();
        while (data.moveToNext()) {
            if (data.getString(5).equals("true"))
                tasks.add(data.getString(0) + " " + data.getString(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4));
        }
        tasksAdapter.notifyDataSetChanged();
    }*/




}
