package AdListing;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fish.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewSelectedAd extends AppCompatActivity {

    TextView tv_title, tv_location, tv_contact, tv_price, tv_description;
    String title, location, contact, price, description, mainImageURL, image2URL, image3URL;
    ImageView mainImage, image2, image3;
    private final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseAuth firebaseAuth;
    String userID;
    String childRef = "Advertisement";
    String adID;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected_ad);

        Advertisement adDet = (Advertisement) getIntent().getSerializableExtra("AD");
        adID = adDet.getKey();
        userID = adDet.getUID();
        StorageReference getImages = storageReference.child(childRef).child(userID).child(adID);

        title = adDet.getTitle();
        location = adDet.getLocation();
        contact = adDet.getContact().toString();
        price = adDet.getPrice().toString();
        description = adDet.getDescription();
        mainImageURL = getImages.child("MainImage").toString();
        image2URL = getImages.child("Image2").toString();
        image3URL = getImages.child("Image3").toString();


        tv_title = findViewById(R.id.tv_selected_ad_title2);
        tv_location = findViewById(R.id.tv_selected_ad_location);
        tv_contact = findViewById(R.id.tv_selected_ad_contact);
        tv_price = findViewById(R.id.tv_selected_ad_price);
        tv_description = findViewById(R.id.tv_selected_ad_description);
        mainImage = findViewById(R.id.img_selected_ad_image);

        tv_title.setText(title);
        tv_location.setText(location);
        tv_contact.setText(contact);
        tv_price.setText(price);
        tv_description.setText(description);

        Glide.with(ViewSelectedAd.this).load(mainImageURL).into(mainImage);


    }

    public void goBack(View view) {
        finish();
    }
}