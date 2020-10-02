package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends Activity implements View.OnClickListener {
    private RadioGroup radioGroup;
    private RadioButton easy, medium, hard;
    private Button btnSave, btnCancel;
    int difficulty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.easy) {
                    Toast.makeText(getApplicationContext(), "choice: easy",
                            Toast.LENGTH_SHORT).show();
                    difficulty = 0;
                } else if(checkedId == R.id.medium) {
                    Toast.makeText(getApplicationContext(), "choice: medium",
                            Toast.LENGTH_SHORT).show();
                    difficulty = 1;
                } else {
                    Toast.makeText(getApplicationContext(), "choice: hard",
                            Toast.LENGTH_SHORT).show();
                    difficulty = 2;
                }
            }

        });

        easy = (RadioButton) findViewById(R.id.easy);
        medium = (RadioButton) findViewById(R.id.medium);
        hard = (RadioButton) findViewById(R.id.hard);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSave:
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
                finish();
            break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }
}