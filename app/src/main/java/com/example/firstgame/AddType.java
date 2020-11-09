package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddType extends AppCompatActivity implements View.OnClickListener {
    EditText etType;
    Button btnAdd, btnCancel;
    String type_expense;
    public DBHelper dbHelper;
    Boolean addNewType = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);

        etType = (EditText) findViewById(R.id.etType);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdd:
                if(TextUtils.isEmpty(etType.getText().toString())){
                    Toast.makeText(this, "Please enter type", Toast.LENGTH_SHORT).show();
                    break;
                }
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String type = etType.getText().toString();
                cv.put("type_expense", type);
                long rowID = db.insert("expenseTypes", null, cv);
                finish();
                break;

        }
    }
}