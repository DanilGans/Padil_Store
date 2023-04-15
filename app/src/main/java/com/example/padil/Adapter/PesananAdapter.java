package com.example.padil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padil.Model.PesananModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.ViewHolder> {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Context context;
    List<PesananModel> list;

    public PesananAdapter(Context context, List<PesananModel> list) {
        this.context = context;
        this.list = list;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
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
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("Pesanan Masuk")
                        .document(list.get(position).getId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    list.remove(list.get(position));
                                    notifyDataSetChanged();
                                    notifyItemRemoved(position);
                                    Toast.makeText(context, "Berhasil menghapus pesanan", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        //VISIBILITY DELETE BTN DI PESANAN
        firestore.collection("Users")
                .document(auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            if (doc.exists()){
                                String isAdmin = doc.getString("isAdmin");
                                if (isAdmin == null){
                                    holder.deletebtn.setVisibility(View.GONE);
                                }else {
                                    holder.deletebtn.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderid, list_produk, alamat, totalharga, tanggal;
        ImageView deletebtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderid = itemView.findViewById(R.id.order_id);
            list_produk = itemView.findViewById(R.id.list_produkLP);
            alamat = itemView.findViewById(R.id.alamatLP);
            totalharga = itemView.findViewById(R.id.totalhargaLP);
            tanggal = itemView.findViewById(R.id.tanggal);
            deletebtn = itemView.findViewById(R.id.deletePsnBtn);
        }
    }
}
