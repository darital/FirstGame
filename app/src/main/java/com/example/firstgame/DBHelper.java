package com.example.firstgame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "expenseDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("my_log", "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table expenseSum ("
                + "id integer primary key autoincrement,"
                + "type_expense text,"
                + "sum_of_expense integer" + ");");
    }

    //  create table mytable (id integer primary key autoincrement, name text, surname text, phone text);
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}