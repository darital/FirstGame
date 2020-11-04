package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AboutPerson extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd, btnRead, btnClear;
    public DBHelper dbHelper;
    String LOG_TAG = "my_log";
    String[] types = new String[200];
    int[] sum = new int[200];
    int total_sum= 0;
    int cnt = 0;
    LinearLayout linLayout;
    LayoutInflater ltInflater;
    String type_expense;
    TextView tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_person);
        findId();

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
        linLayout = (LinearLayout) findViewById(R.id.linlayout);

        ltInflater = getLayoutInflater();

        Spinner staticSpinner = (Spinner) findViewById(R.id.expense_spinner);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);


        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("My_tag", (String) parent.getItemAtPosition(position));
                type_expense = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }




    private void findId(){
        tvTotal = (TextView) findViewById(R.id.tvTotal);
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



        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (view.getId()) {

            case R.id.btnRead:
                // TODO elementlar qayta qo'shilmasin
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                String selection = "type_expense = ?";
//                String sAge = etAge.getText().toString();
                String[] selectionArgs = new String[]{type_expense};
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("mytable", null, selection, selectionArgs, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int typeColIndex = c.getColumnIndex("type_expense");
                    int sumColIndex = c.getColumnIndex("sum_of_expense");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        String msg = "ID = " + c.getInt(idColIndex) +
                                ", typeColIndex = " + c.getString(typeColIndex) +
                                ", sumColIndex = " + c.getInt(sumColIndex);
                        Log.d(LOG_TAG, msg);
//                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        // переход на следующую строку
                        types[cnt] =  c.getString(typeColIndex);
                        sum[cnt] = c.getInt(sumColIndex);
                        total_sum += c.getInt(sumColIndex);

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
                    TextView tvSurname = (TextView) item.findViewById(R.id.tvType);
                    tvSurname.setText("Type: " + types[i]);
                    TextView tvPhone = (TextView) item.findViewById(R.id.tvSum);
                    tvPhone.setText("Sum: " + (sum[i]));
                    item.setBackgroundColor(colors[i % 2]);
                    linLayout.addView(item);
                }
                tvTotal.setText(String.format("Total: %d", total_sum));
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