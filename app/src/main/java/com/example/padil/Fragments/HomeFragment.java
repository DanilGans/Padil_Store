package com.example.padil.Fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.padil.Activity.SemuaProduk;
import com.example.padil.Adapter.KategoriAdapter;
import com.example.padil.Adapter.ProdukPopulerAdapter;
import com.example.padil.Model.KategoriModel;
import com.example.padil.Model.ProdukPopulerModel;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //recyclerview
    RecyclerView katRecyclerview, populerProdukRV;
    KategoriAdapter kategoriAdapter;
    List<KategoriModel> kategoriModelList;
    TextView lihat_spBtn;

    //Populer Produk
    ProdukPopulerAdapter produkPopulerAdapter;
    List<ProdukPopulerModel> produkPopulerModelList;

    //Firestore Connection
    FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        katRecyclerview = root.findViewById(R.id.rec_kategori);
        populerProdukRV = root.findViewById(R.id.produk_populer);
        lihat_spBtn = root.findViewById(R.id.lihat_spBtn);

        lihat_spBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SemuaProduk.class);
                startActivity(intent);
            }
        });

        db = FirebaseFirestore.getInstance();

        //Kategori
        katRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL, false));
        kategoriModelList = new ArrayList<>();
        kategoriAdapter = new KategoriAdapter(getContext(), kategoriModelList);
        katRecyclerview.setAdapter(kategoriAdapter);

        //Read Data from Firestore DB
        db.collection("Kategori")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                KategoriModel kategoriModel = document.toObject(KategoriModel.class);
                                kategoriModelList.add(kategoriModel);
                                kategoriAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        //Populer Produk
        populerProdukRV.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL, false));
        produkPopulerModelList = new ArrayList<>();
        produkPopulerAdapter = new ProdukPopulerAdapter(getContext(), produkPopulerModelList);
        populerProdukRV.setAdapter(produkPopulerAdapter);

        //Read Data from Firestore DB
        db.collection("Produk_Populer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                ProdukPopulerModel produkPopulerModel = document.toObject(ProdukPopulerModel.class);
                                produkPopulerModelList.add(produkPopulerModel);
                                produkPopulerAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return root;

    }
}