package com.example.firstgame;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {
    public DBHelper dbHelper;
    String LOG_TAG = "MY_TAG";
    String[] expense_types = new String[200];
    int[] sum = new int[200];
    int cnt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
// reading element of database
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = new String[] { "type_expense", "sum(sum_of_expense) as sum_of_expense" };
        String groupBy = "type_expense";
        Cursor c = db.query("mytable", columns, null, null, groupBy, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int typeColIndex = c.getColumnIndex("type_expense");
            int sumColIndex = c.getColumnIndex("sum_of_expense");

            do {
                // получаем значения по номерам столбцов и пишем все в лог

//                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                // переход на следующую строку
                expense_types[cnt] =  c.getString(typeColIndex);
                sum[cnt] = c.getInt(sumColIndex);
                Log.d(LOG_TAG, "sdjkfhsjdkfhsdkjfsd: "+sum[cnt]);


                cnt++;
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();

        BarChart chart = findViewById(R.id.barchart);


        ArrayList NoOfEmp = new ArrayList();

        for(int i = 0; i<cnt; i++){
            NoOfEmp.add(new BarEntry(sum[i], i));
        }
        Log.d(LOG_TAG, "elements: " + cnt);
//        NoOfEmp.add(new BarEntry(945f, 0));
//        NoOfEmp.add(new BarEntry(1040f, 1));
//        NoOfEmp.add(new BarEntry(1133f, 2));
//        NoOfEmp.add(new BarEntry(1240f, 3));
//        NoOfEmp.add(new BarEntry(1369f, 4));


        ArrayList types = new ArrayList();
        for(int i = 0; i<cnt; i++){
        types.add(expense_types[i]);
        }
        Log.d(LOG_TAG, "onCreate: ");
//        types.add("Kiyim-kechak");
//        types.add("Oziq-ovqat");
//        types.add("Restoran");
//        types.add("Yol harajatlari");
//        types.add("Boshqa");

        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
        chart.animateY(5000);
        BarData data = new BarData(types, bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
    }
}