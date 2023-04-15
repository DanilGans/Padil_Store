package com.example.padil.Adapter;

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
import com.example.padil.Model.SemuaProdukModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SemuaProdukAdapter extends RecyclerView.Adapter<SemuaProdukAdapter.ViewHolder> {

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    private Context context;
    private List<SemuaProdukModel> list;

    public SemuaProdukAdapter(Context context, List<SemuaProdukModel> list) {
        this.context = context;
        this.list = list;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public SemuaProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_produk, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SemuaProdukAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.mItemImage);
        holder.mCost.setText(formatRupiah.format((double) +list.get(position).getHarga()));
        holder.mTag.setText(list.get(position).getTagline());
        holder.mName.setText(list.get(position).getNama());

        if (auth.getCurrentUser() != null){
            firestore.collection("Users")
                    .document(auth.getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.getString("isAdmin") == null){
                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context, DetailProduk.class);
                                        intent.putExtra("detailproduk", list.get(position));
                                        context.startActivity(intent);
                                    }
                                });
                            }
                            else {

                            }
                        }
                    });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mItemImage;
        private TextView mCost, mName, mTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mItemImage = itemView.findViewById(R.id.item_image);
            mCost = itemView.findViewById(R.id.item_cost);
            mTag = itemView.findViewById(R.id.item_tag);
            mName = itemView.findViewById(R.id.item_name);
        }
    }
}
