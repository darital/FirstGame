package com.example.firstgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
TextView tvResult;
EditText etAnswer;
ImageView image;
Button btnOk;
int myAnswer;
int randomNumber;
Random random;
int cnt = 5;
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
                    btnOk.setText("Qayta o'ynash");
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
                break;
        }
    }
}