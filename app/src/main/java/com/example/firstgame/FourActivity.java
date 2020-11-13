package com.example.firstgame;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class FourActivity extends Activity{

    DBHelper dbHelper;
    String[] position = { "Программер", "Бухгалтер", "Программер",
            "Программер", "Бухгалтер", "Директор", "Программер", "Охранник" };
    String[] LessonName = new String [100];
    int salary[] = { 13000, 10000, 13000, 13000, 10000, 15000, 13000, 8000 };
    int cnt=0;
    int[] tab1_para=new int[100];
    int[] tab1_id=new int[100];
    String[] tab1_lesson=new String[100];
    String[] tab1_lesson_type=new String[100];
    String[] tab1_teacher=new String[100];
    String[] tab1_time=new String[100];
    String[] tab1_room=new String[100];
    Integer[] tab1_week=new Integer[100];
    final String LOG_TAG = "myLogs";
    SQLiteDatabase db;
    public int i=0;
    boolean switch_used=false;
    int[] colors = new int[2];
    Calendar c = Calendar.getInstance();
    int juft_toq_hafta = c.get(Calendar.WEEK_OF_YEAR)%2;
    int day = c.get(Calendar.DAY_OF_WEEK)-1;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four);
        Intent intent = getIntent();
        switch_used = intent.getBooleanExtra("switch", true);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        if(day==0){
            juft_toq_hafta = (juft_toq_hafta+1)%2;
        }
        if(juft_toq_hafta==0){
            juft_toq_hafta=2;
        }
        Log.d(LOG_TAG, "--- Insert in juft: ---"+juft_toq_hafta);

        //    Intent intent = getIntent();
        //    juft_toq_hafta = intent.getIntExtra("juft_toq", 0);
        //   cnt = intent.getIntExtra("soni", 0);


        String selection = null;
        String selection2 = null;
        String[] selectionArgs = null;
        String[] selectionArgs2 = null;

        String orderBy = null;
        // курсор
        Cursor c = null;

        // yetarlimas qo'shish garak shu yera
        selection = "kun = ?";
        selectionArgs = new String[] { "4" };
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
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", kun = " + c.getInt(kunColIndex) +
                                ", para = " + c.getInt(paraColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", teacher = " + c.getString(teacherColIndex) +
                                ", type = " + c.getString(typeColIndex) +
                                ", time = " + c.getString(timeColIndex) +
                                ", room = " + c.getString(roomColIndex) +
                                ", week = " + c.getInt(weekColIndex));

                tab1_id[cnt]=c.getInt(idColIndex);
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
        //  Intent intent = new Intent(AddLesson.this, OneActivity.class);
        //   intent.putExtra("tab1", tab1_lesson);
        //   intent.putExtra("soni", son);
        //   Intent intent2 = new Intent(AddLesson.this, MainActivity.class);
        //  finish();
        //    startActivity(intent);

        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");

        final LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);

        LayoutInflater ltInflater = getLayoutInflater();
        if(cnt!=0)
            for (i = 0; i < cnt; i++) {
                //     if (tab1_week[i]==juft_toq_hafta && switch_used==false){
                //        continue;
                //    }
                if (tab1_week[i]!=0 && tab1_week[i]!=juft_toq_hafta){
                    continue;
                }

                //   Log.d("myLogs", "i = " + i);
                View item = ltInflater.inflate(R.layout.item, linLayout, false);
                //      Log.d(LOG_TAG, "--- manga i ni qiymati kerak: ---"+i);

                TextView tvNameLesson = (TextView) item.findViewById(R.id.tvNameLesson);
                tvNameLesson.setText(tab1_lesson[i]);
                //    Log.d("myLogs", tab1_lesson[i]);

                TextView tvPosition = (TextView) item.findViewById(R.id.tvPara);
                tvPosition.setText("" + tab1_para[i]);
                //   Log.d("myLogs", "" + tab1_para[i]);

                TextView tvRoom = (TextView) item.findViewById(R.id.tvRoom);
                tvRoom.setText(tab1_room[i]);
                //    Log.d("myLogs", tab1_room[i]);

                TextView tvLesson = (TextView) item.findViewById(R.id.tvTypeLesson);
                tvLesson.setText(tab1_lesson_type[i]);
                //  Log.d("myLogs", tab1_lesson_type[i]);

                TextView tvTeacher = (TextView) item.findViewById(R.id.tvTeacher);
                tvTeacher.setText(tab1_teacher[i]);
                //   Log.d("myLogs", tab1_teacher[i]);

                TextView tvTime = (TextView) item.findViewById(R.id.tvTime);
                tvTime.setText(tab1_time[i]);
                //    Log.d("myLogs", tab1_time[i]);
                final int x= i;

                item.getLayoutParams().width = LayoutParams.MATCH_PARENT;
                //item.setBackgroundColor(colors[i % 2]);
                registerForContextMenu(item);
                final Toast toast = Toast.makeText(getApplicationContext(),
                        "Bir nima bosildi"+i, Toast.LENGTH_SHORT);
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //   Log.d(LOG_TAG, "--- aniq i ni tutib oladi: ---"+i);
                        toast.show();
                    }
                });
                item.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        menu.add(0, 1, 0, "uyga vazifa qo'shish");
                        menu.add(0, 2, 0, "o'zgartirish");
                        menu.add(0, 3, 0, "o'chirish");
                        MenuItem.OnMenuItemClickListener listener = new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                onContextItemSelected(item);
                                int menuindex = item.getItemId();
                                switch (menuindex) {
                                    case 3:
                                        //  Log.d(LOG_TAG, "--- Delete from mytable: ---");
                                        Log.d(LOG_TAG, "--- Delete from mytable: ---" + x);
                                        // удаляем по id
                                        int delCount = db.delete("mytable", "id = " + tab1_id[x], null);
                                        Log.d(LOG_TAG, "deleted rows count = " + delCount);
                                        //    finish();
                                        Intent intent2 = new Intent(FourActivity.this, MainActivity.class);
                                        startActivity(intent2);

                                        break;
                                    case 2:
                                        Intent intent3 = new Intent(FourActivity.this, EditLesson.class);
                                        Log.d(LOG_TAG, "--- EditLessonga kirmoqchiman ---");
                                        intent3.putExtra("TabActivityDay", "day4");
                                        intent3.putExtra("DarsniOrni", "" + x);
                                        intent3.putExtra("DarsID", "" + tab1_id[x]);
                                        startActivity(intent3);

                                        break;
                                    case 1:
                                        Intent intent4 = new Intent(FourActivity.this, AddHomework.class);
                                        startActivity(intent4);
                                        break;
                                }
                                return true;
                            }
                        };
                        for (int i = 0, n = menu.size(); i < n; i++) {
                            menu.getItem(i).setOnMenuItemClickListener(listener);
                        }
                    }


                });
                linLayout.addView(item);
            }
    }
    @Override
    public void onBackPressed() {

        finish();
    }

}