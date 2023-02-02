package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.padil.OnBoard.Onboard2;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    EditText formEmail, formPassword;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){

            startActivity(new Intent(LoginPage.this, MainActivity.class));
            finish();
        }

        formEmail = findViewById(R.id.FormEmailLog);
        formPassword = findViewById(R.id.FormPassword);


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
                .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        
                        if (task.isSuccessful()){
                            Toast.makeText(LoginPage.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginPage.this, MainActivity.class));
                        }else{
                            Toast.makeText(LoginPage.this, "Gagal: "+task.getException(), Toast.LENGTH_SHORT).show();
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