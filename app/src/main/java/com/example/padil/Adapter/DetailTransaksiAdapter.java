package com.example.padil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.padil.Model.DetailTransaksiModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DetailTransaksiAdapter extends RecyclerView.Adapter<DetailTransaksiAdapter.ViewHolder> {

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    Context context;
    List<DetailTransaksiModel> list;

    int SubTotal = 0;
    int TotalSemuaKP = 0;

    public DetailTransaksiAdapter(Context context, List<DetailTransaksiModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DetailTransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_keranjang, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailTransaksiAdapter.ViewHolder holder, int position) {
        holder.namaProduk.setText(list.get(position).getNamaProduk());
        holder.hargaProduk.setText(list.get(position).getHargaProduk());
        holder.deleteBtn.setVisibility(View.GONE);

        if (list.get(position).getVariasi() == null) {
            holder.variasi.setVisibility(View.GONE);
        } else {
            holder.variasi.setText("Variant : "+ list.get(position).getVariasi());
            holder.variasi.setVisibility(View.VISIBLE);
        }
        if (list.get(position).getCicilan() == null){
            holder.cicilan.setVisibility(View.GONE);
        } else {
            holder.cicilan.setText("Cicilan : "+ list.get(position).getCicilan());
            holder.cicilan.setVisibility(View.VISIBLE);
            holder.variasi.setVisibility(View.GONE);
        }

        holder.totalHarga.setText((formatRupiah.format((double)list.get(position).getTotalHarga())));
        holder.totalKuantiti.setText("Qty "+list.get(position).getTotalKuantiti());

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.productImage);

        SubTotal = SubTotal + list.get(position).getTotalHarga();
        Intent intentSub = new Intent ("MySubTotal");
        intentSub.putExtra("subTotalDT", SubTotal);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intentSub);

        TotalSemuaKP = TotalSemuaKP + SubTotal;
        Intent intentKP = new Intent ("MyTotalKP");
        intentKP.putExtra("totalKP", TotalSemuaKP);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intentKP);

        ////////////////////////////////////////////////////////////////////////
        String NamaProduk, Variasi, TotalKuantiti;
        NamaProduk = list.get(position).getNamaProduk();
        Variasi = list.get(position).getVariasi();
        TotalKuantiti = list.get(position).getTotalKuantiti();

        Intent intent2 = new Intent("ProductList");
        intent2.putExtra("namaProduk", NamaProduk);
        intent2.putExtra("variasi", Variasi);
        intent2.putExtra("totalKuantiti", TotalKuantiti);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
        ////////////////////////////////////////////////////////////////////////

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView namaProduk, hargaProduk, totalHarga, totalKuantiti, variasi, subtotalDT, ongkirDT, totalDT, cicilan;
        ImageView productImage, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namaProduk = itemView.findViewById(R.id.product_name);
            hargaProduk = itemView.findViewById(R.id.product_price);
            totalHarga = itemView.findViewById(R.id.total_price);
            totalKuantiti = itemView.findViewById(R.id.total_quantity);
            productImage = itemView.findViewById(R.id.product_image);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            subtotalDT = itemView.findViewById(R.id.subtotalDT);
            ongkirDT = itemView.findViewById(R.id.ongkirDT);
            totalDT = itemView.findViewById(R.id.totalDT);
            variasi = itemView.findViewById(R.id.variasiText);
            cicilan = itemView.findViewById(R.id.cicilanText);
        }
    }
}
