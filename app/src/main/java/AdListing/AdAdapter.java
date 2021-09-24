package AdListing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fish.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.AdViewHolder> {

    Context context;
    ArrayList<Advertisement> list;
    String childRef = "Advertisement";
    String userID;
    FirebaseAuth firebaseAuth;

    public AdAdapter(Context context, ArrayList<Advertisement> list) {
        this.context = context;
        this.list = list;
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

        holder.progressBar.setVisibility(View.VISIBLE);
        Advertisement ad = list.get(position);
        holder.title.setText(ad.getTitle());
        holder.location.setText(ad.getLocation());
        holder.price.setText(ad.getPrice().toString());
        holder.date.setText(ad.getDate());

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
                    holder.progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });


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

    @Override
    public int getItemCount() {
        return list.size();
    }

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
