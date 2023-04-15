package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.padil.Adapter.PesananAdapter;
import com.example.padil.Model.PesananModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class AP_Pesanan extends AppCompatActivity {

    RecyclerView rvPesanan;
    FirebaseFirestore firestore;

    List<PesananModel> pesananModelList;
    PesananAdapter pesananAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ap_pesanan);

        rvPesanan = findViewById(R.id.rv_pesanan);
        firestore = FirebaseFirestore.getInstance();

        rvPesanan.setLayoutManager(new LinearLayoutManager(this));
        pesananModelList = new ArrayList<>();
        pesananAdapter = new PesananAdapter(this, pesananModelList);
        rvPesanan.setAdapter(pesananAdapter);
        getListPesanan();
    }

    private void getListPesanan() {
        firestore.collection("Pesanan Masuk")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot doc : task.getResult().getDocuments()){
                                PesananModel pesananModel = new PesananModel();

                                String documentId = doc.getId();

                                pesananModel.setOrder_id(doc.getString("order_id"));
                                pesananModel.setAlamat(doc.getString("alamat"));
                                pesananModel.setTotalharga(doc.getString("totalharga"));
                                pesananModel.setList_produk((ArrayList<String>) doc.get("list_produk"));
                                pesananModel.setTanggal(doc.getString("tanggal"));
                                pesananModel.setId(documentId);

                                pesananModelList.add(pesananModel);
                                pesananAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}