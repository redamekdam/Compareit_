package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.locks.ReadWriteLock;

@SuppressWarnings("ALL")
public class Profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageView imageView;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button logout;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent=getIntent();
        String email=intent.getStringExtra("email");
        final   TextView  TextUser= (TextView) findViewById(R.id.welcome);
        final   TextView  TextAge= (TextView) findViewById(R.id.age_t);
        final   TextView  TextUser_= (TextView) findViewById(R.id.username_t);
        final   TextView  TextEmail= (TextView) findViewById(R.id.email_t);
        final   TextView  TextPassword= (TextView) findViewById(R.id.password_t);
        logout=findViewById(R.id.LogOut);

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
                    case R.id.favorite:
                        startActivity(new Intent(getApplicationContext(),Favori.class));
                        overridePendingTransition(0,0);

                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:


                        return true;

                }
                return false;
            }
        });
    }


}