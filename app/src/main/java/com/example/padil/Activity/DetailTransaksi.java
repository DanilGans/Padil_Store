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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.padil.Adapter.DetailTransaksiAdapter;
import com.example.padil.Adapter.KeranjangAdapter;
import com.example.padil.Model.DetailTransaksiModel;
import com.example.padil.Model.KeranjangModel;
import com.example.padil.Model.SemuaProdukModel;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class DetailTransaksi extends AppCompatActivity {

    //Get mata uang rupiah
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    List<DetailTransaksiModel> detailTransaksiModelList;
    DetailTransaksiAdapter detailTransaksiAdapter;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    KeranjangModel keranjangModel;
    TextView alamat, subtotal, ongkir, total;
    Button bayar, addAlamat;
    ImageView backBtn;

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
        //ongkir = findViewById(R.id.ongkirDT);
        total = findViewById(R.id.totalDT);
        bayar = findViewById(R.id.bayarbtnDT);
        backBtn = findViewById(R.id.backBtnDT);
        addAlamat = findViewById(R.id.addAlamat);

        recyclerView = findViewById(R.id.rv_detTransaksi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailTransaksiModelList = new ArrayList<>();
        detailTransaksiAdapter = new DetailTransaksiAdapter(this, detailTransaksiModelList);
        recyclerView.setAdapter(detailTransaksiAdapter);

        getDataKeranjang();
        getDataAlamat();

        addAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailTransaksi.this, AlamatPengiriman.class));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firestore.collection("Keranjang")
                        .document(userId)
                        .collection("User")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    ArrayList<String> namaProdukList = new ArrayList<String>();
                                    for (QueryDocumentSnapshot doc : task.getResult()){

                                        String namaProduk = doc.getString("namaProduk");
                                        String kuantiti = doc.getString("totalKuantiti");
                                        String variasi = doc.getString("variasi");
                                        String cicilan = doc.getString("cicilan");

                                        // Check if the 'variasi' & 'cicilan' field is not null
                                        if (variasi != null) {
                                            namaProduk = namaProduk + " || " + variasi;
                                        } else if (cicilan != null){
                                            namaProduk = namaProduk + " || Cicilan : " + cicilan;
                                        }
                                        namaProduk = namaProduk + " || Qty: " + kuantiti;

                                        namaProdukList.add(namaProduk);
                                    }

                                    final HashMap<String, Object> bayarMap = new HashMap<>();

                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    String currentDate = dateFormat.format(calendar.getTime());

                                    bayarMap.put("order_id", UUID.randomUUID().toString().substring(0,5));
                                    bayarMap.put("list_produk", namaProdukList);
                                    bayarMap.put("totalharga", total.getText());
                                    bayarMap.put("alamat", alamat.getText().toString());
                                    bayarMap.put("tanggal", currentDate);

                                    firestore.collection("Pesanan Masuk").document(userId)
                                            .set(bayarMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
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
                            }
                        });
            }
        });

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("MySubTotal"));

    }

    private void getDataAlamat() {
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

    private void getDataKeranjang() {
        //GET DATA "KERANJANG"
        firestore.collection("Keranjang").document(userId)
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