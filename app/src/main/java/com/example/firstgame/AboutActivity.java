package com.example.firstgame;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnWeb;
    ImageButton btnMap;
    ImageButton btnCall;
    ImageView tg, yout, insta;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);


       btnWeb = (ImageButton) findViewById(R.id.btnWeb);
       btnMap = (ImageButton) findViewById(R.id.btnMap);
       btnCall = (ImageButton) findViewById(R.id.btnCall);
       tg = (ImageView) findViewById(R.id.tg);
       yout = (ImageView) findViewById(R.id.yout);
       insta = (ImageView) findViewById(R.id.insta);


       btnWeb.setOnClickListener(this);
       btnMap.setOnClickListener(this);
       btnCall.setOnClickListener(this);
       tg.setOnClickListener(this);
       yout.setOnClickListener(this);
       insta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnWeb:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://datalearningcentre.uz/"));
                startActivity(intent);
               break;
            case R.id.btnMap:
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setPackage("com.google.android.apps.maps");
               startActivity(intent);
                break;
           case R.id.btnCall:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+9989975988886"));
               startActivity(intent);
               break;
            case R.id.tg:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/data_learning_centre"));
                startActivity(intent);
            break;

            case R.id.insta:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/data_learning_centre/"));
                startActivity(intent);
            break;

            case R.id.yout:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/c/ShahzodSobirov/featured"));
                startActivity(intent);
            break;

        }
    }
}




