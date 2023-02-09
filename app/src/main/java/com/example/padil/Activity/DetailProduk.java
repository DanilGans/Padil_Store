package com.example.padil.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.padil.Model.ProdukPopulerModel;
import com.example.padil.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailProduk extends AppCompatActivity {

    ImageView imgProduk;
    TextView namaProduk, hargaProduk, deskripsiProduk;
    ImageView addItem, removeItem, addtoCart, buyNow;

    //Produk Populer

    ProdukPopulerModel produkPopulerModel = null;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        firestore = FirebaseFirestore.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailproduk");

        if (obj instanceof ProdukPopulerModel){
            produkPopulerModel = (ProdukPopulerModel) obj;
        }

        imgProduk = findViewById(R.id.img_produk);
        namaProduk = findViewById(R.id.nama_produk);
        hargaProduk = findViewById(R.id.harga_produk);
        deskripsiProduk = findViewById(R.id.isi_desk);

        addItem = findViewById(R.id.additem);
        removeItem = findViewById(R.id.removeitem);

        addtoCart = findViewById(R.id.addcartbtn);
        buyNow = findViewById(R.id.belibtn);

        //Produk Populer

        if (produkPopulerModel != null){
            Glide.with(getApplicationContext()).load(produkPopulerModel.getImg_url()).into(imgProduk);
            namaProduk.setText(produkPopulerModel.getNama());
            hargaProduk.setText(String.valueOf(produkPopulerModel.getHarga()));
            deskripsiProduk.setText(produkPopulerModel.getDeskripsi());
        }

    }
}