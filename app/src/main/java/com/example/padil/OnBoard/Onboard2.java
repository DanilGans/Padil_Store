package com.example.padil.OnBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.padil.Activity.LoginPage;
import com.example.padil.Activity.RegistPage;
import com.example.padil.R;

public class Onboard2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard2);
    }

    public void masukOB(View view) {
        startActivity(new Intent(Onboard2.this, LoginPage.class));
    }


    public void daftarOB(View view) {
        startActivity(new Intent(Onboard2.this, RegistPage.class));
    }

    @Override
    public void onBackPressed() {
    }
}