package com.example.fish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email, pwd;
    Button login, signInBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.LogEmail);
        pwd = findViewById(R.id.LogPwd);
        login = findViewById(R.id.LoginBtn);
        signInBtn = findViewById(R.id.Signup);

        mAuth = FirebaseAuth.getInstance();
    }

    public void loginFunc(View view) {
        String log_email = email.getText().toString().trim();
        String log_pwd = pwd.getText().toString().trim();

        if(log_email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter an Email address", Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(log_email).matches()) {
            Toast.makeText(getApplicationContext(), "Enter a valid Email address", Toast.LENGTH_SHORT).show();
        }
        else if(log_pwd.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter a Password", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(log_email, log_pwd).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, Delivery.class));
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login credentials invalid", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void signUpFunc(View view) {
        startActivity(new Intent(Login.this, RegisterSeller.class));
    }
}