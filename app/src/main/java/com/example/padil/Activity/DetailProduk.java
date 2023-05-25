package com.example.padil.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Flow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.padil.Model.ProdukPopulerModel;
import com.example.padil.Model.SemuaProdukModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

public class DetailProduk extends AppCompatActivity {

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    ImageView imgProduk, imgProduk2, imgProduk3, imgProduk4, imgProduk5,
            imgProduk6, imgProduk7, imgProduk8, imgProduk9, imgProduk10;
    TextView namaProduk, hargaProduk, deskripsiProduk, Kuantiti, pilihVariant;
    ImageView addItem, removeItem, addtoCart, buyNow, backBtnDP;
    ImageButton chatBtn;
    Spinner spinnerVariant;
    String type = "";
    Flow variantLayout;
    int totalKuantiti = 1;
    int totalHarga = 0;

    ImageSlider imageSlider;

    //Produk Populer
    ProdukPopulerModel produkPopulerModel = null;
    //Semua Produk
    SemuaProdukModel semuaProdukModel = null;
    FirebaseAuth auth;

    private FirebaseFirestore firestore;
    private ArrayList<String> arrayVariasi;
    private ArrayAdapter<String> adapterVariasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailproduk");

        if (obj instanceof ProdukPopulerModel){
            produkPopulerModel = (ProdukPopulerModel) obj;
        }else if (obj instanceof SemuaProdukModel) {
            semuaProdukModel = (SemuaProdukModel) obj;
            type = semuaProdukModel.getType();
        }

        imgProduk = findViewById(R.id.img_produk);
        imgProduk2 = findViewById(R.id.img_produk2);
        imgProduk3 = findViewById(R.id.img_produk3);
        imgProduk4 = findViewById(R.id.img_produk4);
        imgProduk5 = findViewById(R.id.img_produk5);
        imgProduk6 = findViewById(R.id.img_produk6);
        imgProduk7 = findViewById(R.id.img_produk7);
        imgProduk8 = findViewById(R.id.img_produk8);
        imgProduk9 = findViewById(R.id.img_produk9);
        imgProduk10 = findViewById(R.id.img_produk10);

//        imageSlider = findViewById(R.id.image_slider);

        namaProduk = findViewById(R.id.nama_produk);
        hargaProduk = findViewById(R.id.harga_produk);
        deskripsiProduk = findViewById(R.id.isi_desk);
        Kuantiti = findViewById(R.id.kuantiti);

        addItem = findViewById(R.id.additem);
        removeItem = findViewById(R.id.removeitem);

        addtoCart = findViewById(R.id.addcartbtn);
        backBtnDP = findViewById(R.id.backBtnDP);
        chatBtn = findViewById(R.id.chatBtn);

        pilihVariant = findViewById(R.id.pilihvariant);
        spinnerVariant = findViewById(R.id.spinnerVariant);
        variantLayout = findViewById(R.id.variant);

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://wa.me/6282360130466/";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        backBtnDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Produk Populer

        if (produkPopulerModel != null){
            Glide.with(getApplicationContext()).load(produkPopulerModel.getImg_url()).into(imgProduk);
            namaProduk.setText(produkPopulerModel.getNama());
            hargaProduk.setText(formatRupiah.format((double)+produkPopulerModel.getHarga()));
            deskripsiProduk.setText(produkPopulerModel.getDeskripsi());

            totalHarga = produkPopulerModel.getHarga() * totalKuantiti;
        }

        //Semua Produk

        if (semuaProdukModel != null){

            Glide.with(getApplicationContext()).load(semuaProdukModel.getImg_url()).into(imgProduk);
            Glide.with(getApplicationContext()).load(semuaProdukModel.getImg_url2()).into(imgProduk2);
            Glide.with(getApplicationContext()).load(semuaProdukModel.getImg_url3()).into(imgProduk3);
            Glide.with(getApplicationContext()).load(semuaProdukModel.getImg_url4()).into(imgProduk4);
            Glide.with(getApplicationContext()).load(semuaProdukModel.getImg_url5()).into(imgProduk5);
            Glide.with(getApplicationContext()).load(semuaProdukModel.getImg_url6()).into(imgProduk6);
            Glide.with(getApplicationContext()).load(semuaProdukModel.getImg_url7()).into(imgProduk7);
            Glide.with(getApplicationContext()).load(semuaProdukModel.getImg_url8()).into(imgProduk8);
            Glide.with(getApplicationContext()).load(semuaProdukModel.getImg_url9()).into(imgProduk9);
            Glide.with(getApplicationContext()).load(semuaProdukModel.getImg_url10()).into(imgProduk10);

            namaProduk.setText(semuaProdukModel.getNama());
            hargaProduk.setText(formatRupiah.format((double)+semuaProdukModel.getHarga()));

            String deskripsi = semuaProdukModel.getDeskripsi().replace("\\n", "\n");
            deskripsiProduk.setText(deskripsi);

            totalHarga = semuaProdukModel.getHarga() * totalKuantiti;

        }

        arrayVariasi = new ArrayList<>();
        adapterVariasi = new ArrayAdapter<>(DetailProduk.this, android.R.layout.simple_spinner_item, arrayVariasi);
        adapterVariasi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVariant.setAdapter(adapterVariasi);

        getListVariasi();

        //PilihVariantVisibilty
        if (type.equals("papan_pvc") || type.equals("ornament") || type.equals("wallpaper")) {
            variantLayout.setVisibility(View.VISIBLE);
        }else if (type.equals("pemasangan")){
            pilihVariant.setText("Pilih Cicilan");
            variantLayout.setVisibility(View.VISIBLE);
        }else {
            variantLayout.setVisibility(View.GONE);
        }

        //Tambah ke keranjang
        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if (semuaProdukModel != null){
                    String imageUrl = semuaProdukModel.getImg_url();
                    String productName = semuaProdukModel.getNama();

                    final HashMap<String, Object> cartMap = new HashMap<>();

                    cartMap.put("namaProduk", productName);
                    cartMap.put("hargaProduk", hargaProduk.getText());
                    cartMap.put("img_url", imageUrl);
                    cartMap.put("totalKuantiti", Kuantiti.getText().toString());
                    cartMap.put("totalHarga", totalHarga);

                    if (spinnerVariant.getSelectedItem() == null){
                        cartMap.put("NotVariasi", toString());
                    }else if (type.equalsIgnoreCase("papan_pvc")
                            || type.equalsIgnoreCase("ornament")
                            || type.equalsIgnoreCase("wallpaper")){
                        String variasi = spinnerVariant.getSelectedItem().toString();
                        cartMap.put("variasi", variasi);
                    }else if (type.equalsIgnoreCase("pemasangan")){
                        String cicilan = spinnerVariant.getSelectedItem().toString();
                        cartMap.put("cicilan", cicilan);
                    }

                    firestore.collection("Keranjang").document(auth.getCurrentUser().getUid())
                            .collection("User").add(cartMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(DetailProduk.this, "Berhasil memasukan ke keranjang", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Gagal menambahkan produk tersebut ", e);
                                }
                            });
                }

                if (produkPopulerModel != null){
                    String imageUrl = produkPopulerModel.getImg_url();
                    String productName = produkPopulerModel.getNama();

                    final HashMap<String, Object> cartMap = new HashMap<>();

                    cartMap.put("namaProduk", productName);
                    cartMap.put("hargaProduk", hargaProduk.getText());
                    cartMap.put("img_url", imageUrl);
                    cartMap.put("totalKuantiti", Kuantiti.getText().toString());
                    cartMap.put("totalHarga", totalHarga);

                    firestore.collection("Keranjang").document(auth.getCurrentUser().getUid())
                            .collection("User").add(cartMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(DetailProduk.this, "Berhasil memasukan ke keranjang", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Gagal menambahkan produk tersebut ", e);
                                }
                            });


                }

            }
        });

        //Tambah Kuantiti barang
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalKuantiti < 50){
                    totalKuantiti++;
                    Kuantiti.setText(String.valueOf(totalKuantiti));

                    if (produkPopulerModel != null){
                        totalHarga = produkPopulerModel.getHarga() * totalKuantiti;
                    }
                    if (semuaProdukModel != null){
                        totalHarga = semuaProdukModel.getHarga() * totalKuantiti;
                    }
                }
            }
        });

        //Mengurangi Kuantiti barang
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalKuantiti > 1){
                    totalKuantiti--;
                    Kuantiti.setText(String.valueOf(totalKuantiti));
                }
            }
        });
    }

    private void getListVariasi() {

        if (type != null && type.equalsIgnoreCase("papan_pvc")){
            firestore.collection("VariasiPapanPVC")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.size()>0){
                                for (DocumentSnapshot doc : queryDocumentSnapshots){
                                    arrayVariasi.add(doc.getString("variasi1"));
                                    arrayVariasi.add(doc.getString("variasi2"));
                                    arrayVariasi.add(doc.getString("variasi3"));
                                    arrayVariasi.add(doc.getString("variasi4"));
                                    arrayVariasi.add(doc.getString("variasi5"));
                                    arrayVariasi.add(doc.getString("variasi6"));
                                    arrayVariasi.add(doc.getString("variasi7"));
                                    arrayVariasi.add(doc.getString("variasi8"));
                                    arrayVariasi.add(doc.getString("variasi9"));
                                    arrayVariasi.add(doc.getString("variasi10"));
                                }
                                adapterVariasi.notifyDataSetChanged();
                            }else {
                                Toast.makeText(DetailProduk.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


        if (type != null && type.equalsIgnoreCase("ornament")){
            firestore.collection("VariasiOrnament")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.size()>0){
                                for (DocumentSnapshot doc : queryDocumentSnapshots){
                                    arrayVariasi.add(doc.getString("variasi1"));
                                    arrayVariasi.add(doc.getString("variasi2"));
                                    arrayVariasi.add(doc.getString("variasi3"));
                                    arrayVariasi.add(doc.getString("variasi4"));
                                    arrayVariasi.add(doc.getString("variasi5"));
                                    arrayVariasi.add(doc.getString("variasi6"));
                                    arrayVariasi.add(doc.getString("variasi7"));
                                    arrayVariasi.add(doc.getString("variasi8"));
                                }
                                adapterVariasi.notifyDataSetChanged();
                            }else {
                                Toast.makeText(DetailProduk.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("wallpaper")){
            firestore.collection("VariasiOrnament")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.size()>0){
                                for (DocumentSnapshot doc : queryDocumentSnapshots){
                                    arrayVariasi.add(doc.getString("variasi1"));
                                    arrayVariasi.add(doc.getString("variasi2"));
                                    arrayVariasi.add(doc.getString("variasi3"));
                                    arrayVariasi.add(doc.getString("variasi4"));
                                    arrayVariasi.add(doc.getString("variasi5"));
                                    arrayVariasi.add(doc.getString("variasi6"));
                                    arrayVariasi.add(doc.getString("variasi7"));
                                    arrayVariasi.add(doc.getString("variasi8"));
                                    arrayVariasi.add(doc.getString("variasi9"));
                                }
                                adapterVariasi.notifyDataSetChanged();
                            }else {
                                Toast.makeText(DetailProduk.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        if (type != null && type.equalsIgnoreCase("pemasangan")){
            firestore.collection("PilihanCicilan")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.size()>0){
                                for (DocumentSnapshot doc : queryDocumentSnapshots){
                                    arrayVariasi.add(doc.getString("cicilan1"));
                                    arrayVariasi.add(doc.getString("cicilan2"));
                                    arrayVariasi.add(doc.getString("cicilan3"));
                                }
                                adapterVariasi.notifyDataSetChanged();
                            }else {
                                Toast.makeText(DetailProduk.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
    
}