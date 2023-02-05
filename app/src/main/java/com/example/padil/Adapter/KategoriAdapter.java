package com.example.padil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.padil.Model.KategoriModel;
import com.example.padil.R;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder> {

    private Context context;
    private List<KategoriModel> list;

    public KategoriAdapter(Context context, List<KategoriModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public KategoriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.kategori_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.katImg);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView katImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            katImg = itemView.findViewById(R.id.kategori_img);
        }
    }
}
