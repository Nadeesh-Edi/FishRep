package com.example.fish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Delivery extends AppCompatActivity {
    EditText orderID, phoneNo, DelAddress, DelDate;
    DeliveryOrder del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        orderID = findViewById(R.id.DelORdID);
        phoneNo = findViewById(R.id.DelPhone);
        DelAddress = findViewById(R.id.DelAddress);
        DelDate = findViewById(R.id.DelDate);

        del = new DeliveryOrder();
    }
}