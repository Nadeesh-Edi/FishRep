package com.example.fish.AdListing;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fish.Login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.fish.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNewAd extends AppCompatActivity {

    EditText et_new_ad_title, et_new_ad_price, et_new_ad_contact, et_new_ad_description;
    Spinner select_location_search;
    Button btn_submit_ad;
    ImageView addImage1, addImage2, addImage3;
    CheckBox chk_negotiable;
    Boolean negotiable_value = false;
    private static final String TAG = "CreateAd";

    ProgressBar progressBar;
    Uri imgURI1 = Uri.EMPTY, imgURI2 = Uri.EMPTY, imgURI3 = Uri.EMPTY;

    DatabaseReference dbRef;
    private final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseAuth firebaseAuth;
    Advertisement ad;
    String userID;
    String childRef = "Advertisement";
    String adID;
    Boolean isNew = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_ad);
        firebaseAuth = FirebaseAuth.getInstance();

        // Check user login
        if (firebaseAuth.getCurrentUser() != null) {
            // Get the current user
            userID = firebaseAuth.getCurrentUser().getUid();
        }
        else{
            Toast.makeText(CreateNewAd.this, "Please login first!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }

        et_new_ad_title = findViewById(R.id.et_new_ad_title);
        et_new_ad_price = findViewById(R.id.et_new_ad_price);
        et_new_ad_contact = findViewById(R.id.et_new_ad_contact);
        et_new_ad_description = findViewById(R.id.et_new_ad_description);
        select_location_search = (Spinner) findViewById(R.id.select_location_search);
        chk_negotiable = (CheckBox) findViewById(R.id.chk_negotiable);

        btn_submit_ad = findViewById(R.id.btn_submit_ad);
        progressBar = findViewById(R.id.progress_bar);

        addImage1 = findViewById(R.id.img_btn_image1);
        addImage2 = findViewById(R.id.img_btn_image2);
        addImage3 = findViewById(R.id.img_btn_image3);

        ad = new Advertisement();

        // Assign images
        addImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_Gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(open_Gallery,1);
            }
        });

        addImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_Gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(open_Gallery,2);
            }
        });

        addImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_Gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(open_Gallery, 3);
            }
        });

        // if negotiable check box checked value get true
        chk_negotiable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chk_negotiable.isChecked()) {
                    negotiable_value = true;
                }
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
    }

    // Preview images in ImageButtons
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 && resultCode == RESULT_OK && data != null){
            imgURI1 = data.getData();
            addImage1.setImageURI(imgURI1);
        }
        if (requestCode ==2 && resultCode == RESULT_OK && data != null){
            imgURI2 = data.getData();
            addImage2.setImageURI(imgURI2);
        }
        if (requestCode ==3 && resultCode == RESULT_OK && data != null){
            imgURI3 = data.getData();
            addImage3.setImageURI(imgURI3);
        }
    }

    // Set Advertisement data
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveData(View view) {
        dbRef = FirebaseDatabase.getInstance().getReference().child(childRef);

        try{
            String title = et_new_ad_title.getText().toString();
            String location = select_location_search.getSelectedItem().toString();
            String price= et_new_ad_price.getText().toString();
            String contact = et_new_ad_contact.getText().toString();
            String description = et_new_ad_description.getText().toString();
            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


            // Check required fields
            if(TextUtils.isEmpty(title)){
                et_new_ad_title.setError("Title is required!");
            }
            else if(location.equals("Select your district")){
                Toast.makeText(CreateNewAd.this, "Please select your district!",
                        Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(price)){
                et_new_ad_price.setError("Price is required!");
            }
            else if(TextUtils.isEmpty(contact)){
                et_new_ad_contact.setError("Contact is required!");
            }
            else if(TextUtils.isEmpty(description)){
                et_new_ad_description.setError("Description is required!");
            }
            else if(imgURI1.equals(Uri.EMPTY)) {
                Toast.makeText(CreateNewAd.this, "Main image required!", Toast.LENGTH_SHORT).show();
            }
            else {
                // Set data to advertisement
                ad.setTitle(title.trim());
                ad.setLocation(location.trim());
                ad.setPrice(Float.valueOf(price.trim()));
                ad.setContact(Integer.valueOf(contact.trim()));
                ad.setDescription(description.trim());
                ad.setNegotiable(negotiable_value);
                ad.setDate(date);

                // Upload image to firebase and save database if main image upload success
                uploadFirebase(imgURI1);

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please enter valid data!", Toast.LENGTH_SHORT).show();
        }
    }

    // Upload images to firebase storage and get the url
    private void uploadFirebase(Uri uri) {
        adID = dbRef.push().getKey();
        StorageReference fileRef = storageReference.child(childRef).child(userID).child(adID).child("MainImage");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // Upload 2nd image
                if (!imgURI2.equals(Uri.EMPTY)){
                    StorageReference storageRef2 = storageReference.child(childRef).child(userID).child(adID).child("Image2");
                    storageRef2.putFile(imgURI2);
                }
                // Upload 3rd image
                if (!imgURI3.equals(Uri.EMPTY)) {
                    StorageReference storageRef3 = storageReference.child(childRef).child(userID).child(adID).child("Image3");
                    storageRef3.putFile(imgURI3);
                }

                // Save data in the database
                dbRef.child(userID).child(adID).setValue(ad)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(CreateNewAd.this, "Data saved successfully!",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MyAds.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateNewAd.this, "Data not saved!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.toString());
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(CreateNewAd.this,"Uploading Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goBack(View view) {

        finish();
    }

}