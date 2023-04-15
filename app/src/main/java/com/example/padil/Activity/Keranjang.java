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
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Keranjang extends AppCompatActivity {

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    RecyclerView recyclerView;
    List<KeranjangModel> keranjangModelList;
    KeranjangAdapter keranjangAdapter;
    BottomNavigationView nav;
    Button bayarsekarang;
    TextView totalHargaCart;

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

                                String documentId = doc.getId();

                                KeranjangModel keranjangModel = doc.toObject(KeranjangModel.class);
                                keranjangModel.setDocumentId(documentId);
                                keranjangModelList.add(keranjangModel);
                                keranjangAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });


        bayarsekarang = findViewById(R.id.bayarsekarangBtn);
        bayarsekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("Keranjang").document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    QuerySnapshot querySnapshot = task.getResult();
                                    if (querySnapshot.isEmpty()){
                                        Toast.makeText(Keranjang.this, "Anda belum mempunyai produk di keranjang!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Intent intent = new Intent(Keranjang.this, DetailTransaksi.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        });
            }
        });

        totalHargaCart = findViewById(R.id.totalharga_cart);

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalHarga"));

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

                    case R.id.userNav:
                        Intent intent2 = new Intent(Keranjang.this, Profile.class);
                        startActivity(intent2);
                        break;

                    case R.id.infoNav:
                        Intent intent3 = new Intent(Keranjang.this, AboutUs.class);
                        startActivity(intent3);
                        break;

                    case R.id.historyNav:
                        Intent intent4 = new Intent(Keranjang.this, RiwayatBelanja.class);
                        startActivity(intent4);
                        break;
                }

                return false;
            }
        });

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("totalHargaSemua", 0);
            totalHargaCart.setText(formatRupiah.format((double) + totalBill));
            return;
        }
    };
}