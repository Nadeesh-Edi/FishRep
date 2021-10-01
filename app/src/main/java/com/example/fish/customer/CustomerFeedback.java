package com.example.fish.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fish.Delivery;
import com.example.fish.MainActivity;
import com.example.fish.R;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerFeedback extends AppCompatActivity {
    TextView tvFeedback;
    RatingBar rbStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_feedback);

        tvFeedback = findViewById(R.id.tvFeedback);
        rbStars = findViewById(R.id.rbStars);

        rbStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating==0){
                    tvFeedback.setText("Very Dissatisfied");
                }
                else if(rating==1){
                    tvFeedback.setText("Dissatisfied");
                }
                else if(rating==2){
                    tvFeedback.setText("Satisfied");
                }
                else if(rating==3){
                    tvFeedback.setText("Good");
                }
                else if(rating==4){
                    tvFeedback.setText("Very Good");
                }
                else{
                    tvFeedback.setText("Very Satisfied");
                }

            }
        });


    }
        public void SendFeedback(View view) {

            try {

                Toast.makeText(getApplicationContext(), "Thanks For Your Feedback", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CustomerFeedback.this, MainActivity.class));
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Please Rate Us Now", Toast.LENGTH_SHORT).show();
            }
        }
    }
