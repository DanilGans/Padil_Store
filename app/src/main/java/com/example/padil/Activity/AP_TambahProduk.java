package com.example.padil.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

public class AP_TambahProduk extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    FirebaseFirestore firestore;
    EditText namaproduk, deskproduk, hargaproduk, taglineproduk;
    Spinner spinnerKategori;
    private ArrayList<String> kategoriList = new ArrayList<>();
    ImageView imgUpload;
    Button tambahproduk;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ap_tambah_produk);

        firestore = FirebaseFirestore.getInstance();
        namaproduk = findViewById(R.id.namaproduk_tp);
        deskproduk = findViewById(R.id.deskripsi_tp);
        hargaproduk = findViewById(R.id.harga_tp);
        taglineproduk = findViewById(R.id.tagline_tp);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        tambahproduk = findViewById(R.id.tambahprodukBtn);
        imgUpload = findViewById(R.id.uploadFoto);

        kategoriList.add("papan_pvc");
        kategoriList.add("material");
        kategoriList.add("aksesoris");
        kategoriList.add("pemasangan");
        kategoriList.add("ornament");
        kategoriList.add("wallpaper");

        HintSpinner<String> hintSpinner = new HintSpinner<>(
                spinnerKategori,
                // Default layout - You don't need to pass in any layout id, just your hint text and
                // your list data
                new HintAdapter<>(this, R.string.spinner_hint, kategoriList),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        // Here you handle the on item selected event (this skips the hint selected event)
                    }
                });
        hintSpinner.init();

        /*

        arrayAdapter = new ArrayAdapter<String>(AP_TambahProduk.this, android.R.layout.simple_list_item_1, kategoriList);
        spinnerKategori.setAdapter(arrayAdapter);

        */

        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        tambahproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

    }

    private void submit() {

        imgUpload.setDrawingCacheEnabled(true);
        imgUpload.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgUpload.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        //UPLOAD
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("produk-baru").child(new Date().getTime()+".jpeg");
        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getMetadata() != null){
                    if (taskSnapshot.getMetadata().getReference() != null){
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.getResult() != null){
                                    saveData(task.getResult().toString());
                                }
                            }
                        });
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AP_TambahProduk.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveData(String imageUrl){
        String namaProduk = namaproduk.getText().toString();
        String deskProduk = deskproduk.getText().toString();
        int hargaProduk = Integer.parseInt(hargaproduk.getText().toString());
        String taglineProduk = taglineproduk.getText().toString();
        String kategoriProduk = spinnerKategori.getSelectedItem().toString();

        // Check if all the required fields are filled
        if (!namaProduk.isEmpty()) {
            namaproduk.setError("Nama produk tidak boleh kosong");
            return;
        }

        if (!deskProduk.isEmpty()) {
            deskproduk.setError("Deskripsi produk tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(hargaproduk.getText().toString())) {
            hargaproduk.setError("Harga produk tidak boleh kosong");
            return;
        }

        if (!taglineProduk.isEmpty()) {
            taglineproduk.setError("Tagline produk tidak boleh kosong");
            return;
        }

        if (!kategoriProduk.isEmpty()) {
            Toast.makeText(this, "Silakan pilih kategori produk", Toast.LENGTH_SHORT).show();
            return;
        }

        DocumentReference newProductRef = firestore.collection("Produk").document();
        String newProductId = newProductRef.getId();

        // Create a map with the data to be added
        Map<String, Object> newProduct = new HashMap<>();
        newProduct.put("nama", namaProduk);
        newProduct.put("deskripsi", deskProduk);
        newProduct.put("harga", hargaProduk);
        newProduct.put("tagline", taglineProduk);
        newProduct.put("type", kategoriProduk);
        newProduct.put("img_url", imageUrl);

        newProductRef.set(newProduct).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AP_TambahProduk.this, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AP_TambahProduk.this, "Produk gagal ditambahkan: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 10);
            } else if (items[item].equals("Choose from Library")) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 20);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == RESULT_OK && data != null){
            final Uri path = data.getData();
            Thread thread = new Thread(() -> {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(path);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgUpload.post(() -> {
                        imgUpload.setImageBitmap(bitmap);
                    });
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        if (requestCode == 10 && resultCode == RESULT_OK) {
            final Bundle extras = data.getExtras();
            Thread thread = new Thread(() ->{
                Bitmap bitmap = (Bitmap) extras.get("data");
                imgUpload.post(() -> {
                    imgUpload.setImageBitmap(bitmap);
                });
            });
            thread.start();
        }
    }
}