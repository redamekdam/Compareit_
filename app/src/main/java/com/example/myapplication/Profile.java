package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;

@SuppressWarnings("ALL")
public class Profile extends AppCompatActivity {
    Uri imageUri;
    BottomNavigationView bottomNavigationView;

    ImageView imageView;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    FirebaseStorage storage;
    StorageReference storageReference;
    Button logout;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_profile);
        progressDialog = new ProgressDialog(this);
        Intent intent=getIntent();
        String email=intent.getStringExtra("email");
        final   TextView  TextUser= (TextView) findViewById(R.id.welcome);
        final   TextView  TextAge= (TextView) findViewById(R.id.age_t);
        final   TextView  TextUser_= (TextView) findViewById(R.id.username_t);
        final   TextView  TextEmail= (TextView) findViewById(R.id.email_t);
        final   TextView  TextPassword= (TextView) findViewById(R.id.password_t);
        imageView=findViewById(R.id.profile_pic);
        logout=findViewById(R.id.LogOut);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoosePicture();
            }
        });
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        user=FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users");
        UserID=user.getUid();
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile=snapshot.getValue(User.class);
                if(userprofile != null){
                    String username=userprofile.username;
                    String email=userprofile.email;
                    String password=userprofile.password;
                    String age=userprofile.age;
                    TextUser_.setText(username);
                    TextUser.setText(username);
                    TextAge.setText(age);
                    TextEmail.setText(email);
                    TextPassword.setText(password);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Somthing wrong happened", Toast.LENGTH_SHORT).show();

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("LOG OUT ...");
                progressDialog.setTitle("LOG OUT");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this,Login.class));

            }
        });




        bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                    case R.id.Favorites:
                        startActivity(new Intent(getApplicationContext(),Favorites.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
    }

    private void ChoosePicture() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri=data.getData();
            imageView.setImageURI(imageUri);
            UploadPic();
        }
    }

    private void UploadPic() {
        ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();
        String randomkey= UUID.randomUUID().toString();
        StorageReference storageReference1 = storageReference.child("image/"+randomkey);
        storageReference1.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Profile.this, "Failed To Upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Upload"+ (int) progressPercent + "%");
            }
        });

    }
}