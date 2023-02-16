package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.padil.Model.ProdukPopulerModel;
import com.example.padil.Model.SemuaProdukModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailProduk extends AppCompatActivity {

    ImageView imgProduk;
    TextView namaProduk, hargaProduk, deskripsiProduk, Kuantiti;
    ImageView addItem, removeItem, addtoCart, buyNow, backBtnDP;

    int totalKuantiti = 1;
    int totalHarga = 0;

    //Produk Populer
    ProdukPopulerModel produkPopulerModel = null;

    //Semua Produk
    SemuaProdukModel semuaProdukModel = null;

    FirebaseAuth auth;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailproduk");

        if (obj instanceof ProdukPopulerModel){
            produkPopulerModel = (ProdukPopulerModel) obj;
        }else if (obj instanceof SemuaProdukModel) {
            semuaProdukModel = (SemuaProdukModel) obj;
        }

        imgProduk = findViewById(R.id.img_produk);
        namaProduk = findViewById(R.id.nama_produk);
        hargaProduk = findViewById(R.id.harga_produk);
        deskripsiProduk = findViewById(R.id.isi_desk);
        Kuantiti = findViewById(R.id.kuantiti);


        addItem = findViewById(R.id.additem);
        removeItem = findViewById(R.id.removeitem);

        addtoCart = findViewById(R.id.addcartbtn);
        buyNow = findViewById(R.id.belibtn);
        backBtnDP = findViewById(R.id.backBtnDP);
        backBtnDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Produk Populer

        if (produkPopulerModel != null){
            Glide.with(getApplicationContext()).load(produkPopulerModel.getImg_url()).into(imgProduk);
            namaProduk.setText(produkPopulerModel.getNama());
            hargaProduk.setText(String.valueOf(produkPopulerModel.getHarga()));
            deskripsiProduk.setText(produkPopulerModel.getDeskripsi());

            totalHarga = produkPopulerModel.getHarga() * totalKuantiti;
        }

        //Semua Produk

        if (semuaProdukModel != null){
            Glide.with(getApplicationContext()).load(semuaProdukModel.getImg_url()).into(imgProduk);
            namaProduk.setText(semuaProdukModel.getNama());
            hargaProduk.setText(String.valueOf(semuaProdukModel.getHarga()));
            deskripsiProduk.setText(semuaProdukModel.getDeskripsi());

            totalHarga = semuaProdukModel.getHarga() * totalKuantiti;
        }

        //Beli Sekarang
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailProduk.this, AlamatPengiriman.class));
            }
        });

        //Tambah ke keranjang
        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String saveCurrentTime, saveCurrentDate;

                Calendar callForDate = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
                saveCurrentDate = currentDate.format(callForDate.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(callForDate.getTime());

                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("namaProduk", namaProduk.getText().toString());
                cartMap.put("hargaProduk", hargaProduk.getText());
                cartMap.put("currentDate", saveCurrentDate);
                cartMap.put("currentTime", saveCurrentTime);
                cartMap.put("totalKuantiti", Kuantiti.getText().toString());
                cartMap.put("totalHarga", totalHarga);

                firestore.collection("Keranjang").document
                        (auth.getCurrentUser().getUid())
                        .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                Toast.makeText(DetailProduk.this, "Berhasil memasukan ke keranjang", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

            }
        });

        //Tambah Kuantiti barang
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalKuantiti < 10){
                    totalKuantiti++;
                    Kuantiti.setText(String.valueOf(totalKuantiti));

                    if (produkPopulerModel != null){
                        totalHarga = produkPopulerModel.getHarga() * totalKuantiti;
                    }
                    if (semuaProdukModel != null){
                        totalHarga = semuaProdukModel.getHarga() * totalKuantiti;
                    }
                }
            }
        });

        //Mengurangi Kuantiti barang
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalKuantiti > 1){
                    totalKuantiti--;
                    Kuantiti.setText(String.valueOf(totalKuantiti));
                }
            }
        });
    }

}