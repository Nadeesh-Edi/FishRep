package com.techdecode.fishproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerBuy extends AppCompatActivity {
    EditText et_add_name, et_add_address, et_add_province, et_add_contact, et_add_food, et_add_qty;
    Button btn_add_cart;
    DatabaseReference dbRef;
    Customer cus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_buy);

        et_add_name = findViewById(R.id.et_add_name);
        et_add_address = findViewById(R.id.et_add_address);
        et_add_province = findViewById(R.id.et_add_province);
        et_add_contact = findViewById(R.id.et_add_contact);
        et_add_food = findViewById(R.id.et_add_food);
        et_add_qty = findViewById(R.id.et_add_qty);

        btn_add_cart = findViewById(R.id.btn_add_cart);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Customer");

        cus = new Customer();
        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                if(TextUtils.isEmpty(et_add_name.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter an Name", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(et_add_address.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter a Phone Address", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(et_add_province.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter a Province", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(et_add_contact.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter a Valid Mobile Number", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(et_add_food.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter a Quantity of Fish Food Packets", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(et_add_qty.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter a Quantity of Pet Fish", Toast.LENGTH_SHORT).show();
                else {
                    cus.setName(et_add_name.getText().toString().trim());
                    cus.setAddress(et_add_address.getText().toString().trim());
                    cus.setProvince(et_add_province.getText().toString().trim());
                    cus.setPhone(Integer.valueOf(et_add_contact.getText().toString().trim()));
                    cus.setPacket(Integer.valueOf(et_add_food.getText().toString().trim()));
                    cus.setQuantity(Integer.valueOf(et_add_qty.getText().toString().trim()));

                    dbRef.push().setValue(cus);

                    Toast.makeText(getApplicationContext(), "Add To Cart is Successfully Save", Toast.LENGTH_SHORT).show();

                }
            }
            catch(Exception e) {
                Toast.makeText(getApplicationContext(), "Add To Cart Data Is Unsuccessful", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

//    public void showData(View view){
//        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Customer/cus1");
//        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChildren()){
//                    et_add_name.setText(dataSnapshot.child("et_cus_name").getValue().toString());
//                    et_add_address.setText(dataSnapshot.child("et_cus_address").getValue().toString());
//                    et_add_province.setText(dataSnapshot.child("et_cus_address").getValue().toString());
//                    et_add_contact.setText(dataSnapshot.child("et_cus_phone").getValue().toString());
//                    et_add_food.setText(dataSnapshot.child("et_cus_food").getValue().toString());
//                    et_add_qty.setText(dataSnapshot.child("et_cus_qty").getValue().toString());
//                }
//                else{
//                    Toast.makeText(CustomerBuy.this, "No Source To Display", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void next(View view) {

        try {
//            if(TextUtils.isEmpty(et_add_name.getText().toString()))
//                Toast.makeText(getApplicationContext(), "Please Enter an Name", Toast.LENGTH_SHORT).show();
//            else if(TextUtils.isEmpty(et_add_address.getText().toString()))
//                Toast.makeText(getApplicationContext(), "Please Enter a Phone Address", Toast.LENGTH_SHORT).show();
//            else if(TextUtils.isEmpty(et_add_province.getText().toString()))
//                Toast.makeText(getApplicationContext(), "Please Enter a Province", Toast.LENGTH_SHORT).show();
//            else if(TextUtils.isEmpty(et_add_contact.getText().toString()))
//                Toast.makeText(getApplicationContext(), "Please Enter a Valid Mobile Number", Toast.LENGTH_SHORT).show();
//            else if(TextUtils.isEmpty(et_add_food.getText().toString()))
//                Toast.makeText(getApplicationContext(), "Please Enter a Quantity of Fish Food Packets", Toast.LENGTH_SHORT).show();
//            else if(TextUtils.isEmpty(et_add_qty.getText().toString()))
//                Toast.makeText(getApplicationContext(), "Please Enter a Quantity of Pet Fish", Toast.LENGTH_SHORT).show();
//            else {
//                cus.setName(et_add_name.getText().toString().trim());
//                cus.setAddress(et_add_address.getText().toString().trim());
//                cus.setProvince(et_add_province.getText().toString().trim());
//                cus.setPhone(Integer.valueOf(et_add_contact.getText().toString().trim()));
//                cus.setPacket(Integer.valueOf(et_add_food.getText().toString().trim()));
//                cus.setQuantity(Integer.valueOf(et_add_qty.getText().toString().trim()));
//
//                dbRef.push().setValue(cus);
//
            Toast.makeText(getApplicationContext(), "Add To Cart is Successfully Save", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CustomerBuy.this, CustomerUpdate.class));

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Add To Cart Data Is Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }
}

//        DAOCustomer dao = new DAOCustomer();
//
//
//        btn_add_cart.setOnClickListener(v->{
//            Customer cus = new Customer(et_add_name.getText().toString(),et_add_address.getText().toString(),et_add_province.getText().toString(),Integer.valueOf(et_add_contact.getText().toString()),Integer.valueOf(et_add_food.getText().toString()),Integer.valueOf(et_add_qty.getText().toString()));
//            dao.add(cus).addOnSuccessListener(suc->
//            {
//                Toast.makeText(this, "Record is Inserted", Toast.LENGTH_SHORT).show();
//            }).addOnFailureListener(er->
//            {
//                Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
//            });
//        });
