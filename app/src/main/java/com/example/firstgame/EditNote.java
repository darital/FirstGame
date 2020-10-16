package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNote extends AppCompatActivity implements View.OnClickListener {
    Button btnEdit, btnCancel;
    EditText etNote;
    DBHelper dbHelper;
    public Date date;
    String mydate;
    String itemId;
    final String myLog = "Mylog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        mydate = formatForDateNow.format(date);
        etNote = (EditText) findViewById(R.id.etNote);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        String noteText = intent.getStringExtra("noteText");
        itemId = "" + intent.getIntExtra("noteId", -1);
        Log.d("OurID", ""+ itemId);

        etNote.setText(noteText);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnEdit:
                // malumotni saqlovchi obyekt
                ContentValues cv = new ContentValues();


                String note = etNote.getText().toString().trim();;

                //malumotlar bazasiga ulanamiz
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Log.d( myLog , "--- Update in noteTable ---");
                cv.put("note", note);
                cv.put("date", mydate);

                //malumotni yozamiz
                long updatedCount = db.update("noteTable", cv, "id = ?",
                        new String[] { "" + itemId });
                Log.d(myLog , "row updated, ID = " + updatedCount);
                finish();
                Intent intent2 = new Intent(EditNote.this, NoteActivity.class);
                startActivity(intent2);
                break;
        }
    }
}