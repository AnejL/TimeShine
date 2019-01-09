package com.example.anejl.timeshine;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper database;
    ListView listView;
    ArrayAdapter<String> tasksAdapter;
    ArrayList tasks;

    PopupWindow popUp;

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
        popUp=new PopupWindow(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater =(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window,null);
                popUp=new PopupWindow(popupView,800,1000,true);
                popUp.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });



        insertTask();
        database.editTask(2, "nogalo", "ezpz", "12312313");
        showTasks();
    }

    public void insertTask() {

        database.insertTask("AIPS", "rest", "222");
        database.insertTask("AsadPS", "rasdasdest", "222asdsa");
        database.insertTask("asd", "asdas", "22asdasd2");

    }

    public void showTasks() {
        Cursor data = database.getTasks();
        while (data.moveToNext()) {
            tasks.add(data.getString(0) + " " + data.getString(1) + " " + data.getString(2) + " " + data.getString(3));
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
