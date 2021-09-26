package com.example.fish.customer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.fish.R;

public class CustomerUpdate extends AppCompatActivity {
    EditText et_update_name, et_update_address, et_update_province, et_update_contact, et_update_food, et_update_qty;
    Button btn_update_cart;
    DatabaseReference dbRef;
    Customer cus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_update);

        et_update_name = findViewById(R.id.et_update_name);
        et_update_address = findViewById(R.id.et_update_address);
        et_update_province = findViewById(R.id.et_update_province);
        et_update_contact = findViewById(R.id.et_update_contact);
        et_update_food = findViewById(R.id.et_update_food);
        et_update_qty = findViewById(R.id.et_update_qty);

        btn_update_cart = findViewById(R.id.btn_update_cart);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Customer");

        cus = new Customer();
        btn_update_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (TextUtils.isEmpty(et_update_name.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter an Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(et_update_address.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter a Phone Address", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(et_update_province.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter a Province", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(et_update_contact.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter a Valid Mobile Number", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(et_update_food.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter a Quantity of Fish Food Packets", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(et_update_qty.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter a Quantity of Pet Fish", Toast.LENGTH_SHORT).show();
                    else {
                        cus.setName(et_update_name.getText().toString().trim());
                        cus.setAddress(et_update_address.getText().toString().trim());
                        cus.setProvince(et_update_province.getText().toString().trim());
                        cus.setPhone(Integer.valueOf(et_update_contact.getText().toString().trim()));
                        cus.setPacket(Integer.valueOf(et_update_food.getText().toString().trim()));
                        cus.setQuantity(Integer.valueOf(et_update_qty.getText().toString().trim()));

                        dbRef.push().setValue(cus);

                        Toast.makeText(getApplicationContext(), "Update Cart is Successfully Save", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Update Cart Data Is Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        public void buyNow(View view) {
            try {
            Toast.makeText(getApplicationContext(), "Order Buy is Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CustomerUpdate.this, Payment.class));
            }
         catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Order Buying Is Unsuccessful. Try Again..!", Toast.LENGTH_SHORT).show();
        }
    }
}

