package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewGame extends Activity implements View.OnClickListener {
Button btnOk,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        btnCancel= (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCancel:
               finish();
                break;
        }
    }
}