package com.example.fish.customer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fish.AdListing.Advertisement;
import com.example.fish.AdListing.CreateNewAd;
import com.example.fish.AdListing.MyAds;
import com.example.fish.Login;
import com.example.fish.MainActivity;
import com.example.fish.UserAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.fish.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CustomerBuy extends AppCompatActivity {
    EditText et_add_name, et_add_address, et_add_province, et_add_contact, et_add_food, et_add_qty;
    Button btn_add_cart;
    DatabaseReference dbRef;
    Customer cus;
    String userID, childRef = "Advertisement", adID;
    private final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ImageView imageView;


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
        imageView = findViewById(R.id.imageView5);


        // Get the data passed from the ViewSelectedAd
        Advertisement adDet = (Advertisement) getIntent().getSerializableExtra("AD");
        adID = adDet.getKey();
        userID = adDet.getUID();
        StorageReference getImages = storageReference.child(childRef).child(userID).child(adID);

        getImages.child("MainImage").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (!uri.equals(Uri.EMPTY)) {
                    Glide.with(CustomerBuy.this).load(uri.toString()).into(imageView);
                }
            }

        });

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

    public void next(View view) {

        try {
            Toast.makeText(getApplicationContext(), "Add To Cart is Successfully Save", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CustomerBuy.this, CustomerUpdate.class));

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Add To Cart Data Is Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }


}
