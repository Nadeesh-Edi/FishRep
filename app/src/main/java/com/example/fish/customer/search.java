package com.example.fish.customer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fish.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class search extends AppCompatActivity {
    EditText search;
    TextView name, addr, provin, phone, food, fishNo;
    Button edit, delete;
    ImageView searchBtn;

    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search=findViewById(R.id.editTextTextPersonName);
        name = findViewById(R.id.et_cus_name);
        addr = findViewById(R.id.et_cus_address);
        provin = findViewById(R.id.et_cus_province);
        phone = findViewById(R.id.et_cus_phone);
        food = findViewById(R.id.et_food);
        fishNo = findViewById(R.id.et_fish_number);

        edit = findViewById(R.id.btn_add_cart);
        delete = findViewById(R.id.btn_delete);

        searchBtn = findViewById(R.id.imageView8);
        String searchName = search.getText().toString().trim();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference("Customer");

                Query query = FirebaseDatabase.getInstance().getReference("Customer")
                        .orderByChild("name").equalTo(searchName);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                Customer customer = dataSnapshot.getValue(Customer.class);

                                addr.setText(customer.getAddress());
                                provin.setText(customer.getProvince());
                                phone.setText(customer.getPhone());
                                food.setText(customer.getPacket());
                                fishNo.setText(customer.getQuantity());
                            }
                            Toast.makeText(getApplicationContext(), "Search Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Search unsuccessfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Data unavailable", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}