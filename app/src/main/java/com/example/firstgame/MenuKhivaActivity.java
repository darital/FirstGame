package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class MenuKhivaActivity extends AppCompatActivity implements View.OnClickListener {
    FrameLayout layout1, layout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_khiva);

        layout1 = (FrameLayout) findViewById(R.id.layout1);
        layout2 = (FrameLayout) findViewById(R.id.layout2);
        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout1:
                Intent intent = new Intent(MenuKhivaActivity.this, GalleryActivity.class);
                intent.putExtra("bino", "kaltaminor");
                startActivity(intent);
                break;
            case R.id.layout2:
                Intent intent2 = new Intent(MenuKhivaActivity.this, GalleryActivity.class);
                intent2.putExtra("bino", "nurullaboysaroyi");
                startActivity(intent2);
                break;
        }
    }
}