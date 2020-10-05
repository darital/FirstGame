package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPass extends Activity implements View.OnClickListener {
    EditText et;
    TextView tw;
    Button btnokk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);

        et = (EditText) findViewById(R.id.et);
        tw = (TextView) findViewById(R.id.tw);
        btnokk = (Button) findViewById(R.id.btnokk);
        btnokk.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnokk:

                String pincode = et.getText().toString();

                if(pincode.equals("123")){
                    tw.setText("your password: data");
                    et.setText("");
                } else {
                    Toast.makeText(this, "wrong pincode", Toast.LENGTH_SHORT).show();
                    et.setText("");

                }
        }

    }
}