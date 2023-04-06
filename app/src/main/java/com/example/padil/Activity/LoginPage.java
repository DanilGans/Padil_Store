package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPage extends AppCompatActivity {

    EditText formEmail, formPassword;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        formEmail = findViewById(R.id.FormEmailLog);
        formPassword = findViewById(R.id.FormPassword);
        firestore = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            firestore.collection("Users").document(auth.getCurrentUser().getUid())
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.getString("isAdmin") != null){
                                startActivity(new Intent(LoginPage.this, AdminPanel.class));
                                finish();
                            }
                            else {
                                startActivity(new Intent(LoginPage.this, MainActivity.class));
                            }
                        }
                    });
        }

    }

    public void masukLog(View view) {
        String userEmail = formEmail.getText().toString();
        String userPass = formPassword.getText().toString();

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Silahkan masukan Alamat Email!", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(userPass)){
            Toast.makeText(this, "Silahkan masukan Password!", Toast.LENGTH_SHORT).show();
        }

        auth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginPage.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginPage.this, "Gagal, cek ulang password Anda!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void checkUserAccessLevel() {
        DocumentReference docRef = firestore.collection("Users").document(auth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("Firebase", "OnSuccess: " +documentSnapshot.getData());

                if (documentSnapshot.getString("isAdmin") != null){
                    startActivity(new Intent(getApplicationContext(), AdminPanel.class));
                    finish();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    public void daftarLog(View view) {
        startActivity(new Intent(LoginPage.this, RegistPage.class));
    }

    public void backbtnLog(View view) {
        startActivity(new Intent(LoginPage.this, Onboard2.class));
    }
}