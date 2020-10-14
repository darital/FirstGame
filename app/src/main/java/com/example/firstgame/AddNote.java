package com.example.firstgame;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNote extends AppCompatActivity implements View.OnClickListener {
    Button btnOk, btnCancel;
    EditText etNote;
    DBHelper dbHelper;
    public Date date;
    String mydate;
    final String myLog = "Mylog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        mydate = formatForDateNow.format(date);
        etNote = (EditText) findViewById(R.id.etNote);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnOk:
                // malumotni saqlovchi obyekt
                ContentValues cv = new ContentValues();

                String note = etNote.getText().toString();;

                //malumotlar bazasiga ulanamiz
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Log.d( myLog , "--- Insert in noteTable ---");
                cv.put("note", note);
                cv.put("date", mydate);

                //malumotni yozamiz
                long rowID = db.insert("noteTable", null, cv);
                Log.d(myLog , "row inserted, ID = " + rowID);
                finish();
                Intent intent = new Intent(AddNote.this, NoteActivity.class);
                startActivity(intent);
                break;
        }
    }
}