package com.example.fish.AdListing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fish.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyAdAdapter extends RecyclerView.Adapter<MyAdAdapter.AdViewHolder> {

    Context context;
    ArrayList<Advertisement> list;
    AlertDialog.Builder builder;
    String childRef = "Advertisement";
    String userID;
    FirebaseAuth firebaseAuth;
    private static final String TAG = "MyAdAdapter";

    public MyAdAdapter(Context context, ArrayList<Advertisement> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdAdapter.AdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.my_advertisement, parent, false);
        return  new MyAdAdapter.AdViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdAdapter.AdViewHolder holder, int position) {

        holder.progressBar.setVisibility(View.VISIBLE);
        Advertisement ad = list.get(position);
        holder.title.setText(ad.getTitle());
        holder.location.setText(ad.getLocation());
        holder.price.setText(ad.getPrice().toString());
//            holder.date.setText(ad.getDate());

        String adKey = ad.getKey();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(childRef).child(userID).child(adKey);

        // Set Image
        storageReference.child("MainImage").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (!uri.equals(Uri.EMPTY)) {
                    Glide.with(context).load(uri.toString()).into(holder.imageView);
                    holder.progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        // On click delete button
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Advertisement").setMessage("Are you sure you want to delete this?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                // Remove data from database
                                DatabaseReference databaseReference =
                                        FirebaseDatabase.getInstance().getReference()
                                                .child(childRef)
                                                .child(userID)
                                                .child(adKey);
                                databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        // Delete images from firebase storage
                                        StorageReference deleteRef = storageReference.child("MainImage");
                                        deleteRef.delete();

                                        deleteRef = storageReference.child("Image2");
                                        deleteRef.delete();

                                        deleteRef = storageReference.child("Image3");
                                        deleteRef.delete();

                                        Toast.makeText(context, "Delete Successful!", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Not deleted!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Cancel delete
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.changeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditAd.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("AD", ad);

                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class AdViewHolder extends RecyclerView.ViewHolder {

        TextView title, location, price, date;
        ImageView imageView, deleteButton;
        Button changeDetails;
        ProgressBar progressBar;

        public AdViewHolder(@NonNull View adView) {
            super(adView);

            title = adView.findViewById(R.id.tv_ad_box_title);
            location = adView.findViewById(R.id.tv_ad_box_location);
            price = adView.findViewById(R.id.tv_ad_box_price);
//                date = adView.findViewById(R.id.tv_ad_box_date);
            imageView = adView.findViewById(R.id.ad_box_img);
            changeDetails = adView.findViewById(R.id.btn_chanege_details);
            deleteButton = adView.findViewById(R.id.btn_delete_ad);
            progressBar = adView.findViewById(R.id.progressBar2);
        }
    }
}
