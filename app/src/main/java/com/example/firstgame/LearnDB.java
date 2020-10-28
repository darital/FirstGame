package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.util.Optional;

public class LearnDB extends AppCompatActivity implements View.OnClickListener {
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
        setContentView(R.layout.activity_practice);
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

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (view.getId()) {
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение
                if(TextUtils.isEmpty(etAge.getText())){
                    Toast.makeText(this, "Yoshni yoz", Toast.LENGTH_SHORT).show();
                    break;
                }
                int age = Integer.parseInt(etAge.getText().toString());

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

                Log.d(LOG_TAG, String.format("id: %d,\nname: %s,\nsurname: %s,\nage: %d,\nphone: %s", cnt, name, surname, age, phone));
                createItem(cnt, name, surname, age, phone);
                cnt++;
                break;
            case R.id.btnRead:
                linLayout.removeAllViews();
                // TODO elementlar qayta qo'shilmasin
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                String selection = "age > ?";
                String sAge = etAge.getText().toString();
                String[] selectionArgs = new String[]{sAge};
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("mytable", null, null, null, null, null, null);

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

                for (int i = 0; i < cnt; i++) {
                    Log.d("myLogs", "i = " + i);

                    createItem(i, names[i], surnames[i], ages[i], phones[i]);
                }
                cnt = 0;
                break;

            case R.id.btnClear:
                Intent intent = new Intent(LearnDB.this, SortedElements.class);
                startActivity(intent);

               /* Log.d(LOG_TAG, "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;*/
            // закрываем подключение к БД

        }
        dbHelper.close();
    }

    @SuppressLint("DefaultLocale")
    public void createItem(int id, String name, String surname, int age, String phone){
        Log.d(LOG_TAG, String.format("id: %d,\nname: %s,\nsurname: %s,\nage: %d,\nphone: %s", id, name, surname, age, phone));

        int[] colors = new int[2];
        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");
           Log.d("myLogs", "i = ");
            View item = ltInflater.inflate(R.layout.item, linLayout, false);
            TextView tvNumber = (TextView) item.findViewById(R.id.tvNumber);
            tvNumber.setText(String.format("%d",id));
            TextView tvName = (TextView) item.findViewById(R.id.tvName);
            tvName.setText(String.format("Ism: %s", name));
            TextView tvSurname = (TextView) item.findViewById(R.id.tvSurname);
            tvSurname.setText(String.format("Familiya: %s", surname));
            TextView tvPhone = (TextView) item.findViewById(R.id.tvPhone);
            tvPhone.setText(String.format("Telefon: %s", phone));
            TextView tvAge = (TextView) item.findViewById(R.id.tvAge);
            tvAge.setText(String.format("Yoshi: %s", age));
//            item.setBackgroundColor(colors[i % 2]);
            linLayout.addView(item);

    }

}