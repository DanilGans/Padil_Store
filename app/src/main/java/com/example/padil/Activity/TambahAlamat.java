package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TambahAlamat extends AppCompatActivity {

    EditText nama, jalan, kota, kodepos, nomorHP;
    Button addAddressBtn;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_alamat);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        nama = findViewById(R.id.ad_name);
        jalan = findViewById(R.id.ad_address);
        kota = findViewById(R.id.ad_city);
        kodepos = findViewById(R.id.ad_code);
        nomorHP = findViewById(R.id.ad_phone);
        addAddressBtn = findViewById(R.id.ad_address_btn);

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = nama.getText().toString();
                String userJalan = ", " + jalan.getText().toString();
                String userKota = ", " + kota.getText().toString();
                String userKodepos = ", " + kodepos.getText().toString();
                String userNomorhp = ", " + nomorHP.getText().toString();

                String final_address = "";

                if (!userName.isEmpty()){
                    final_address+=userName;
                }
                if (!userJalan.isEmpty()){
                    final_address+=userJalan;
                }
                if (!userKota.isEmpty()){
                    final_address+=userKota;
                }
                if (!userKodepos.isEmpty()){
                    final_address+=userKodepos;
                }
                if (!userNomorhp.isEmpty()){
                    final_address+=userNomorhp;
                }
                if (!userName.isEmpty() && !userJalan.isEmpty() && !userKota.isEmpty() && !userKodepos.isEmpty() && !userNomorhp.isEmpty()){

                    Map<String, String> map = new HashMap<>();
                    map.put("userAlamat", final_address);

                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Alamat").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(TambahAlamat.this, "Alamat telah ditambahkan!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(TambahAlamat.this, AlamatPengiriman.class));
                                        finish();
                                    }
                                }
                            });
                }

            }
        });

    }
}