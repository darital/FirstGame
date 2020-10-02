package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.victor.loading.newton.NewtonCradleLoading;

public class SplashActivity extends Activity {
    NewtonCradleLoading newtonCradleLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);

        newtonCradleLoading.setLoadingColor(Color.YELLOW);

        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long l) {
                newtonCradleLoading.start();
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this, Menu.class);
                startActivity(intent);
                finish();
            }
        }.start();


    }
}