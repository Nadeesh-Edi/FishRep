package com.example.fish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.example.fish.AdListing.AdAdapter;
import com.example.fish.AdListing.Advertisement;
import com.example.fish.AdListing.CreateNewAd;
import com.example.fish.AdListing.MyAds;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    DatabaseReference dbRef;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;

    ArrayList<Advertisement> list;

    AdAdapter adAdapter;
    Button nav_login, nav_logout;
    AlertDialog.Builder builder;
    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

        drawerLayout = findViewById(R.id.drawer_layout);

        nav_login = findViewById(R.id.nav_login);
        nav_logout = findViewById(R.id.nav_logout);
        progressBar = findViewById(R.id.progress_bar);
        firebaseAuth = FirebaseAuth.getInstance();

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
            progressBar.setVisibility(View.INVISIBLE);
            alert11.show();
        }

        // Check user login
        if (firebaseAuth.getCurrentUser() != null) {
            nav_logout.setVisibility(View.VISIBLE);
            nav_login.setVisibility(View.GONE);
        }
        else {
            nav_login.setVisibility(View.VISIBLE);
            nav_logout.setVisibility(View.GONE);
        }

        recyclerView = findViewById(R.id.ad_listning_recyclerview);
        recyclerView.setHasFixedSize(true);
        dbRef = FirebaseDatabase.getInstance().getReference("Advertisement");
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(gridLayoutManager);

        list = new ArrayList<>();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        Advertisement ad = ds.getValue(Advertisement.class);
                        ad.setKey(ds.getKey());
                        ad.setUID(dataSnapshot.getKey());
                        list.add(ad);
                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    adAdapter = new AdAdapter(MainActivity.this, list);
                    recyclerView.setAdapter(adAdapter);
                    adAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Search bar
        searchView = findViewById(R.id.search_box);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adAdapter.getFilter().filter(s);
                return false;
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
        Toast.makeText(MainActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();

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
            finish();
            super.onBackPressed();
        }
    }
    // ------------------------------- END NAVIGATION DRAWER -------------------------------------
}


