package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener {
Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btn2 = (Button)findViewById(R.id.btn2);
        btn1 = (Button)findViewById(R.id.btn1);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                Intent intent =new Intent(Menu.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                Intent intent1 =new Intent(Menu.this,QuestionGame.class);
                startActivity(intent1);
                break;
        }
    }
}