package com.example.fish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Delivery extends AppCompatActivity {
    EditText orderID, phoneNo, DelAddress, DelDate;
    Button delBtn, mapBtn;
    DeliveryOrder del;
    DatabaseReference dbRef;
    public static final String ORDER_ID = "com.example.fish.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        orderID = findViewById(R.id.DelORdID);
        phoneNo = findViewById(R.id.DelPhone);
        DelAddress = findViewById(R.id.DelAddress);
        DelDate = findViewById(R.id.DelDate);
        delBtn = findViewById(R.id.DelBtn);
        mapBtn = findViewById(R.id.MapBtn);

        del = new DeliveryOrder();
    }

    public void addDelivery(View view) {
        dbRef = FirebaseDatabase.getInstance().getReference().child("Delivery");

        try {
            if(TextUtils.isEmpty(orderID.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter an ID", Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(phoneNo.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter a Phone Number", Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(DelAddress.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter an Address", Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(DelDate.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please enter an expected delivery date", Toast.LENGTH_SHORT).show();
            else {
                del.setOrderID(orderID.getText().toString().trim());
                del.setPhoneNo(phoneNo.getText().toString().trim());
                del.setDelAddress(DelAddress.getText().toString().trim());
                del.setDelDate(DelDate.getText().toString().trim());

                dbRef.push().setValue(del);

                Toast.makeText(getApplicationContext(), "Delivery order recorded successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Delivery.this, DeliveryConfirm.class);
                String ordID = orderID.getText().toString().trim();
                intent.putExtra(ORDER_ID, ordID);
                startActivity(intent);
            }
        }
        catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Delivery Unsuccessful", Toast.LENGTH_SHORT).show();
            }
    }
}