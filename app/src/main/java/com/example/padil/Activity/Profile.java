package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.padil.Model.ProfileModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    Button logout, editprofile;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    BottomNavigationView nav;

    CircleImageView imageAvatar;
    TextView nama,email, phone;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = findViewById(R.id.logoutBtn);
        editprofile = findViewById(R.id.editProfileBtn);
        nama = findViewById(R.id.nama_profile);
        imageAvatar = findViewById(R.id.circleImageView);
        email = findViewById(R.id.email_profile);
        phone = findViewById(R.id.phone_profile);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();

        DocumentReference docRef = firestore.collection("Users").document(userID);
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String ava_url = value.getString("avatar");
                String isAdmin = value.getString("isAdmin");

                nama.setText(value.getString("Nama Lengkap"));
                email.setText(value.getString("Email Address"));
                phone.setText(value.getString("Nomor Handphone"));
                Glide.with(Profile.this)
                        .load(ava_url)
                        .into(imageAvatar);

                if (isAdmin != null){
                    nav.setVisibility(View.GONE);
                    logout.setVisibility(View.GONE);
                }else {
                    nav.setVisibility(View.VISIBLE);
                }
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, EditProfile.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(Profile.this, "Berhasil keluar", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Profile.this, LoginPage.class));
                finish();
            }
        });

        nav = findViewById(R.id.bottomNavigationView);

        Menu menu = nav.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.homeNav:
                        Intent intent1 = new Intent(Profile.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.cartNav:
                        Intent intent2 = new Intent(Profile.this, Keranjang.class);
                        startActivity(intent2);
                        break;

                    case R.id.userNav:

                        break;

                    case R.id.infoNav:
                        Intent intent3 = new Intent(Profile.this, AboutUs.class);
                        startActivity(intent3);
                        break;

                    case R.id.historyNav:
                        Intent intent4 = new Intent(Profile.this, RiwayatBelanja.class);
                        startActivity(intent4);
                        break;
                }

                return false;
            }
        });

    }
}
//