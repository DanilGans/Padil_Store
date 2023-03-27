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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.padil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    Button logout;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    BottomNavigationView nav;
    CircleImageView avatar;
    TextView nama,email, phone;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = findViewById(R.id.logoutBtn);
        avatar = findViewById(R.id.circleImageView);
        nama = findViewById(R.id.nama_profile);
        email = findViewById(R.id.email_profile);
        phone = findViewById(R.id.phone_profile);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        DocumentReference docRef = firestore.collection("Users").document(userID);
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                nama.setText(value.getString("Nama Lengkap"));
                email.setText(value.getString("Email Address"));
                phone.setText(value.getString("Nomor Handphone"));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(Profile.this, "Berhasil keluar", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Profile.this, LoginPage.class));
                finish();
            }
        });

        nav = findViewById(R.id.bottomNavigationView);

        Menu menu = nav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.homeNav:
                        Intent intent1 = new Intent(Profile.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.cartNav:
                        Intent intent2 = new Intent(Profile.this, Keranjang.class);
                        startActivity(intent2);
                        break;

                    case R.id.userNav:

                        break;

                    case R.id.infoNav:
                        Intent intent3 = new Intent(Profile.this, AboutUs.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });

    }

    private void uploadImage(){
        avatar.setDrawingCacheEnabled(true);
        avatar.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        //UPLOAD
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("profile").child(new Date().getTime()+".jpeg");
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
                Toast.makeText(Profile.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveData(String avatar){
        Map<String, Object> user = new HashMap<>();
        user.put("avatar", avatar);

        firestore.collection("Users").document(userID)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Profile.this, "Berhasil Upload", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    private void selectImage(){
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")){
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
                    avatar.post(() -> {
                        avatar.setImageBitmap(bitmap);
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
                avatar.post(() -> {
                    avatar.setImageBitmap(bitmap);
                });
            });
            thread.start();
        }
    }
}
//