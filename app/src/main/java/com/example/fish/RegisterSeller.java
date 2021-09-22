package com.example.fish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterSeller extends AppCompatActivity {
    EditText regName, regNic, regPhone, regEmail, regPwd, reRegPwd;
    Button regBtn;
    Seller seller;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regName = findViewById(R.id.RegName);
        regNic = findViewById(R.id.RegNIC);
        regPhone = findViewById(R.id.RegPhone);
        regEmail = findViewById(R.id.RegEmail);
        regPwd = findViewById(R.id.RegPwd);
        reRegPwd = findViewById(R.id.ReRegPwd);
        regBtn = findViewById(R.id.RegButn);

        seller = new Seller();
    }

    public void addSeller(View view) {
            dbRef = FirebaseDatabase.getInstance().getReference().child("Seller");

            try {
                if(TextUtils.isEmpty(regName.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter a Name", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(regNic.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter a NIC number", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(regPhone.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter a Contact number", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(regEmail.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter an Email address", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(regPwd.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter a Password", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(reRegPwd.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter a Password", Toast.LENGTH_SHORT).show();
                else {
                    if ((regPwd.getText().toString()).equals(reRegPwd.getText().toString())) {

                        seller.setName(regName.getText().toString().trim());
                        seller.setNic(regNic.getText().toString().trim());
                        seller.setNumber(regPhone.getText().toString().trim());
                        seller.setEmail(regEmail.getText().toString().trim());
                        seller.setPassword(regPwd.getText().toString().trim());

                        dbRef.push().setValue(seller);

                        Toast.makeText(getApplicationContext(), "Seller Registered successfully", Toast.LENGTH_SHORT).show();
                        clearData();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Invalid inputs", Toast.LENGTH_SHORT).show();
            }
    }

    public void clearData() {
        regName.setText("");
        regNic.setText("");
        regPhone.setText("");
        regEmail.setText("");
        regPwd.setText("");
        reRegPwd.setText("");
    }
}