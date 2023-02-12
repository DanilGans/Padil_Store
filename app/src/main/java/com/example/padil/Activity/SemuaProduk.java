package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

    RecyclerView recyclerView;
    SemuaProdukAdapter semuaProdukAdapter;
    List<SemuaProdukModel> semuaProdukModelList;
    ImageView BackBtnSP;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semua_produk);

        String type = getIntent().getStringExtra("type");

        firestore = FirebaseFirestore.getInstance();

        BackBtnSP = findViewById(R.id.backBtnSP);
        BackBtnSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.show_allProdukRV);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        semuaProdukModelList = new ArrayList<>();
        semuaProdukAdapter = new SemuaProdukAdapter(this, semuaProdukModelList);
        recyclerView.setAdapter(semuaProdukAdapter);

        if (type == null || type.isEmpty()){
            firestore.collection("Produk")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()){
                                for (DocumentSnapshot doc : task.getResult().getDocuments()){

                                    SemuaProdukModel semuaProdukModel = doc.toObject(SemuaProdukModel.class);
                                    semuaProdukModelList.add(semuaProdukModel);
                                    semuaProdukAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("papan_pvc")){
            firestore.collection("Produk").whereEqualTo("type", "papan_pvc")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()){
                                for (DocumentSnapshot doc : task.getResult().getDocuments()){

                                    SemuaProdukModel semuaProdukModel = doc.toObject(SemuaProdukModel.class);
                                    semuaProdukModelList.add(semuaProdukModel);
                                    semuaProdukAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("aksesoris")){
            firestore.collection("Produk").whereEqualTo("type", "aksesoris")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()){
                                for (DocumentSnapshot doc : task.getResult().getDocuments()){

                                    SemuaProdukModel semuaProdukModel = doc.toObject(SemuaProdukModel.class);
                                    semuaProdukModelList.add(semuaProdukModel);
                                    semuaProdukAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }

    }
}