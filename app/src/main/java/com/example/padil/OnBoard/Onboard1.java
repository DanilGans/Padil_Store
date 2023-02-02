package com.example.padil.OnBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.padil.R;

public class Onboard1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard1);
    }

    public void lanjut(View view) {
        startActivity(new Intent(Onboard1.this, Onboard2.class));
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}