package com.example.padil.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.padil.OnBoard.Onboard2;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class RegistPage extends AppCompatActivity {

    EditText formNama, formNope, formEmail, formPassword;

    private FirebaseAuth auth;
    FirebaseFirestore firestore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_page);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        if (auth.getCurrentUser() != null){

            startActivity(new Intent(RegistPage.this, MainActivity.class));
            finish();
        }

        formNama = findViewById(R.id.formNamaLengkap);
        formNope = findViewById(R.id.formNope);
        formEmail = findViewById(R.id.formEmailReg);
        formPassword = findViewById(R.id.formPassword);
    }

    public void daftar(View view) {
        String userNama = formNama.getText().toString();
        String userNope = formNope.getText().toString();
        String userEmail = formEmail.getText().toString();
        String userPass = formPassword.getText().toString();

        if (TextUtils.isEmpty(userNama)){

            Toast.makeText(this, "Masukan Nama Lengkap!", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(userNope)){

            Toast.makeText(this, "Masukan Nomor Handphone!", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(userEmail)){

            Toast.makeText(this, "Masukan Alamat Email!", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(userPass)){

            Toast.makeText(this, "Masukan Password!", Toast.LENGTH_SHORT).show();
        }

        if (userPass.length() < 6){

            Toast.makeText(this, "Password terlalu pendek, minimal 6 karakter!", Toast.LENGTH_SHORT).show();
        }

        auth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(RegistPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(RegistPage.this, "Akun Anda Berhasil Didaftarkan!", Toast.LENGTH_SHORT).show();
                            userID = auth.getCurrentUser().getUid();
                            DocumentReference documentReference = firestore.collection("Users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Nama Lengkap", userNama);
                            user.put("Nomor Handphone", userNope);
                            user.put("Email Address", userEmail);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "User berhasil didaftarkan untuk "+userID);
                                }
                            });

                            startActivity(new Intent(RegistPage.this, MainActivity.class));

                        }else{
                            Toast.makeText(RegistPage.this, "Akun Anda Gagal Didaftarkan!"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void masuk(View view) {
        startActivity(new Intent(RegistPage.this, LoginPage.class));
    }

    public void backbtnReg(View view) {
        startActivity(new Intent(RegistPage.this, Onboard2.class));
    }

    @Override
    public void onBackPressed() {
    }
}