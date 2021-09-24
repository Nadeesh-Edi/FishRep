package AdListing;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fish.R;

public class ViewSelectedAd extends AppCompatActivity {

    TextView tv_title, tv_location, tv_contact, tv_price, tv_description;
    String title, location, contact, price, description, mainImageURL;
    ImageView mainImage;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected_ad);

        title = getIntent().getExtras().getString("Title");
        location = getIntent().getExtras().getString("Location");
        contact = getIntent().getExtras().getString("Contact");
        price = getIntent().getExtras().getString("Price");
        description = getIntent().getExtras().getString("Description");
        mainImageURL = getIntent().getExtras().getString("MainImage");

        tv_title = findViewById(R.id.tv_selected_ad_title2);
        tv_location = findViewById(R.id.tv_selected_ad_location);
        tv_contact = findViewById(R.id.tv_selected_ad_contact);
        tv_price = findViewById(R.id.tv_selected_ad_price);
        tv_description = findViewById(R.id.tv_selected_ad_description);
        mainImage = findViewById(R.id.img_selected_ad_image);

        tv_title.setText(title);
        tv_location.setText(location);
        tv_contact.setText("0"+contact);
        tv_price.setText(price);
        tv_description.setText(description);

        Glide.with(ViewSelectedAd.this).load(mainImageURL).into(mainImage);


    }

    public void goBack(View view) {
        finish();
    }
}