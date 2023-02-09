package com.example.padil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.padil.Activity.SemuaProduk;
import com.example.padil.Model.SemuaProdukModel;
import com.example.padil.R;

import java.util.List;

public class SemuaProdukAdapter extends RecyclerView.Adapter<SemuaProdukAdapter.ViewHolder> {

    private Context context;
    private List<SemuaProdukModel> list;

    public SemuaProdukAdapter(SemuaProduk semuaProduk, List<SemuaProdukModel> semuaProdukModelList) {
    }

    @NonNull
    @Override
    public SemuaProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.semua_produk, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SemuaProdukAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.SPimage);
        holder.SPnama.setText(list.get(position).getNama());
        holder.SPtag.setText(list.get(position).getTagline());
        holder.SPharga.setText(list.get(position).getHarga());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView SPimage;
        private TextView SPnama, SPtag, SPharga;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            SPimage = itemView.findViewById(R.id.semuaproduk_img);
            SPnama = itemView.findViewById(R.id.nama_sp);
            SPtag = itemView.findViewById(R.id.tag_sp);
            SPharga = itemView.findViewById(R.id.harga_sp);

        }
    }
}
