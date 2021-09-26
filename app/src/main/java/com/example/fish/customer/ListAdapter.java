package com.example.fish.customer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import com.example.fish.R;

public class ListAdapter extends ArrayAdapter {

    private final Activity mContext;
    List<Customer> customerList;

    public ListAdapter(Activity mContext, List<Customer> customerList) {
        super(mContext,R.layout.list_item,customerList);
        this.mContext = mContext;
        this.customerList = customerList;
    }

    //    public ListAdapterBuy(Activity mContext, List<Customer> customerList);

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item, null, true);

        TextView tvName = listItemView.findViewById(R.id.tvName);
        TextView tvAddress = listItemView.findViewById(R.id.tvAddress);
        TextView tvProvince = listItemView.findViewById(R.id.tvProvince);
        TextView tvPhone = listItemView.findViewById(R.id.tvPhone);
        TextView tvPacket = listItemView.findViewById(R.id.tvPacket);
        TextView tvQty = listItemView.findViewById(R.id.tvQty);

        Customer cus = customerList.get(position);

        tvName.setText(cus.getName());
        tvAddress.setText(cus.getAddress());
        tvProvince.setText(cus.getProvince());
        tvPhone.setText(cus.getPhone());
        tvPacket.setText(cus.getPacket());
        tvQty.setText(cus.getQuantity());

        return listItemView;

    }
}
