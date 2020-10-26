package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AboutPerson extends AppCompatActivity implements View.OnClickListener {
    EditText etName, etSurname, etPhone, etAge;
    Button btnAdd, btnRead, btnClear;
    public DBHelper dbHelper;
    String LOG_TAG = "my_log";
    String[] names = new String[200];
    String[] surnames = new String[200];
    int[] ages = new int[200];
    String[] phones = new String[200];

    int cnt = 0;
    LinearLayout linLayout;
    LayoutInflater ltInflater;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_person);
        findId();

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
        linLayout = (LinearLayout) findViewById(R.id.linlayout);

        ltInflater = getLayoutInflater();

    }




    private void findId(){
        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etAge = (EditText) findViewById(R.id.etAge);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRead = (Button) findViewById(R.id.btnRead);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnAdd.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String phone = etPhone.getText().toString();
        int age = Integer.parseInt(etAge.getText().toString());

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (view.getId()) {
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение

                cv.put("name", name);
                cv.put("surname", surname);
                cv.put("age", age);
                cv.put("phone", phone);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);
                Log.d("my_log", "row inserted, ID = " + rowID);
                etName.setText("");
                etSurname.setText("");
                etPhone.setText("");
                etAge.setText("");
                break;
            case R.id.btnRead:
                // TODO elementlar qayta qo'shilmasin
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                String selection = "age > ?";
                String sAge = etAge.getText().toString();
                String[] selectionArgs = new String[]{sAge};
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("mytable", null, selection, selectionArgs, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int surnameColIndex = c.getColumnIndex("surname");
                    int ageColIndex = c.getColumnIndex("age");
                    int phoneColIndex = c.getColumnIndex("phone");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        String msg = "ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", surname = " + c.getString(surnameColIndex) +
                                ", age = " + c.getInt(ageColIndex) +
                                ", phone = " + c.getString(phoneColIndex);
                        Log.d(LOG_TAG, msg);
//                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        // переход на следующую строку
                        names[cnt] =  c.getString(nameColIndex);
                        surnames[cnt] = c.getString(surnameColIndex);
                        ages[cnt] = c.getInt(ageColIndex);
                        phones[cnt] = c.getString(phoneColIndex);
                        cnt++;
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                int[] colors = new int[2];
                 colors[0] = Color.parseColor("#559966CC");
                 colors[1] = Color.parseColor("#55336699");

                for (int i = 0; i < cnt; i++) {
                    Log.d("myLogs", "i = " + i);
                    View item = ltInflater.inflate(R.layout.item, linLayout, false);
                    TextView tvNumber = (TextView) item.findViewById(R.id.tvNumber);
                    tvNumber.setText(""+(i+1));
                    TextView tvName = (TextView) item.findViewById(R.id.tvName);
                    tvName.setText("Ism: " + names[i]);
                    TextView tvSurname = (TextView) item.findViewById(R.id.tvSurname);
                    tvSurname.setText("Familiya: " + surnames[i]);
                    TextView tvPhone = (TextView) item.findViewById(R.id.tvPhone);
                    tvPhone.setText("Telefon: " + (phones[i]));
                    TextView tvAge = (TextView) item.findViewById(R.id.tvAge);
                    tvAge.setText("Yoshi: " + (ages[i]));
                    item.setBackgroundColor(colors[i % 2]);
                    linLayout.addView(item);
                }

                break;

            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
            // закрываем подключение к БД

        }
        dbHelper.close();
    }

}