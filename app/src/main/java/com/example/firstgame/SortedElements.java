package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SortedElements extends AppCompatActivity implements View.OnClickListener {
    Button btnYoung, btnMiddle, btnOld;
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
        setContentView(R.layout.activity_sorted_elements);
        findViews();
        dbHelper = new DBHelper(this);
        linLayout = (LinearLayout) findViewById(R.id.linlayout_elements);

        ltInflater = getLayoutInflater();
    }

    private void findViews() {
        btnYoung = (Button) findViewById(R.id.btnYoung);
        btnYoung.setOnClickListener(this);
        btnMiddle = (Button) findViewById(R.id.btnMiddle);
        btnMiddle.setOnClickListener(this);
        btnOld = (Button) findViewById(R.id.btnOld);
        btnOld.setOnClickListener(this);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View view) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (view.getId()) {
            case R.id.btnYoung:
                cnt = 0;
                linLayout.removeAllViews();
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
//                String selection = "age <= ?";
//                String[] selectionArgs = new String[]{"17"};
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
                int[] colors = new int[2];
                colors[0] = Color.parseColor("#559966CC");
                colors[1] = Color.parseColor("#55336699");
                int newCnt = 0;
                for (int i = 0; i < cnt; i++) {
                    if(ages[i] <= 18){
                        Log.d(LOG_TAG, String.format("ages: %d", ages[i]));
                        newCnt++;
                        Log.d("myLogs", "i = " + i);
                        View item = ltInflater.inflate(R.layout.item, linLayout, false);
                        TextView tvNumber = (TextView) item.findViewById(R.id.tvNumber);
                        tvNumber.setText(String.format("%d", newCnt));
                        TextView tvName = (TextView) item.findViewById(R.id.tvName);
                        tvName.setText(String.format("Ism: %s", names[i]));
                        TextView tvSurname = (TextView) item.findViewById(R.id.tvSurname);
                        tvSurname.setText("Familiya: " + surnames[i]);
                        TextView tvPhone = (TextView) item.findViewById(R.id.tvPhone);
                        tvPhone.setText("Telefon: " + (phones[i]));
                        TextView tvAge = (TextView) item.findViewById(R.id.tvAge);
                        tvAge.setText("Yoshi: " + (ages[i]));
                        item.setBackgroundColor(colors[i % 2]);
                        linLayout.addView(item);
                    }

                }
                break;
        }
    }
}