package com.example.anejl.timeshine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String dbname = "TimeShine.db";
    public static final String tasks_table_name = "tasks";
    public static final String tasks_id = "id";
    public static final String tasks_name = "name";
    public static final String tasks_type = "type";
    public static final String tasks_hour = "hour";
    public static final String tasks_minute = "minute";
    public static final String tasks_saved = "saved";

    public static String stats_table_name = "stats";
    public static String stats_id = "id";
    public static String stats_comment = "comment";

    public DBHelper(Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tasks (id integer primary key autoincrement, name text, type text, hour text, minute text,saved text)");
        db.execSQL("create table stats (id integer primary key autoincrement, comment text, fk_id_task integer, foreign key (fk_id_task) references tasks(id))");
    }

    public void reset() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table tasks (id integer primary key autoincrement, name text, type text, hour text, minute text,saved text)");
        db.execSQL("create table stats (id integer primary key autoincrement, comment text, fk_id_task integer, foreign key (fk_id_task) references tasks(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tasks");
        db.execSQL("drop table if exists stats");
        onCreate(db);
    }

    public void insertTask(String name, String type, String hour,String minute,String saved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(tasks_name, name);
        cv.put(tasks_type, type);
        cv.put(tasks_hour, hour);
        cv.put(tasks_minute, minute);
        cv.put(tasks_saved, saved);
        db.insert(tasks_table_name, null, cv);
    }

    public Cursor getTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("select * from tasks", null);
        return data;
    }

    public int getMaxID(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor data= db.rawQuery("select max(id) from tasks",null);
        data.moveToFirst();
        int k=data.getInt(0);
        return data.getInt(0);
    }

    public void insertStat(int id,String comment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("comment","Trenutni Commen");
        cv.put("fk_id_task",id);
        db.insert(stats_table_name, null, cv);
    }

    public Cursor getStats() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("select * from stats", null);
        return data;
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tasks_table_name, "id=" + id, null);
    }

    public void editTask(int id, String name, String type, String hour,String minute) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(tasks_name, name);
        cv.put(tasks_type, type);
        cv.put(tasks_hour, hour);
        cv.put(tasks_minute, minute);
        db.update(tasks_table_name, cv, "id="+id,null);
    }
}
