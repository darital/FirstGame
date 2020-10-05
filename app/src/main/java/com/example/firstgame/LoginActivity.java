package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener {
    EditText etLogin, etPassword;
    Button btnLogin, btnpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnpass = (Button) findViewById(R.id.btnpass);
        btnpass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnpass:
                Intent intent2 = new Intent(LoginActivity.this, ForgotPass.class);
                startActivity(intent2);
                break;

            case R.id.btnLogin:
                // login: darital
                // parol: data
                String login = etLogin.getText().toString();
                String parol = etPassword.getText().toString();

                if(login.equals("darital") && parol.equals("data")){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Wrong login or password", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etLogin.setText("");
                }

        }
    }
}