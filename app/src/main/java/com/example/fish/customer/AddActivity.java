package com.example.fish.customer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fish.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText name,address,province,phone,packet,quantity;
    Button btnAdd, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = (EditText) findViewById(R.id.txtName);
        address = (EditText) findViewById(R.id.txtAddress);
        province = (EditText) findViewById(R.id.txtProvince);
        phone = (EditText) findViewById(R.id.txtPhone);
        packet = (EditText) findViewById(R.id.txtPacket);
        quantity = (EditText) findViewById(R.id.txtQuantity);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(name.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Fill Your Name", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(address.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Fill Your Name", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(province.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Fill Your Name", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(phone.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Fill Your Name", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(quantity.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Fill Your Name", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(packet.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Fill Your Name", Toast.LENGTH_SHORT).show();
                else{
                    startActivity(new Intent(AddActivity.this, Payment.class));
                    Toast.makeText(getApplicationContext(),"Successfully.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    private void insertData(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", name.getText().toString());
        map.put("address", address.getText().toString());
        map.put("province", province.getText().toString());
        map.put("phone", phone.getText().toString());
        map.put("packet", packet.getText().toString());
        map.put("quantity", quantity.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Customer").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this,"Data Insert Successfully.", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this,"Error.. try Again..!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void clearAll(){
        name.setText("");
        address.setText("");
        province.setText("");
        phone.setText("");
        packet.setText("");
        quantity.setText("");
    }
}