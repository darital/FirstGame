package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {
TextView textWelcome;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        textWelcome = (TextView) findViewById(R.id.textWelcome);


        dbHelper = new DBHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.query("mytable", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int surnameColIndex = c.getColumnIndex("surname");
            int phoneColIndex = c.getColumnIndex("phone");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                String msg = "ID = " + c.getInt(idColIndex) +
                        ", name = " + c.getString(nameColIndex) +
                        ", surname = " + c.getString(surnameColIndex) +
                        ", phone = " + c.getString(phoneColIndex);

                String welcome = "Hello " + c.getString(nameColIndex) +
                        " " + c.getString(surnameColIndex) + '\n' +
                        "your phone number: " + c.getString(phoneColIndex);
                textWelcome.setText(welcome);
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else {
            Log.d("my_log", "0 rows");
            Intent intent = new Intent(this, AboutPerson.class);
            startActivity(intent);
        }
        c.close();
    }
}