package com.example.firstgame;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditLesson extends Activity implements OnClickListener {
    String[] data = {"08:30 09:50", "10:00 11:20", "11:30 12:50", "13:30 14:50", "15:00 16:20"};
    String[] data2 = {"Ma'ruza", "Amaliyot", "Tajriba"};
    String[] data3 = {"Har hafta", "Toq hafta", "Juft hafta"};
    int time_position = 0;
    DBHelper dbHelper;
    SQLiteDatabase db;
    int type_position = 0;
    int juft_toq_position = 0;
    String newid;
    String darsID;
    int orni;
    int cnt = 0;
    String tabID;
    int tabID_position = 0;
    final String LOG_TAG = "myLogs";

    int[] tab1_para = new int[10];
    String[] tab1_lesson = new String[10];
    String[] tab1_lesson_type = new String[10];
    int id_lesson_type;
    int id_week;

    String[] tab1_teacher = new String[10];
    String[] tab1_time = new String[10];
    String[] tab1_room = new String[10];
    String[] tab1_week = new String[10];


    Button btnUpd;
    EditText nameLesson, nameTeacher, etID, lessonRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_lesson);
        Log.d(LOG_TAG, "--- EditLessonga kirdim ---");

        Intent intent = getIntent();
        tabID = intent.getStringExtra("TabActivityDay");
        tabID_position = tabID.charAt(3) - 48;
        newid = intent.getStringExtra("DarsniOrni");
        darsID = intent.getStringExtra("DarsID");
        Log.d(LOG_TAG, "--- Ornini qiymati: " + newid);
        orni = newid.charAt(0) - 48;
        Log.d(LOG_TAG, "--- Ornini qiymati: " + orni);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        String selection = null;
        String[] selectionArgs = null;

        String orderBy = null;
        // курсор
        Cursor c = null;

        // yetarlimas qo'shish garak shu yera
        selection = "kun = ?";
        selectionArgs = new String[]{"" + tabID_position};
        orderBy = "para";
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        c = db.query("mytable", null, selection, selectionArgs, null, null,
                orderBy);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        Log.d(LOG_TAG, "--- EditLessonga kirdim ---1");

        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int kunColIndex = c.getColumnIndex("kun");
            int paraColIndex = c.getColumnIndex("para");
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int teacherColIndex = c.getColumnIndex("teacher");
            int typeColIndex = c.getColumnIndex("type");
            int timeColIndex = c.getColumnIndex("time");
            int roomColIndex = c.getColumnIndex("room");

            int weekColIndex = c.getColumnIndex("week");
            do {
                // получаем значения по номерам столбцов и пишем все в лог

                tab1_para[cnt] = c.getInt(paraColIndex);
                tab1_lesson[cnt] = c.getString(nameColIndex);
                tab1_teacher[cnt] = c.getString(teacherColIndex);
                tab1_lesson_type[cnt] = c.getString(typeColIndex);
                tab1_time[cnt] = c.getString(timeColIndex);
                tab1_room[cnt] = c.getString(roomColIndex);
                tab1_week[cnt] = c.getString(weekColIndex);
                cnt++;

                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();

        Log.d(LOG_TAG, "--- EditLessonga kirdim ---2");

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_time);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Vaqt");
        // выделяем элемент

        spinner.setSelection(tab1_para[orni] - 1);
        Log.d(LOG_TAG, "---" + tab1_para[orni]);
        //    spinner.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                time_position = position;
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


// адаптер
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,  R.layout.spinner_item, data2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_lesson_type);
        spinner2.setAdapter(adapter2);
        // заголовок
        spinner2.setPrompt("Dars Turi");
        // выделяем элемент
        Log.d(LOG_TAG, "---" + tab1_lesson_type[orni]);

        if (tab1_lesson_type[orni].charAt(0) == 'M')
            spinner2.setSelection(0);
        if (tab1_lesson_type[orni].charAt(0) == 'A')
            spinner2.setSelection(1);
        if (tab1_lesson_type[orni].charAt(0) == 'T')
            spinner2.setSelection(2);
        // spinner2.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                type_position = position;
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // адаптер
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,  R.layout.spinner_item, data3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Spinner spinner3 = (Spinner) findViewById(R.id.spinner_week);
        spinner3.setAdapter(adapter3);
        // заголовок
        spinner3.setPrompt("Davomiyligi");
        // выделяем элемент
        //      spinner3.setSelection(tab1_week[orni].charAt(0) - 48);

            spinner3.setSelection(tab1_week[orni].charAt(0)-48);
        // устанавливаем обработчик нажатия
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                juft_toq_position = position;
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        btnUpd = (Button) findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(this);


        nameLesson = (EditText) findViewById(R.id.editLesson);
        nameLesson.setText(tab1_lesson[orni]);



        nameTeacher = (EditText) findViewById(R.id.editTeacher);
        nameTeacher.setText(tab1_teacher[orni]);

        lessonRoom = (EditText) findViewById(R.id.editRoom);
        lessonRoom.setText(tab1_room[orni]);


        //  etID = (EditText) findViewById(R.id.etID);
        //    textTime = (Spinner) findViewById(R.id.spinner_time);
        //    lessonType = (Spinner) findViewById(R.id.spinner_lesson_type);
        //    spinnerWeek = (Spinner) findViewById(R.id.spinner_week);

        // создаем объект для создания и управления версиями БД
        Log.d(LOG_TAG, "--- EditLessonga kirdim 1---1");
    }
    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(EditLesson.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();
        Log.d(LOG_TAG, "--- EditLessonga kirdim 1---2");
        Log.d(LOG_TAG, "--- EditLessonga kirdim ---3");


        // получаем данные из полей ввода
        String lesson = nameLesson.getText().toString();
        String teacher = nameTeacher.getText().toString();
        String type = data2[type_position];
        String time = data[time_position];
        String room = lessonRoom.getText().toString();
        Integer week = juft_toq_position;
        Integer kunID = tabID_position;
        Integer paraID = time_position + 1;
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // переменные для query
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        // курсор
        Cursor c = null;

        switch (v.getId()) {

            case R.id.btnUpd:
                // if (paraID.equalsIgnoreCase("")) {
                //      break;
                //   }
                Log.d(LOG_TAG, "--- Update mytable: ---");
                // подготовим значения для обновления
                cv.put("kun", kunID);
                cv.put("para", paraID);
                cv.put("name", lesson);
                cv.put("teacher", teacher);
                cv.put("type", type);
                cv.put("time", time);
                cv.put("room", room);
                cv.put("week", week);
                // обновляем по id

                int updCount = db.update("mytable", cv, "id = ?",
                        new String[]{darsID});
                Log.d(LOG_TAG, "updated rows count = " + updCount);
                finish();
                Intent intent = new Intent(EditLesson.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        // закрываем подключение к БД
        dbHelper.close();
    }


    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "kun integer,"
                    + "para integer,"
                    + "name text,"
                    + "teacher text,"
                    + "type text,"
                    + "time text,"
                    + "week integer,"
                    + "room text" + ");");
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}