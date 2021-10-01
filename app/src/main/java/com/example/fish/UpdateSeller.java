package com.example.fish;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UpdateSeller extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String email;
    Seller seller;

    DatabaseReference dbRef;

    EditText name, nic, phone;
    TextView upEmail;
    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_seller);

        Intent intent = getIntent();
        email = intent.getStringExtra(UserAccount.EMAIL_KEY);

        name = findViewById(R.id.RegName);
        nic = findViewById(R.id.RegNIC);
        phone = findViewById(R.id.RegPhone);
        upEmail = findViewById(R.id.updateEmail);
        updateBtn = findViewById(R.id.updButn);

        Query query = FirebaseDatabase.getInstance().getReference().child("Seller")
                .orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(valueEventListener);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Seller")
                        .orderByChild("email").equalTo(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                dbRef = dataSnapshot.getRef();
                                seller = dataSnapshot.getValue(Seller.class);

                                if(TextUtils.isEmpty(name.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                                else if(TextUtils.isEmpty(nic.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "NIC cannot be empty", Toast.LENGTH_SHORT).show();
                                else if(TextUtils.isEmpty(phone.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Phone number cannot be empty", Toast.LENGTH_SHORT).show();
                                else {
                                    seller.setName(name.getText().toString());
                                    seller.setNic(nic.getText().toString());
                                    seller.setNumber(phone.getText().toString());

                                    dbRef.setValue(seller);
                                    Toast.makeText(getApplicationContext(), "Account updated successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(UpdateSeller.this, MainActivity.class));
                                }

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Account update unsuccessful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Account cannot be updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.hasChildren()) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    seller = dataSnapshot.getValue(Seller.class);
                    name.setText(seller.getName());
                    try {
                        upEmail.setText(email);
                        nic.setText(seller.getNic());
                        phone.setText(seller.getNumber());

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "AAAAAAAAAAAAAAAAAA", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else
                Toast.makeText(getApplicationContext(), "No sources to display", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getApplicationContext(), "No sources", Toast.LENGTH_SHORT).show();
        }
    };
}