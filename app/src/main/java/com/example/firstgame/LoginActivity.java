package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etlemail;
    Button btnlogin, btnregister;
    TextView twinfo;
    AboutPerson.DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findId();
        dbHelper = new AboutPerson.DBHelper(this);
    }

    private void findId(){
        etlemail = (EditText) findViewById(R.id.etlemail);
        twinfo = (TextView) findViewById(R.id.twinfo);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnregister = (Button) findViewById(R.id.btnregister);
        btnlogin.setOnClickListener(this);
        btnregister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        int idColIndex = c.getColumnIndex("id");
        int nameColIndex = c.getColumnIndex("name");
        int surnameColIndex = c.getColumnIndex("surname");
        int phoneColIndex = c.getColumnIndex("phone");
        int emailColIndex = c.getColumnIndex("email");
        int ageColIndex = c.getColumnIndex("age");

        switch (view.getId()){
            case R.id.btnlogin:
                if (c.moveToFirst()) {
                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        String msg = "ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", surname = " + c.getString(surnameColIndex) +
                                ", phone = " + c.getString(phoneColIndex) +
                                ", email = " + c.getString(emailColIndex) +
                                ", age = " + c.getString(ageColIndex);

                        if (etlemail.getText().toString().equals(c.getString(emailColIndex))) {
                            twinfo.setText(msg);
                            break;
                        } else {
                            twinfo.setText("wrong");
                        }

                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                }
                c.close();

                break;
            case R.id.btnregister:
                Intent intent = new Intent(this, AboutPerson.class);
                startActivity(intent);
                break;
        }
    }
}