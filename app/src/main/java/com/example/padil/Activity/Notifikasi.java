package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.padil.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Notifikasi extends AppCompatActivity {

    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        nav = findViewById(R.id.bottomNavigationView);

        Menu menu = nav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.homeNav:
                        Intent intent1 = new Intent(Notifikasi.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.cartNav:
                        Intent intent2 = new Intent(Notifikasi.this, Keranjang.class);
                        startActivity(intent2);
                        break;

                    case R.id.notifNav:

                        break;
                }

                return false;
            }
        });
    }
}