package com.example.fish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserAccount extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    String email;
    Seller seller;

    DatabaseReference dbRef;

    TextView proName, proNic, proPhone, proEmail;
    Button deletePro, updatePro, advertise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(EMAIL_KEY, null);

        Toast.makeText(getApplicationContext(), "Welcome " +email, Toast.LENGTH_SHORT).show();

        proName = findViewById(R.id.ProName);
        proNic = findViewById(R.id.profileNic);
        proPhone = findViewById(R.id.profileNum);
        proEmail = findViewById(R.id.profileEmail);
        deletePro = findViewById(R.id.DeleteAccBtn);
        updatePro = findViewById(R.id.UpdateAccBtn);
        advertise = findViewById(R.id.MakeAdBtn);

        Query query = FirebaseDatabase.getInstance().getReference().child("Seller")
                .orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    public void updateUser(View view) {
        Intent intent = new Intent(UserAccount.this, UpdateSeller.class);
        String message = email;
        intent.putExtra(EMAIL_KEY, message);

        startActivity(intent);
    }

    public void deletUser(View view) {
        Query query = FirebaseDatabase.getInstance().getReference().child("Seller")
                .orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        dbRef = dataSnapshot.getRef();
                        dbRef.removeValue();
                        Toast.makeText(getApplicationContext(), "Account deleted successfully", Toast.LENGTH_SHORT).show();

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "User deleted successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } );
                        startActivity(new Intent(UserAccount.this, Login.class));
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Account delete unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "No sources to delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.hasChildren()) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    seller = dataSnapshot.getValue(Seller.class);
                    proName.setText(seller.getName());
                    try {
                        proEmail.setText(email);

                        proNic.setText(seller.getNic());
                        proPhone.setText(seller.getNumber());
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