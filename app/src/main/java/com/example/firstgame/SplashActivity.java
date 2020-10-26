package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    ImageView imageView2;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView2= (ImageView) findViewById(R.id.imageView2);

       final Animation anim = AnimationUtils.loadAnimation(this, R.anim.mytrans);
        imageView2.startAnimation(anim);

        CountDownTimer timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {


            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this, ChartsActivity.class);
                startActivity(intent);
                finish();


            }
        }.start();

    }
}