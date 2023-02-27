package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.padil.Adapter.AlamatAdapter;
import com.example.padil.Model.AlamatModel;
import com.example.padil.Model.KeranjangModel;
import com.example.padil.Model.ProdukPopulerModel;
import com.example.padil.Model.SemuaProdukModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AlamatPengiriman extends AppCompatActivity implements AlamatAdapter.SelectedAddress {

    Button addAlamat, payment;
    RecyclerView rvAlamat;
    private List<AlamatModel> alamatModelList;
    private AlamatAdapter alamatAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String mAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alamat_pengiriman);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        //Ambil data dari detail produk
        Object obj = getIntent().getSerializableExtra("item");

        rvAlamat = findViewById(R.id.alamat_rv);
        addAlamat = findViewById(R.id.addalamat_btn);
        payment = findViewById(R.id.payment_btn);

        rvAlamat.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        alamatModelList = new ArrayList<>();
        alamatAdapter = new AlamatAdapter(getApplicationContext(), alamatModelList, this);
        rvAlamat.setAdapter(alamatAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("Alamat")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot doc : task.getResult().getDocuments()){

                                AlamatModel alamatModel = doc.toObject(AlamatModel.class);
                                alamatModelList.add(alamatModel);
                                alamatAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });


        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double amount = 0.0;
                if (obj instanceof ProdukPopulerModel){
                    ProdukPopulerModel produkPopulerModel = (ProdukPopulerModel) obj;
                    amount = produkPopulerModel.getHarga();
                }
                if (obj instanceof SemuaProdukModel){
                    SemuaProdukModel semuaProdukModel = (SemuaProdukModel) obj;
                    amount = semuaProdukModel.getHarga();
                }

                Intent intent = new Intent(AlamatPengiriman.this, Pembayaran.class);
                intent.putExtra("amount", amount);
                startActivity(intent);

            }
        });

        addAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlamatPengiriman.this, TambahAlamat.class));
            }
        });

    }

    @Override
    public void setAlamat(String alamat) {

        mAlamat = alamat;
    }

}