package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {
    public DBHelper dbHelper;
    String LOG_TAG = "LOG_PIE";
    String[] expense_types = new String[200];
    int[] sum = new int[200];
    int cnt=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        PieChart pieChart =  findViewById (R.id.piechart);
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = new String[] { "type_expense", "sum(sum_of_expense) as sum_of_expense" };
        String groupBy = "type_expense";
        Cursor c = db.query("mytable", columns, null, null, groupBy, null, null);
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
                Log.d(LOG_TAG, "ed: "+sum[cnt]);


                cnt++;
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();

        PieChart chart = findViewById(R.id.piechart);


        ArrayList NoOfEmp = new ArrayList();

        for(int i = 0; i<cnt; i++){
            NoOfEmp.add(new BarEntry(sum[i], i));
        }
        Log.d(LOG_TAG, "elements: " + cnt);



        ArrayList types = new ArrayList();
        for(int i = 0; i<cnt; i++){
            types.add(expense_types[i]);
        }




        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");


        PieData data = new PieData(types, dataSet);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(1000, 1000);
    }
}