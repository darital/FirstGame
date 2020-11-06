package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;

public class ChartsActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnBarChart, btnPieChart, btnAddExpense, btnPie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        btnBarChart = findViewById(R.id.btnBarChart);
        btnPieChart = findViewById(R.id.btnPieChart);
        btnPie = findViewById(R.id.btnPie);
        btnBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(ChartsActivity.this, BarChartActivity.class);
                startActivity(I);
            }
        });
        btnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sdf = new Intent(ChartsActivity.this, AboutPerson.class);
                startActivity(sdf);
            }
        });
        btnAddExpense = (Button) findViewById(R.id.btnExpense);
        btnAddExpense.setOnClickListener(this);

        btnPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChartsActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExpense:
                Intent intent = new Intent(ChartsActivity.this, AddExpense.class);
                startActivity(intent);
                break;
        }

    }
}