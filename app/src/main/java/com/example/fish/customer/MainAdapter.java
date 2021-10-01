package com.example.fish.customer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fish.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<Customer,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<Customer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull Customer model) {

        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());
        holder.province.setText(model.getProvince());
        holder.phone.setText(model.getPhone().toString());
        holder.packet.setText(model.getPacket().toString());
        holder.quantity.setText(model.getQuantity().toString());


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_update_popup))
                        .setExpanded(true, 1200)
                        .create();
                //dialogPlus.show();
                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.txtName);
                EditText address = view.findViewById(R.id.txtAddress);
                EditText province = view.findViewById(R.id.txtProvince);
                EditText phone = view.findViewById(R.id.txtPhone);
                EditText packet = view.findViewById(R.id.txtPacket);
                EditText quantity = view.findViewById(R.id.txtQuantity);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                name.setText(model.getName());
                address.setText(model.getAddress());
                province.setText(model.getProvince());
                phone.setText(model.getPhone());
                packet.setText(model.getPacket());
                quantity.setText(model.getQuantity());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map =new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("address", address.getText().toString());
                        map.put("province", province.getText().toString());
                        map.put("phone", phone.getText().toString());
                        map.put("packet", packet.getText().toString());
                        map.put("quantity", quantity.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Customer")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Data Update Successfully Save", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error While updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are You Sure, Delete This Item ?");
                builder.setMessage("Delete data can't be Undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Customer")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });

                builder.setNegativeButton("Cancell", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(), "Cancell", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView name,address,province,phone,packet,quantity;

        Button btnEdit,btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.img1);
            name = itemView.findViewById(R.id.nametext);
            address =  itemView.findViewById(R.id.addresstext);
            province =  itemView.findViewById(R.id.provincetext);
            phone =  itemView.findViewById(R.id.phonetext);
            packet = itemView.findViewById(R.id.packettext);
            quantity = itemView.findViewById(R.id.qtytext);

            btnEdit= itemView.findViewById(R.id.btnEdit);
            btnDelete=  itemView.findViewById(R.id.btnDelete);

        }
    }
}
