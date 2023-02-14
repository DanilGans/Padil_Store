package com.example.padil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.padil.Model.KeranjangModel;
import com.example.padil.R;

import java.util.List;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {

    Context context;
    List<KeranjangModel> list;

    public KeranjangAdapter(Context context, List<KeranjangModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public KeranjangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_keranjang, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KeranjangAdapter.ViewHolder holder, int position) {
        holder.namaProduk.setText(list.get(position).getNamaProduk());
        holder.hargaProduk.setText("Rp "+ list.get(position).getHargaProduk());
        holder.totalHarga.setText(String.valueOf(list.get(position).getTotalHarga()));
        holder.totalKuantiti.setText("Qty "+list.get(position).getTotalKuantiti());

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView namaProduk, hargaProduk, totalHarga, totalKuantiti;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namaProduk = itemView.findViewById(R.id.product_name);
            hargaProduk = itemView.findViewById(R.id.product_price);
            totalHarga = itemView.findViewById(R.id.total_price);
            totalKuantiti = itemView.findViewById(R.id.total_quantity);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }
}
