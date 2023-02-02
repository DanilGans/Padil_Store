package com.example.padil.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.padil.Activity.LoginPage;
import com.example.padil.OnBoard.Onboard1;
import com.example.padil.R;

public class SplashScreen extends AppCompatActivity {

    //1 detik = 1000
    private int loading=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, Onboard1.class));
                finish();
            }
        },loading);
    }
}