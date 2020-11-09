package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddExpense extends AppCompatActivity implements View.OnClickListener {
EditText etSum;
Button btnAdd, btnCancel;
String expense_type;
TextView tvType;
public DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etSum = (EditText) findViewById(R.id.etSum);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        tvType = (TextView) findViewById(R.id.tvType);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        Intent intent = getIntent();
        expense_type = intent.getStringExtra("expense_type");

        tvType.setText("Novig'o pul yo'q yo'qatajaksan: " + expense_type);




        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdd:
                if(TextUtils.isEmpty(etSum.getText().toString())){
                    Toast.makeText(this, "Uka pul nichcha so'mliqini yoz", Toast.LENGTH_SHORT).show();
                    break;
                }

                int summ = Integer.parseInt(etSum.getText().toString());
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                cv.put("type_expense", expense_type);
                cv.put("sum_of_expense", summ);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("expenseSum", null, cv);
                finish();
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }
}