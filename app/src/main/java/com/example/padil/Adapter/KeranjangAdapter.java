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
import com.example.padil.Model.KeranjangModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    Context context;
    List<KeranjangModel> list;
    int totalHargaSemua = 0;

    public KeranjangAdapter(Context context, List<KeranjangModel> list) {
        this.context = context;
        this.list = list;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public KeranjangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_keranjang, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KeranjangAdapter.ViewHolder holder, int position) {
        holder.namaProduk.setText(list.get(position).getNamaProduk());
        holder.hargaProduk.setText(list.get(position).getHargaProduk());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("Keranjang").document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .document(list.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    list.remove(list.get(position));
                                    notifyDataSetChanged();
                                    notifyItemRemoved(position);
                                    Toast.makeText(context, "Berhasil menghapus produk", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

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

        totalHargaSemua = totalHargaSemua + list.get(position).getTotalHarga();
        Intent intent = new Intent("MyTotalHarga");
        intent.putExtra("totalHargaSemua", totalHargaSemua);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView namaProduk, hargaProduk, totalHarga, totalKuantiti, variasi, cicilan;
        ImageView productImage, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namaProduk = itemView.findViewById(R.id.product_name);
            hargaProduk = itemView.findViewById(R.id.product_price);
            totalHarga = itemView.findViewById(R.id.total_price);
            totalKuantiti = itemView.findViewById(R.id.total_quantity);
            productImage = itemView.findViewById(R.id.product_image);
            variasi = itemView.findViewById(R.id.variasiText);
            cicilan = itemView.findViewById(R.id.cicilanText);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

    public void refresh(){
        getItemCount();
    }
}
