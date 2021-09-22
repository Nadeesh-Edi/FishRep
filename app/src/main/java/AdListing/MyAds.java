package AdListing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.fish.MainActivity;
import com.example.fish.R;

import java.util.ArrayList;

public class MyAds extends AppCompatActivity {
    private static final String TAG = "MyAdvertisements";

    DatabaseReference dbRef;
    RecyclerView recyclerView;
    String userID;

    ArrayList<Advertisement> list;

    AdAdapter adAdapter;
    Button nav_login, nav_register;
    DrawerLayout drawerLayout;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);
        Log.d(TAG, "onCreate: started");

        drawerLayout = findViewById(R.id.drawer_layout);

        nav_login = findViewById(R.id.nav_login);
        nav_register = findViewById(R.id.nav_register);

        // Check network connectivity
        boolean connected = false;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() ==
                NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() ==
                        NetworkInfo.State.CONNECTED) {
            //connected to a network
            connected = true;
        }

        if(!connected) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage("Not Connected to Network");
            builder.setCancelable(false);
            AlertDialog alert11 = builder.create();
            alert11.show();
        }

        recyclerView = findViewById(R.id.ad_listning_recyclerview);
        dbRef = FirebaseDatabase.getInstance().getReference("Advertisement");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adAdapter = new AdAdapter(this, list);
        recyclerView.setAdapter(adAdapter);

        userID = "user2";

        dbRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()) {
                    Advertisement ad = ds.getValue(Advertisement.class);
                    list.add(ad);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // ------------------------- START NAVIGATION DRAWER ---------------------------------------
    public void navClickHome(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void navClickPostAd(View view) {
        startActivity(new Intent(getApplicationContext(), CreateNewAd.class));
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void navClickMyAd(View view) {
        startActivity(new Intent(getApplicationContext(), MyAds.class));
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void navClickOrders(View view) {

    }

    public void ClickMenu(View view) {
        // Open nav drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    // ------------------------------- END NAVIGATION DRAWER -------------------------------------

}