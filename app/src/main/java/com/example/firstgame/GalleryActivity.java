package com.example.firstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GalleryActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView53;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView3;
    TextView textView;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_gallery);
         findId();
         Intent intent = getIntent();
         String bino = intent.getStringExtra("bino");
         switch (bino){
             case "kaltaminor":
                 textView.setText(R.string.kalta_minor);
                 break;
             case "nurullaboysaroyi":
                 textView.setText(R.string.nurullaboy_s);
                 break;
         }
     }

     @Override
     public void onClick(View view) {
         switch (view.getId()){
             case R.id.imageView53:
                 textView.setText(R.string.ichon_qala);
                 break;
             case R.id.imageView4:
                 textView.setText(R.string.kalta_minor );
                 break;
             case  R.id.imageView5:
                 textView.setText(R.string.koxna_ark);
                 break;
             case  R.id.imageView6:
                 textView.setText(R.string.mamirxon_m);
                 break;
             case  R.id.imageView7:
                 textView.setText(R.string.nurullaboy_s);
                 break;
             case  R.id.imageView8:
                 textView.setText(R.string.pahlavon_m);
                 break;
             case  R.id.imageView3:
                 textView.setText(R.string.tosh_xovli);
                 break;
         }
     }

     private void findId(){
         imageView53 = (ImageView) findViewById(R.id.imageView53);
         imageView53.setOnClickListener(this);
         textView = (TextView) findViewById(R.id.textView3);
         imageView4 = (ImageView) findViewById(R.id.imageView4);
         imageView4.setOnClickListener(this);
         imageView5 = (ImageView) findViewById(R.id.imageView5);
         imageView5.setOnClickListener(this);
         imageView6 = (ImageView) findViewById(R.id.imageView6);
         imageView6.setOnClickListener(this);
         imageView7 = (ImageView) findViewById(R.id.imageView7);
         imageView7.setOnClickListener(this);
         imageView8 = (ImageView) findViewById(R.id.imageView8);
         imageView8.setOnClickListener(this);
         imageView3 = (ImageView) findViewById(R.id.imageView3);
         imageView3.setOnClickListener(this);
     }
 }
