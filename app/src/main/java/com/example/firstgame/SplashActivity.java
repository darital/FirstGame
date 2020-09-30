package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.victor.loading.newton.NewtonCradleLoading;

public class SplashActivity extends Activity implements View.OnClickListener {
    NewtonCradleLoading newtonCradleLoading;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);

        newtonCradleLoading.start();
        newtonCradleLoading.setLoadingColor(Color.YELLOW);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}