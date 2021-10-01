package com.example.fish.AdListing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fish.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.AdViewHolder> {

    private static final String TAG = "AdAdapter";
    Context context;
    ArrayList<Advertisement> list;
    ArrayList<Advertisement> listFull;
    String childRef = "Advertisement";
    String userID;
    FirebaseAuth firebaseAuth;
    Date currentDate, postedDate;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    public AdAdapter() {}

    public AdAdapter(Context context, ArrayList<Advertisement> list) {
        this.context = context;
        this.listFull = list;
        this.list = new ArrayList<>(listFull);
    }

    @NonNull
    @Override
    public AdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.advetisement, parent, false);
        return  new AdViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdViewHolder holder, int position) {

        Advertisement ad = list.get(position);

        try {
            currentDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            postedDate = simpleDateFormat.parse(ad.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.progressBar.setVisibility(View.VISIBLE);
        holder.imageView.setVisibility(View.INVISIBLE);

        holder.title.setText(ad.getTitle());
        holder.location.setText(ad.getLocation());
        holder.price.setText(ad.getPrice().toString());
//        holder.date.setText(ad.getDate());

        String adKey = ad.getKey();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = ad.getUID();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(childRef).child(userID).child(adKey);


        // Set Image
        storageReference.child("MainImage").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (!uri.equals(Uri.EMPTY)) {
                    Glide.with(context).load(uri.toString()).into(holder.imageView);
                    holder.imageView.setVisibility(View.VISIBLE);
                    holder.progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        //display days on site
        Long dateDifference = getDateDifference(postedDate,currentDate);
        Log.d(TAG, dateDifference.toString());
        if (dateDifference==0){
            holder.date.setText("Less than 24h ago");
        }else if(dateDifference==1){
            holder.date.setText(dateDifference.toString()+" Day ago");
        }else{
            holder.date.setText(dateDifference.toString()+" Days ago");
        }



        // Send data to the selected ad view when select on the ad
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewSelectedAd.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("AD", ad);

                view.getContext().startActivity(intent);
            }
        });

    }


    // Function to get the time difference
    public Long getDateDifference(Date postedDate, Date currentDate) {
        long timeDiff = currentDate.getTime() - postedDate.getTime();
        TimeUnit time = TimeUnit.DAYS;

        return time.convert(timeDiff, time.MILLISECONDS);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    // Search
    public Filter getFilter() {
        return adFilter;
    }

    private final Filter adFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Advertisement> filteredAdList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredAdList.addAll(listFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Advertisement advertisement : listFull) {
                    if (advertisement.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredAdList.add(advertisement);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredAdList;
            filterResults.count = filteredAdList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    // View holder
    public static class AdViewHolder extends RecyclerView.ViewHolder {

        TextView title, location, price, date;
        ImageView imageView;
        ProgressBar progressBar;

        public AdViewHolder(@NonNull View adView) {
            super(adView);

            title = adView.findViewById(R.id.tv_ad_box_title);
            location = adView.findViewById(R.id.tv_ad_box_location);
            price = adView.findViewById(R.id.tv_ad_box_price);
            date = adView.findViewById(R.id.tv_ad_box_date);
            imageView = adView.findViewById(R.id.ad_box_img);
            progressBar = adView.findViewById(R.id.progressBar);
        }
    }
}
