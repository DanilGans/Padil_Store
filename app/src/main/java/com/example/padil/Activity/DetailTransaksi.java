package com.example.padil.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.padil.Adapter.DetailTransaksiAdapter;
import com.example.padil.Adapter.KeranjangAdapter;
import com.example.padil.Model.DetailTransaksiModel;
import com.example.padil.Model.KeranjangModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DetailTransaksi extends AppCompatActivity {

    //Get mata uang rupiah
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    List<DetailTransaksiModel> detailTransaksiModelList;
    DetailTransaksiModel detailTransaksiModel = null;
    DetailTransaksiAdapter detailTransaksiAdapter;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;


    TextView alamat, subtotal, ongkir, total;
    Button bayar;

    String userId;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();

        alamat = findViewById(R.id.alamat_user);
        subtotal = findViewById(R.id.subtotalDT);
        ongkir = findViewById(R.id.ongkirDT);
        total = findViewById(R.id.totalDT);
        bayar = findViewById(R.id.bayarbtnDT);

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final HashMap<String, Object> bayarMap = new HashMap<>();

                bayarMap.put("TotalHarga", total.getText());
                bayarMap.put("Alamat", alamat.getText().toString());

                firestore.collection("Pesanan Masuk").document(userId)
                        .collection("User").add(bayarMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(DetailTransaksi.this, "Berhasil melakukan pesanan", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DetailTransaksi.this, KonfirmasiPembayaran.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Gagal melakukan pesanan tersebut ", e);
                            }
                        });
            }
        });

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("MySubTotal"));

        recyclerView = findViewById(R.id.rv_detTransaksi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailTransaksiModelList = new ArrayList<>();
        detailTransaksiAdapter = new DetailTransaksiAdapter(this, detailTransaksiModelList);
        recyclerView.setAdapter(detailTransaksiAdapter);

        //GET DATA "KERANJANG"
        firestore.collection("Keranjang").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot doc : task.getResult().getDocuments()){

                                DetailTransaksiModel detailTransaksiModel = doc.toObject(DetailTransaksiModel.class);
                                detailTransaksiModelList.add(detailTransaksiModel);
                                detailTransaksiAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });


        //GET DATA "ALAMAT" FROM FIRESTORE SUBCOLLECTION
        DocumentReference documentReference = firestore.collection("CurrentUser").document(userId);
        CollectionReference subCollectionRef = documentReference.collection("Alamat");

        subCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        String alamatuser = doc.getString("userAlamat");
                        alamat.setText(alamatuser);
                    }
                }else {
                    Log.d(TAG, "Error : ", task.getException());
                }
            }
        });

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("subTotalDT", 0);
            subtotal.setText(formatRupiah.format((double) + totalBill));
            total.setText(formatRupiah.format((double) + totalBill));
        }
    };
}