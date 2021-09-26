package com.example.fish.customer;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import com.example.fish.R;

public class RetriveDataActivity extends AppCompatActivity {
    ListView myListview;
    List<Customer> customerList;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_data);

        myListview = findViewById(R.id.myListView);
        customerList = new ArrayList<>();

        dbRef = FirebaseDatabase.getInstance().getReference("Customer");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customerList.clear();
                for (DataSnapshot customerDatasnap : dataSnapshot.getChildren()) {
                    Customer customer = customerDatasnap.getValue(Customer.class);
                    customerList.add(customer);
                }
                ListAdapter adapter = new ListAdapter(RetriveDataActivity.this,customerList);
                myListview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

    }
}