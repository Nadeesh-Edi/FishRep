package com.example.fish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterSeller extends AppCompatActivity {
    EditText regName, regNic, regPhone, regEmail, regPwd, reRegPwd;
    Button regBtn;
    Seller seller;
    DatabaseReference dbRef;
    FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
    }

    public void addSeller(View view) {

            String email = regEmail.getText().toString().trim();
            String pwd = regPwd.getText().toString().trim();

            try {
                if(TextUtils.isEmpty(regName.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter a Name", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(regNic.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter a NIC number", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(regPhone.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter a Contact number", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(regEmail.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter an Email address", Toast.LENGTH_SHORT).show();
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    Toast.makeText(getApplicationContext(), "Please enter a valid Email address", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(regPwd.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter a Password", Toast.LENGTH_SHORT).show();
                else if(pwd.length()<5)
                    Toast.makeText(getApplicationContext(), "Password must contain more than 5 characters", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(reRegPwd.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter a Password", Toast.LENGTH_SHORT).show();
                else {
                    if ((regPwd.getText().toString()).equals(reRegPwd.getText().toString())) {

                        String name = regName.getText().toString().trim();
                        String nic = regNic.getText().toString().trim();
                        String numb = regPhone.getText().toString().trim();

                        regAuthUser(email, pwd);
                        registerUser(name, nic, numb, email, pwd);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Invalid inputs", Toast.LENGTH_SHORT).show();
            }
    }

    public void registerUser(String name, String nic, String phone, String email, String pwd) {
        dbRef = FirebaseDatabase.getInstance().getReference().child("Seller");

        seller.setName(name);
        seller.setNic(nic);
        seller.setNumber(phone);
        seller.setEmail(email);
        seller.setPassword(pwd);

        dbRef.push().setValue(seller);

        Toast.makeText(getApplicationContext(), "Seller Registered successfully", Toast.LENGTH_SHORT).show();
    }

    public void regAuthUser(String email, String pwd) {
        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterSeller.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Seller Registered successfully", Toast.LENGTH_SHORT).show();
                    clearData();
                    startActivity(new Intent(RegisterSeller.this, Login.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
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