package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.padil.Fragments.HomeFragment;
import com.example.padil.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    Fragment homeFragment;
    BottomNavigationView nav;
    TextView NamaUser;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String userID;

    CircleImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();

        nav = findViewById(R.id.bottomNavigationView);
        avatar = findViewById(R.id.avatar);
        NamaUser = findViewById(R.id.username);

        DocumentReference docRef = firestore.collection("Users").document(userID);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String ava_url = value.getString("avatar");
                NamaUser.setText("Hi, " + value.getString("Nama Lengkap"));
                Glide.with(MainActivity.this)
                        .load(ava_url)
                        .into(avatar);
            }
        });


        Menu menu = nav.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.homeNav:

                        break;

                    case R.id.cartNav:
                        Intent intent1 = new Intent(MainActivity.this, Keranjang.class);
                        startActivity(intent1);
                        break;

                    case R.id.userNav:
                        Intent intent2 = new Intent(MainActivity.this, Profile.class);
                        startActivity(intent2);
                        break;

                    case R.id.infoNav:
                        Intent intent3 = new Intent(MainActivity.this, AboutUs.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });

        homeFragment = new HomeFragment();
        loadFragment(homeFragment);
    }

    private void loadFragment(Fragment homeFragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homeFragment);
        transaction.commit();
    }
}