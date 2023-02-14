package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.padil.Adapter.KeranjangAdapter;
import com.example.padil.Model.KeranjangModel;
import com.example.padil.Model.ProdukPopulerModel;
import com.example.padil.Model.SemuaProdukModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Keranjang extends AppCompatActivity {

    RecyclerView recyclerView;
    List<KeranjangModel> keranjangModelList;
    KeranjangAdapter keranjangAdapter;
    BottomNavigationView nav;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.keranjang_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        keranjangModelList = new ArrayList<>();
        keranjangAdapter = new KeranjangAdapter(this, keranjangModelList);
        recyclerView.setAdapter(keranjangAdapter);

        firestore.collection("Keranjang").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot doc : task.getResult().getDocuments()){

                                KeranjangModel keranjangModel = doc.toObject(KeranjangModel.class);
                                keranjangModelList.add(keranjangModel);
                                keranjangAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        nav = findViewById(R.id.bottomNavigationView);

        Menu menu = nav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.homeNav:
                        Intent intent1 = new Intent(Keranjang.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.cartNav:

                        break;

                    case R.id.notifNav:
                        Intent intent2 = new Intent(Keranjang.this, Notifikasi.class);
                        startActivity(intent2);
                        break;
                }

                return false;
            }
        });

    }
}