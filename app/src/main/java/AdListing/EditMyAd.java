package AdListing;

import android.net.Uri;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class EditMyAd  extends AppCompatActivity {
    EditText et_new_ad_title, et_new_ad_price, et_new_ad_contact, et_new_ad_description;
    Spinner select_location_search;
    Button btn_submit_ad;
    ImageView addImage1, addImage2, addImage3;
    CheckBox negotiable;
    Boolean negotiable_value = false;

    ProgressBar progressBar;
    Uri imgURI1 = Uri.EMPTY, imgURI2 = Uri.EMPTY, imgURI3 = Uri.EMPTY;

    DatabaseReference dbRef;
    StorageReference storageReference;
    Advertisement ad;
    String userID;
    String childRef = "Advertisement";
}
