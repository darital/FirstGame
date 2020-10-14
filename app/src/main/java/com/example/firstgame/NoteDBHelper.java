package com.example.firstgame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NoteDBHelper extends SQLiteOpenHelper {
    public NoteDBHelper(Context context){
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("MyTag" , "--- onCreate database ---");
        // tablitsa yaratamiz
        sqLiteDatabase.execSQL("create table noteTable ("
        + "id integer primary key autoincrement,"
        + "date text,"
        + "note text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
