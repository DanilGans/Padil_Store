package com.example.padil.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.padil.R;

public class AP_Produk extends AppCompatActivity {

    CardView tambahproduk, daftarproduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ap_produk);

        tambahproduk = findViewById(R.id.tambahprodukCV);
        daftarproduk = findViewById(R.id.daftarprodukCV);

        daftarproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AP_Produk.this, AP_DaftarProduk.class));
            }
        });

        tambahproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AP_Produk.this, AP_TambahProduk.class));
            }
        });

    }
}