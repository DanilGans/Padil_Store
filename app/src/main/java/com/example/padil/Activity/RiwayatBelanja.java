package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.padil.Adapter.PesananAdapter;
import com.example.padil.Model.PesananModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RiwayatBelanja extends AppCompatActivity {

    BottomNavigationView nav;
    RecyclerView rvRiwayatBelanja;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    List<PesananModel> pesananModelList;
    PesananAdapter pesananAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_belanja);

        nav = findViewById(R.id.bottomNavigationView);
        rvRiwayatBelanja = findViewById(R.id.rvRiwayatBelanja);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        rvRiwayatBelanja.setLayoutManager(new LinearLayoutManager(this));
        pesananModelList = new ArrayList<>();
        pesananAdapter = new PesananAdapter(this, pesananModelList);
        rvRiwayatBelanja.setAdapter(pesananAdapter);
        getListPesanan();



        Menu menu = nav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.homeNav:
                        Intent intent4 = new Intent(RiwayatBelanja.this, MainActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.cartNav:
                        Intent intent1 = new Intent(RiwayatBelanja.this, Keranjang.class);
                        startActivity(intent1);
                        break;

                    case R.id.userNav:
                        Intent intent2 = new Intent(RiwayatBelanja.this, Profile.class);
                        startActivity(intent2);
                        break;

                    case R.id.infoNav:
                        Intent intent3 = new Intent(RiwayatBelanja.this, AboutUs.class);
                        startActivity(intent3);
                        break;

                    case R.id.historyNav:

                        break;
                }

                return false;
            }
        });
    }

    private void getListPesanan() {
        firestore.collection("Pesanan Masuk")
                .document(auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            if (doc.exists()){
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