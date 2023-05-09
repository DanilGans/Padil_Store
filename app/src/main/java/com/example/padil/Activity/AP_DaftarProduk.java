package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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

public class AP_DaftarProduk extends AppCompatActivity {

    RecyclerView daftarprodukRV;
    SemuaProdukAdapter semuaProdukAdapter;
    List<SemuaProdukModel> semuaProdukModelList;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ap_daftar_produk);

        firestore = FirebaseFirestore.getInstance();

        daftarprodukRV = findViewById(R.id.daftarprodukRV);
        daftarprodukRV.setLayoutManager(new GridLayoutManager(this, 2));
        semuaProdukModelList = new ArrayList<>();
        semuaProdukAdapter = new SemuaProdukAdapter(this, semuaProdukModelList);
        daftarprodukRV.setAdapter(semuaProdukAdapter);

        getProductNonType();
        getProductTypePapanPVC();
        getProductTypeAksesoris();
        getProductTypeMaterial();
        getProductTypeOrnament();
        getProductTypeWallpaper();
        getProductTypePemasangan();

    }

    private void getProductTypePemasangan() {

        String type = getIntent().getStringExtra("type");

        if (type != null && type.equalsIgnoreCase("pemasangan")){
            firestore.collection("Produk").whereEqualTo("type", "pemasangan")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()){
                                for (DocumentSnapshot doc : task.getResult().getDocuments()){

                                    String documentId = doc.getId();

                                    SemuaProdukModel semuaProdukModel = doc.toObject(SemuaProdukModel.class);
                                    semuaProdukModel.setDocumentId(documentId);
                                    semuaProdukModelList.add(semuaProdukModel);
                                    semuaProdukAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }
    }

    private void getProductTypeWallpaper() {

        String type = getIntent().getStringExtra("type");

        if (type != null && type.equalsIgnoreCase("wallpaper")){
            firestore.collection("Produk").whereEqualTo("type", "wallpaper")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()){
                                for (DocumentSnapshot doc : task.getResult().getDocuments()){
                                    String documentId = doc.getId();

                                    SemuaProdukModel semuaProdukModel = doc.toObject(SemuaProdukModel.class);
                                    semuaProdukModel.setDocumentId(documentId);
                                    semuaProdukModelList.add(semuaProdukModel);
                                    semuaProdukAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }
    }

    private void getProductTypeOrnament() {

        String type = getIntent().getStringExtra("type");

        if (type != null && type.equalsIgnoreCase("ornament")){
            firestore.collection("Produk").whereEqualTo("type", "ornament")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()){
                                for (DocumentSnapshot doc : task.getResult().getDocuments()){
                                    String documentId = doc.getId();

                                    SemuaProdukModel semuaProdukModel = doc.toObject(SemuaProdukModel.class);
                                    semuaProdukModel.setDocumentId(documentId);
                                    semuaProdukModelList.add(semuaProdukModel);
                                    semuaProdukAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }
    }

    private void getProductTypeMaterial() {

        String type = getIntent().getStringExtra("type");

        if (type != null && type.equalsIgnoreCase("material")){
            firestore.collection("Produk").whereEqualTo("type", "material")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (DocumentSnapshot doc : task.getResult().getDocuments()){
                                    String documentId = doc.getId();

                                    SemuaProdukModel semuaProdukModel = doc.toObject(SemuaProdukModel.class);
                                    semuaProdukModel.setDocumentId(documentId);
                                    semuaProdukModelList.add(semuaProdukModel);
                                    semuaProdukAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }
    }

    private void getProductTypeAksesoris() {

        String type = getIntent().getStringExtra("type");

        if (type != null && type.equalsIgnoreCase("aksesoris")){
            firestore.collection("Produk").whereEqualTo("type", "aksesoris")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (DocumentSnapshot doc : task.getResult().getDocuments()){
                                    String documentId = doc.getId();

                                    SemuaProdukModel semuaProdukModel = doc.toObject(SemuaProdukModel.class);
                                    semuaProdukModel.setDocumentId(documentId);
                                    semuaProdukModelList.add(semuaProdukModel);
                                    semuaProdukAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }
    }

    private void getProductTypePapanPVC() {

        String type = getIntent().getStringExtra("type");

        if (type != null && type.equalsIgnoreCase("papan_pvc")){
            firestore.collection("Produk").whereEqualTo("type", "papan_pvc")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (DocumentSnapshot doc : task.getResult().getDocuments()){
                                    String documentId = doc.getId();

                                    SemuaProdukModel semuaProdukModel = doc.toObject(SemuaProdukModel.class);
                                    semuaProdukModel.setDocumentId(documentId);
                                    semuaProdukModelList.add(semuaProdukModel);
                                    semuaProdukAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }

    private void getProductNonType() {

        String type = getIntent().getStringExtra("type");

        if (type == null || type.isEmpty()) {
            firestore.collection("Produk")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                    String documentId = doc.getId();

                                    SemuaProdukModel semuaProdukModel = doc.toObject(SemuaProdukModel.class);
                                    semuaProdukModel.setDocumentId(documentId);
                                    semuaProdukModelList.add(semuaProdukModel);
                                    semuaProdukAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }
    }
}