package com.example.padil.Adapter;

import android.annotation.SuppressLint;
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
import com.example.padil.Activity.DetailProduk;
import com.example.padil.Model.ProdukPopulerModel;
import com.example.padil.R;

import java.util.List;

public class ProdukPopulerAdapter extends RecyclerView.Adapter<ProdukPopulerAdapter.ViewHolder> {

    private Context context;
    private List<ProdukPopulerModel> list;

    public ProdukPopulerAdapter(Context context, List<ProdukPopulerModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProdukPopulerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.produk_populer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukPopulerAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.newImg);
        holder.newNama.setText(list.get(position).getNama());
        holder.newTag.setText(list.get(position).getTagline());
        holder.newHarga.setText(String.valueOf(list.get(position).getHarga()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProduk.class);
                intent.putExtra("detailproduk", list.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView newImg;
        TextView newNama, newTag, newHarga;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newImg = itemView.findViewById(R.id.populer_img);
            newNama = itemView.findViewById(R.id.nama_pp);
            newTag = itemView.findViewById(R.id.tag_pp);
            newHarga = itemView.findViewById(R.id.harga_pp);

        }
    }
}
