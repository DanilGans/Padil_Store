package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.Locale;

public class KonfirmasiPembayaran extends AppCompatActivity {

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    Button chatpenjual, belanjalgi;
    TextView totalharga, nomorpesanan;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pembayaran);

        chatpenjual = findViewById(R.id.buttonKP);
        belanjalgi = findViewById(R.id.belanjalagiBtn);
        totalharga = findViewById(R.id.totalKP);
        nomorpesanan = findViewById(R.id.nomorpesanan);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();

        firestore.collection("Pesanan Masuk").document(userID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        String totalHargaUser = value.getString("totalharga");
                        totalharga.setText(totalHargaUser);

                        String NomorPesanan = value.getString("order_id");
                        nomorpesanan.setText("Nomor Pesanan : "+NomorPesanan);
                    }
                });

        //double total = 0.0;
        //total = getIntent().getIntExtra("totalKP", 176000);
        //totalharga.setText(formatRupiah.format((double) + total));

        chatpenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://wa.me/6282360130466/";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        belanjalgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KonfirmasiPembayaran.this, MainActivity.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(KonfirmasiPembayaran.this, MainActivity.class));
    }
}
