package com.example.firstgame;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class AddLesson extends Activity implements OnClickListener{


    String[] data = {"08:30 09:50", "10:00 11:20", "11:30 12:50", "13:30 14:50", "15:00 16:20"};
    String[] data2 = {"Ma'ruza", "Amaliyot", "Tajriba"};
    String[] data3 = {"Har hafta", "Toq hafta", "Juft hafta"};
    int time_position = 0;
    int type_position = 0;
    int juft_toq_position = 0;
    String tabID;
    int cnt=0;
    DBHelper dbHelper;
    SQLiteDatabase db;
    int tabID_position;
    final String LOG_TAG = "myLogs";
    Button btnAdd, btnBack;
    EditText nameLesson, nameTeacher, lessonRoom;
    boolean nameTeacherUsed = false;
    boolean nameLessonUsed = false;
    boolean haveLesson = false;

   int[] tab1_para=new int[10];
    String[] tab1_lesson=new String[10];
    String[] tab1_lesson_type=new String[10];
    String[] tab1_teacher=new String[10];
    String[] tab1_time=new String[10];
    String[] tab1_room=new String[10];
    Integer[] tab1_week=new Integer[10];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lesson);
       Intent intent = getIntent();
        tabID = intent.getStringExtra("TabID");
        tabID_position = tabID.charAt(3)-48;

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        String selection = null;
        String[] selectionArgs = null;

        String orderBy = null;
        // курсор
        Cursor c = null;

        // yetarlimas qo'shish garak shu yera
        selection = "kun = ?";
        selectionArgs = new String[] { ""+tabID_position };
        orderBy = "para";
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        c = db.query("mytable", null, selection, selectionArgs, null, null,
                orderBy);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
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
                tab1_week[cnt] = c.getInt(weekColIndex);
                cnt++;

                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
         // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_time);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Vaqt");
        // выделяем элемент
        spinner.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                time_position = position;
                }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

// адаптер
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item, data2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_lesson_type);
        spinner2.setAdapter(adapter2);
        // заголовок
        spinner2.setPrompt("Dars Turi");
        // выделяем элемент
        spinner2.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                type_position = position;
              }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // адаптер
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item, data3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Spinner spinner3 = (Spinner) findViewById(R.id.spinner_week);
        spinner3.setAdapter(adapter3);
        // заголовок
        spinner3.setPrompt("Davomiyligi");
        // выделяем элемент
        spinner3.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                juft_toq_position = position;
             }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);


        nameLesson = (EditText) findViewById(R.id.editLesson);
        nameTeacher = (EditText) findViewById(R.id.editTeacher);

        lessonRoom = (EditText) findViewById(R.id.editRoom);

      //  etID = (EditText) findViewById(R.id.etID);
        //    textTime = (Spinner) findViewById(R.id.spinner_time);
        //    lessonType = (Spinner) findViewById(R.id.spinner_lesson_type);
        //    spinnerWeek = (Spinner) findViewById(R.id.spinner_week);

        // создаем объект для создания и управления версиями БД

    }

    @Override
    public void onBackPressed() {

            finish();
            Intent intent = new Intent(AddLesson.this, MainActivity.class);
            startActivity(intent);
    }

    @Override
    public void onClick(View v) {
         // создаем объект для данных
        ContentValues cv = new ContentValues();
        haveLesson = false;
        // получаем данные из полей ввода
        String lesson = nameLesson.getText().toString();
        String teacher = nameTeacher.getText().toString();
        String type = data2[type_position];
         String time = data[time_position];
        String room = lessonRoom.getText().toString();
        Integer week = juft_toq_position;
        Integer kunID = tabID_position;
        Integer paraID = time_position+1;
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
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение
//Bu yerga malumot kiritishda talablar qo'yaman
                //
                  if(room.length()>4){
                    Toast.makeText(getBaseContext(), "Xonani kiriting", Toast.LENGTH_SHORT).show();
                        break;
                }

                if( nameLesson.length()<5){
                    Toast.makeText(getBaseContext(), "Mashg'ulot nomini kiriting", Toast.LENGTH_SHORT).show();
                      break;
                }

               for(int i=0; i<cnt; i++){
                      if(paraID == tab1_para[i] && week == tab1_week [i] && week==0){
                        Toast.makeText(getBaseContext(), "Bu parada dars mavjud", Toast.LENGTH_SHORT).show();
                         haveLesson = true;
                    }
                }
                // juft toqlikni qoshish garak
                if(haveLesson == true){
                    break;
                }

                cv.put("kun", kunID);
                cv.put("para", paraID);
                cv.put("name", lesson);
                cv.put("teacher", teacher);
                cv.put("type", type);
                cv.put("time", time);
                cv.put("room", room);
                cv.put("week", week);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                 Toast.makeText(getBaseContext(), "dars jadvalga qoshildi", Toast.LENGTH_SHORT).show();
               finish();
                Intent intent = new Intent(AddLesson.this, MainActivity.class);
                startActivity(intent);
               break;
            case R.id.btnBack:
                finish();
                Intent intent2 = new Intent(AddLesson.this, MainActivity.class);
                startActivity(intent2);
                break;

        }
        // закрываем подключение к БД
        dbHelper.close();
    }

}