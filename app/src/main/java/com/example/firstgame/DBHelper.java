package com.example.firstgame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admiral on 07.12.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATA_VERSION=1;
    public static final String DATABASE_NAME = "myDB";

    public static final String KEY_ID = "_id";
    public static final String KEY_KUN = "kun";
    public static final String KEY_LESSON = "lesson";
    public static final String KEY_LESSONTYPE = "type";
    public static final String KEY_TEACHER = "teacher";
    public static final String KEY_TIME = "time";
    public static final String KEY_ROOM = "room";
    public static final String KEY_WEEK = "week";


    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "kun integer,"
                + "para integer,"
                + "name text,"
                + "teacher text,"
                + "type text,"
                + "time text,"
                + "week integer,"
                + "room text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists mytable");
        onCreate(db);
    }


}
