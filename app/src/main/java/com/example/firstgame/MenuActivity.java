package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    FrameLayout layout1, layout2, layout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        layout1 = (FrameLayout) findViewById(R.id.layout1);
        layout2 = (FrameLayout) findViewById(R.id.layout2);
        layout3 = (FrameLayout) findViewById(R.id.layout3);

        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout1:
                Intent intent =new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.layout2:
                Intent intent1 =new Intent(MenuActivity.this,QuestionGame.class);
                startActivity(intent1);
                break;
        }
    }
}