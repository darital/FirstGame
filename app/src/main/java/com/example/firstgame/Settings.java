package com.example.firstgame;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Admiral on 10.12.2016.
 */
public class Settings extends Activity implements View.OnClickListener {
    DBHelper dbHelper;
    SQLiteDatabase db;

    Button btnClear, btnJuftToq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Button btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        Button btnJuftToq = (Button) findViewById(R.id.btnJuftToq);
        btnJuftToq.setOnClickListener(this);
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnClear:
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                db.delete("mytable", null, null);
                finish();
                break;
            case R.id.btnJuftToq:
               // if(btnJuftToq.)
               // btnJuftToq.setText("Juft");
                break;
        }
    }
}
