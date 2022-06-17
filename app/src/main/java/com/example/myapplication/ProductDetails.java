package com.example.myapplication;

import androidx.annotation.NonNull;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ProductDetails extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button shop,fav;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Objects.requireNonNull(getSupportActionBar()).hide();

        bottomNavigationView=findViewById(R.id.nav_bar);
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
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Favorites:
                        startActivity(new Intent(getApplicationContext(),Favorites.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        TextView title=findViewById(R.id.title);
        TextView rate=findViewById(R.id.rating);
        TextView store=findViewById(R.id.store);
        TextView link=findViewById(R.id.link);
        TextView price=findViewById(R.id.price);
        ImageView img=findViewById(R.id.image);
        Bundle bundle = getIntent().getExtras();
        String mTitle = bundle.getString("title");
        String mRate = bundle.getString("rating");
        String mStore = bundle.getString("store");
        String mLink = bundle.getString("link");
        String mPrice = bundle.getString("price");
        String mImage=bundle.getString("image");
        Glide.with(this).load(mImage).into(img);
        title.setText(mTitle);
        rate.setText(mRate);
        store.setText(mStore);
        link.setText(mLink);
        price.setText(mPrice);
        shop=findViewById(R.id.Shop);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(mLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        fav=findViewById(R.id.Fav);
        String id= mDatabase.push().getKey();
        ProductModelClass fa = new ProductModelClass(mTitle,mPrice,mStore,mImage,mRate,mLink);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("Favorite").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(id).setValue(fa).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProductDetails.this, "PRODUCT ADDED TO FAVORITE", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ProductDetails.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProductDetails.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });

    }

}