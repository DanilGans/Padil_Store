package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.padil.Adapter.SemuaProdukAdapter;
import com.example.padil.Model.SemuaProdukModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SemuaProduk extends AppCompatActivity {

    RecyclerView rvSemuaproduk;
    SemuaProdukAdapter semuaProdukAdapter;
    List<SemuaProdukModel> semuaProdukModelList;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semua_produk);

        firestore = FirebaseFirestore.getInstance();

        rvSemuaproduk = findViewById(R.id.semua_produk);
        rvSemuaproduk.setLayoutManager(new GridLayoutManager(this, 2));
        semuaProdukModelList = new ArrayList<>();
        semuaProdukAdapter = new SemuaProdukAdapter(this, semuaProdukModelList);
        rvSemuaproduk.setAdapter(semuaProdukAdapter);

        firestore.collection("Produk")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){

                                SemuaProdukModel semuaProdukModel = documentSnapshot.toObject(SemuaProdukModel.class)
                            }
                        }
                    }
                });
    }
}