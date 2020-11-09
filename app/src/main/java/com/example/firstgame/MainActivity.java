package com.example.firstgame;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout linLayout;
    LayoutInflater ltInflater;
    public DBHelper dbHelper;
    String LOG_TAG = "MY_TAG";
    int cnt = 0;
    int total_sum = 0;
    TextView tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddType.class);
                startActivity(intent);
            }
        });
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        linLayout = (LinearLayout) findViewById(R.id.linlayout);

        ltInflater = getLayoutInflater();
        getExpenseElements();
        tvTotal.setText("Buyun nichcha so'm ishlatganing: " + total_sum);
    }

    public void getExpenseElements(){
        linLayout.removeAllViews();
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = new String[] { "type_expense", "sum(sum_of_expense) as sum_of_expense" };
        String groupBy = "type_expense";
        final Cursor c = db.query("expenseSum", columns, null, null, groupBy, null, null);

        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
//            int idColIndex = c.getColumnIndex("id");
            final int typeColIndex = c.getColumnIndex("type_expense");
            int sumColIndex = c.getColumnIndex("sum_of_expense");
            Log.d(LOG_TAG, "typeColIndex: " + typeColIndex);
            Log.d(LOG_TAG, "sumColIndex: " + sumColIndex);
            do {
                cnt++;
                // получаем значения по номерам столбцов и пишем все в лог
//                String msg = "ID = " + c.getInt(idColIndex) +
//                        ", typeColIndex = " + c.getString(typeColIndex) +
//                        ", sumColIndex = " + c.getInt(sumColIndex);
//                Log.d(LOG_TAG, msg);
                final String expense_type = c.getString(typeColIndex);
                int sum_of_expenses = c.getInt(sumColIndex);
                total_sum += sum_of_expenses;
                    int[] colors = new int[2];
                    colors[0] = Color.parseColor("#559966CC");
                    colors[1] = Color.parseColor("#55336699");
                    View item = ltInflater.inflate(R.layout.item, linLayout, false);
                    TextView tvNumber = (TextView) item.findViewById(R.id.tvNumber);
                    tvNumber.setText("" + cnt);
                    TextView tvSurname = (TextView) item.findViewById(R.id.tvType);
                    tvSurname.setText("Novig'o pul yo'q yo'qatganing: " + expense_type);
                    TextView tvPhone = (TextView) item.findViewById(R.id.tvSum);
                    tvPhone.setText("Puli: " + sum_of_expenses);
                    item.setBackgroundColor(colors[cnt % 2]);
                    linLayout.addView(item);

                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent2 = new Intent(MainActivity.this, AddExpense.class);
                            intent2.putExtra("expense_type", expense_type);
                            startActivity(intent2);
                        }
                    });

            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        total_sum = 0;
        cnt= 0;
        getExpenseElements();
        super.onResume();
        tvTotal.setText("Buyun nichcha so'm ishlatganing: " + total_sum);
    }

    @Override
    public void onClick(View view) {

    }
}