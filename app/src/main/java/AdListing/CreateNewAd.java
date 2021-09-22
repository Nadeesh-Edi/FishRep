package AdListing;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.fish.R;

public class CreateNewAd extends AppCompatActivity {

    EditText et_new_ad_title, et_new_ad_price, et_new_ad_contact, et_new_ad_description;
    Spinner select_location_search;
    Button btn_submit_ad;
    ImageView addImage1, addImage2, addImage3;

    ProgressBar progressBar;
    Uri imgURI1 = Uri.EMPTY, imgURI2 = Uri.EMPTY, imgURI3 = Uri.EMPTY;

    DatabaseReference dbRef;
    StorageReference storageReference;
    Advertisement ad;
    String userID;
    String childName = "Advertisement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_ad);

        et_new_ad_title = findViewById(R.id.et_new_ad_title);
        et_new_ad_price = findViewById(R.id.et_new_ad_price);
        et_new_ad_contact = findViewById(R.id.et_new_ad_contact);
        et_new_ad_description = findViewById(R.id.et_new_ad_description);
        select_location_search = (Spinner) findViewById(R.id.select_location_search);
        btn_submit_ad = findViewById(R.id.btn_submit_ad);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

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
        dbRef = FirebaseDatabase.getInstance().getReference().child(childName);
        storageReference = FirebaseStorage.getInstance().getReference();

        try{
            String title = et_new_ad_title.getText().toString();
            String location = select_location_search.getSelectedItem().toString();
            String price= et_new_ad_price.getText().toString();
            String contact = et_new_ad_contact.getText().toString();
            String description = et_new_ad_description.getText().toString();

            // Get the current user
            userID = "user2";

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
            else if(imgURI1 == null) {
                Toast.makeText(CreateNewAd.this, "Main image required!", Toast.LENGTH_SHORT).show();
            }
            else {
                // Set data to advertisement
                ad.setTitle(title.trim());
                ad.setLocation(location.trim());
                ad.setPrice(Float.valueOf(price.trim()));
                ad.setContact(Integer.valueOf(contact.trim()));
                ad.setDescription(description.trim());
                ad.setNegotiable(Boolean.TRUE);
                ad.setDate();

                // Upload image to firebase and save database
                uploadFirebase(imgURI1);

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please enter valid data!", Toast.LENGTH_SHORT).show();
        }
    }

    // Upload images to firebase storage and get the url
    private void uploadFirebase(Uri uri) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        // Add image url to the advertisement
                        ad.setImageUrlMain(uri.toString());

                        // Save data in the database
                        dbRef.child(userID).push().setValue(ad)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(CreateNewAd.this, "Data saved Successfully!",
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
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(CreateNewAd.this,"Uploading Faild!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Get the file extension
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        System.out.println(extension);
        return extension;
    }

    public void goBack(View view) {
        finish();
    }

}