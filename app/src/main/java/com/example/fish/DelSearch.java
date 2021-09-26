package com.example.fish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DelSearch extends AppCompatActivity {
    EditText ordID;
    Button search;
    public static final String ORDER_ID = "com.example.fish.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_search);

        ordID = findViewById(R.id.SearchOrdID);
        search = findViewById(R.id.SearchDel);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DelSearch.this, DeliveryConfirm.class);
                String orderID = ordID.getText().toString().trim();
                intent.putExtra(ORDER_ID, orderID);
                startActivity(intent);
            }
        });
    }
}