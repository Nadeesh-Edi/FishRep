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
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fish.Login;
import com.example.fish.RegisterSeller;
import com.google.firebase.auth.FirebaseAuth;
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
    String userID="";
    FirebaseAuth firebaseAuth;

    ArrayList<Advertisement> list;

    MyAdAdapter adAdapter;
    Button nav_login, nav_logout;
    DrawerLayout drawerLayout;
    AlertDialog.Builder builder;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);
        Log.d(TAG, "onCreate: started");
        firebaseAuth = FirebaseAuth.getInstance();
        nav_login = findViewById(R.id.nav_login);
        nav_logout = findViewById(R.id.nav_logout);
        drawerLayout = findViewById(R.id.drawer_layout);
        progressBar = findViewById(R.id.progress_bar);

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
            builder.setCancelable(true);
            AlertDialog alert11 = builder.create();
            alert11.show();
        }

        // Check user login
        if (firebaseAuth.getCurrentUser() != null) {
            // Get the current user
            userID = firebaseAuth.getCurrentUser().getUid();
            nav_logout.setVisibility(View.VISIBLE);
            nav_login.setVisibility(View.GONE);
        }
        else{
            Toast.makeText(MyAds.this, "Please login first!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }

        recyclerView = findViewById(R.id.ad_listning_recyclerview);
        dbRef = FirebaseDatabase.getInstance().getReference("Advertisement");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adAdapter = new MyAdAdapter(this, list);
        recyclerView.setAdapter(adAdapter);

        assert userID != null;
        dbRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds: snapshot.getChildren()) {
                    Advertisement ad = ds.getValue(Advertisement.class);
                    ad.setKey(ds.getKey());
                    list.add(ad);
                }
                adAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    // ------------------------- START NAVIGATION DRAWER ---------------------------------------


    public void navClickLogin(View view) {
        Intent openMainActivity = new Intent(getApplicationContext(), Login.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(openMainActivity, 0);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void navClickLogout(View view) {
        FirebaseAuth.getInstance().signOut();
        nav_logout.setVisibility(View.GONE);
        nav_login.setVisibility(View.VISIBLE);
        Toast.makeText(MyAds.this, "Logging out...", Toast.LENGTH_SHORT).show();

        Intent openMainActivity = new Intent(getApplicationContext(), MainActivity.class);
        openMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityIfNeeded(openMainActivity, 0);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void navClickHome(View view) {
        Intent openMainActivity = new Intent(getApplicationContext(), MainActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(openMainActivity, 0);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void navClickPostAd(View view) {
        Intent openMainActivity = new Intent(getApplicationContext(), CreateNewAd.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(openMainActivity, 1);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void navClickMyAd(View view) {
        Intent openMainActivity = new Intent(getApplicationContext(), MyAds.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(openMainActivity, 2);
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