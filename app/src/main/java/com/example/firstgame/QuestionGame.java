package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionGame extends AppCompatActivity implements View.OnClickListener {
    Button  button;
    TextView txt1;
    EditText edt;
    int cnt =5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
        edt = (EditText)findViewById(R.id.edt);
        txt1 = (TextView)findViewById(R.id.txt1);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button:
                cnt --;
                if(cnt == 0){
                    edt.setText("");
                    Toast.makeText(this,"Utizding bolom utizding",Toast.LENGTH_LONG).show();
                }
                String answer = edt.getText().toString();
                answer = answer.toLowerCase();
                if (TextUtils.isEmpty(answer)|!"kitob".equals(answer)) {
                    Toast.makeText(this,"Topmoding bolom topmoding", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"Borokallo o'g'lim,mullo bo'ling",Toast.LENGTH_LONG).show();

                }

                break;
        }

    }
}