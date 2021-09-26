package com.techdecode.fishproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Payment extends AppCompatActivity {

    Button btn_cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btn_cash = findViewById(R.id.btn_cash);
    }
    public void cash(View view) {

        try {
            Toast.makeText(getApplicationContext(), "Payment Cash Method is Successfully Save", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Payment.this, CustomerFeedback.class));

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Payment Cash Method Is Unsuccessful. Try Again..!", Toast.LENGTH_SHORT).show();
        }
    }
}
