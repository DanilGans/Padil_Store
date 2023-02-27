package com.example.padil.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.example.padil.R;

import java.text.NumberFormat;
import java.util.Locale;

public class Pembayaran extends AppCompatActivity {

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    TextView subtotal, ongkir, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        subtotal = findViewById(R.id.subtotal);
        ongkir = findViewById(R.id.ongkir);
        total = findViewById(R.id.total);

        double amount = 0.0;
        amount = getIntent().getDoubleExtra("amount", 0);
        subtotal.setText(formatRupiah.format((double) + amount));

        ongkir.setText(formatRupiah.format((double) 10000));

        total.setText(formatRupiah.format((double) + amount + 10000));



    }
}