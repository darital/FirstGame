package com.example.firstgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GalleryActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView img_khiwa1, img_khiwa2, img_khiwa3, img_khiwa4, imageMain;

    TextView textView;
    String bino;
    @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_gallery);
         findId();
         Intent intent = getIntent();
         bino = intent.getStringExtra("bino");
         switch (bino){
             case "kaltaminor":
                 textView.setText(R.string.kalta_minor);
                 img_khiwa1.setImageResource(R.drawable.ichonqala);
                 img_khiwa2.setImageResource(R.drawable.kalta_minor1);
                 img_khiwa3.setImageResource(R.drawable.minor_k);
                 img_khiwa4.setImageResource(R.drawable.mamirxonmadrasa);
                 break;

             case "nurullaboysaroyi":
                 textView.setText(R.string.nurullaboy_s);
                 img_khiwa1.setImageResource(R.drawable.nurulla_1);
                 img_khiwa2.setImageResource(R.drawable.nurulla_2);
                 img_khiwa3.setImageResource(R.drawable.nurulla_3);
                 img_khiwa4.setImageResource(R.drawable.nurulla_4);
                 break;
         }
     }

     @Override
     public void onClick(View view) {
         switch (bino){
             case "kaltaminor":
                switch (view.getId()){
                     case R.id.img_khiwa1:
                         imageMain.setImageResource(R.drawable.ichonqala);
                         break;
                     case R.id.img_khiwa2:
                         imageMain.setImageResource(R.drawable.kalta_minor1);
                         break;
                     case  R.id.img_khiwa3:
                         imageMain.setImageResource(R.drawable.minor_k);
                         break;
                     case  R.id.img_khiwa4:
                         imageMain.setImageResource(R.drawable.mamirxonmadrasa);
                         break;

                 }
                 break;

             case "nurullaboysaroyi":
               switch (view.getId()){
                     case R.id.img_khiwa1:
                         imageMain.setImageResource(R.drawable.nurulla_1);
                         break;
                     case R.id.img_khiwa2:
                         imageMain.setImageResource(R.drawable.nurulla_2);
                         break;
                     case  R.id.img_khiwa3:
                         imageMain.setImageResource(R.drawable.nurulla_3);
                         break;
                     case  R.id.img_khiwa4:
                         imageMain.setImageResource(R.drawable.nurulla_4);
                         break;

                 }
                 break;
         }
     }

     private void findId(){
         img_khiwa1 = (ImageView) findViewById(R.id.img_khiwa1);
         img_khiwa1.setOnClickListener(this);
         textView = (TextView) findViewById(R.id.textView3);
         img_khiwa2 = (ImageView) findViewById(R.id.img_khiwa2);
         img_khiwa2.setOnClickListener(this);
         img_khiwa3 = (ImageView) findViewById(R.id.img_khiwa3);
         img_khiwa3.setOnClickListener(this);
         img_khiwa4 = (ImageView) findViewById(R.id.img_khiwa4);
         img_khiwa4.setOnClickListener(this);
         imageMain = (ImageView) findViewById(R.id.imageMain);
         img_khiwa4.setOnClickListener(this);
     }
 }
