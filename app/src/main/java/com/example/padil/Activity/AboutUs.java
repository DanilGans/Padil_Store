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

public class AboutUs extends AppCompatActivity {

    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        nav = findViewById(R.id.bottomNavigationView);

        Menu menu = nav.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.homeNav:
                        Intent intent1 = new Intent(AboutUs.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.cartNav:
                        Intent intent2 = new Intent(AboutUs.this, Keranjang.class);
                        startActivity(intent2);
                        break;

                    case R.id.userNav:
                        Intent intent3 = new Intent(AboutUs.this, Profile.class);
                        startActivity(intent3);
                        break;

                    case R.id.infoNav:

                        break;
                }

                return false;
            }
        });

    }
}