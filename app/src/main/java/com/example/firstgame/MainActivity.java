package com.example.firstgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
TextView tvResult;
EditText etAnswer;
ImageView image;
Button btnOk;
int myAnswer;
int randomNumber;
Random random;
int cnt = 12;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("settings");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tvResult);
        etAnswer = (EditText) findViewById(R.id.etAnswer);
        image = (ImageView) findViewById(R.id.imageView);
        btnOk = (Button) findViewById(R.id.btnOk);

        btnOk.setOnClickListener(this);
        random = new Random();
        randomNumber = random.nextInt(100);

        Intent intent = getIntent();
        int difficulty = intent.getIntExtra("difficulty", 0);
        switch (difficulty){
            case 0:
                cnt = 12;
                break;
            case 1:
                cnt = 10;
                break;
            case 2:
                cnt = 8;
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnOk:
                if(TextUtils.isEmpty(etAnswer.getText())){
                    Toast.makeText(this, "Sonni kiritmadingiz", Toast.LENGTH_SHORT).show();
                    break;
                }
                cnt--;
                if(cnt < 0){
                    etAnswer.setText("");
                    tvResult.setText("Siz yutqazdingiz");

                    Intent intent = new Intent(MainActivity.this, NewGame.class);
                    startActivity(intent);
                    break;
                }
                myAnswer = Integer.parseInt(etAnswer.getText().toString());
                etAnswer.setText("");
                if(myAnswer > randomNumber)
                    tvResult.setText("Men o'ylagan son kichikroq");
                if(myAnswer < randomNumber)
                    tvResult.setText("Men o'ylagan son kattaroq");
                if(myAnswer == randomNumber){
                    image.setImageResource(R.drawable.success);
                    tvResult.setText("TABRIKLAYMAN");
                    btnOk.setText("Qayta o'ynash");
                }
                Toast.makeText(this, "Qolgan urinishlaringiz soni " + cnt, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}