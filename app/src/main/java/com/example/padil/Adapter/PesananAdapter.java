package com.example.padil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padil.Model.PesananModel;
import com.example.padil.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.ViewHolder> {
    Context context;
    List<PesananModel> list;

    public PesananAdapter(Context context, List<PesananModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PesananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pesanan, parent,  false));
    }

    @Override
    public void onBindViewHolder(@NonNull PesananAdapter.ViewHolder holder, int position) {
        holder.orderid.setText("Order ID : #"+list.get(position).getOrder_id());
        holder.alamat.setText(list.get(position).getAlamat());
        holder.totalharga.setText("Total :\n"+list.get(position).getTotalharga());
        holder.tanggal.setText(list.get(position).getTanggal());

        StringBuilder produkStringBuilder = new StringBuilder();
        for (String produk : list.get(position).getList_produk()){
            produkStringBuilder.append(produk).append("\n");
        }

        String produkString = produkStringBuilder.toString().trim();
        if (produkString.endsWith(",")){
            produkString = produkString.substring(0, produkString.length() - 1);
        }
        holder.list_produk.setText(produkString);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderid, list_produk, alamat, totalharga, tanggal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderid = itemView.findViewById(R.id.order_id);
            list_produk = itemView.findViewById(R.id.list_produkLP);
            alamat = itemView.findViewById(R.id.alamatLP);
            totalharga = itemView.findViewById(R.id.totalhargaLP);
            tanggal = itemView.findViewById(R.id.tanggal);
        }
    }
}
