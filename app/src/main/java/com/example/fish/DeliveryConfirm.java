package com.example.fish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DeliveryConfirm extends AppCompatActivity {
    TextView orderID, deladdress, delDate;
    Button deleteDel;
    DatabaseReference dbRef;
    String ordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_confirm);

        Intent intent = getIntent();
        ordID = intent.getStringExtra(Delivery.ORDER_ID);

        orderID = findViewById(R.id.ordIDConfirm);
        deladdress = findViewById(R.id.DeladConfirm);
        delDate = findViewById(R.id.dateConfirm);
        deleteDel = findViewById(R.id.OKbtn);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Delivery");
        Query query = FirebaseDatabase.getInstance().getReference().child("Delivery")
                .orderByChild("orderID").equalTo(ordID);
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    public void deleteDelivery(View view) {
        Query query = FirebaseDatabase.getInstance().getReference().child("Delivery")
                .orderByChild("orderID").equalTo(ordID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        dbRef = dataSnapshot.getRef();
                        dbRef.removeValue();

                        Toast.makeText(getApplicationContext(), "Delivery Order deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "No order to deliver", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "No source to deliver", Toast.LENGTH_SHORT).show();
            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.hasChildren()) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DeliveryOrder deliveryOrder = dataSnapshot.getValue(DeliveryOrder.class);

                    orderID.setText(deliveryOrder.getOrderID());
                    delDate.setText(deliveryOrder.getDelDate());
                    deladdress.setText(deliveryOrder.getDelAddress());
                }
            }
            else
                Toast.makeText(getApplicationContext(), "No delivery to display", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getApplicationContext(), "No Sources to display", Toast.LENGTH_SHORT).show();
        }
    };
}