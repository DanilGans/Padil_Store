package com.example.padil.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.padil.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminPanel extends AppCompatActivity {

    private FirebaseAuth auth;
    ImageButton produk, pesanan, akun, keluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        auth = FirebaseAuth.getInstance();
        produk = findViewById(R.id.produkBtn);
        pesanan = findViewById(R.id.pesananBtn);
        akun = findViewById(R.id.akunBtn);
        keluar = findViewById(R.id.keluarBtn);

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(AdminPanel.this, "Logout Berhasil!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminPanel.this, LoginPage.class));
                finish();
            }
        });

        pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this, AP_Pesanan.class));
            }
        });

        produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this, AP_Produk.class));
            }
        });

        akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this, Profile.class));
            }
        });

    }
}