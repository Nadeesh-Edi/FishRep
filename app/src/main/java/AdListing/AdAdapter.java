package AdListing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fish.R;

import java.util.ArrayList;

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.AdViewHolder> {

    Context context;
    ArrayList<Advertisement> list;

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

    @Override
    public void onBindViewHolder(@NonNull AdViewHolder holder, int position) {

        Advertisement ad = list.get(position);
        holder.title.setText(ad.getTitle());
        holder.location.setText(ad.getLocation());
        holder.price.setText(ad.getPrice().toString());
        holder.date.setText(ad.getDate());

        Glide.with(context).load(list.get(position).getImageUrlMain()).into(holder.imageView);

        // Send data to the selected ad view when select on the ad
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewSelectedAd.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Title", ad.getTitle());
                intent.putExtra("Location", ad.getLocation());
                intent.putExtra("Contact", ad.getContact().toString());
                intent.putExtra("Price", ad.getPrice().toString());
                intent.putExtra("Date", ad.getDate());
                intent.putExtra("Description", ad.getDescription());
                intent.putExtra("MainImage", ad.getImageUrlMain());

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

        public AdViewHolder(@NonNull View adView) {
            super(adView);

            title = adView.findViewById(R.id.tv_ad_box_title);
            location = adView.findViewById(R.id.tv_ad_box_location);
            price = adView.findViewById(R.id.tv_ad_box_price);
            date = adView.findViewById(R.id.tv_ad_box_date);
            imageView = adView.findViewById(R.id.ad_box_img);
        }
    }
}
