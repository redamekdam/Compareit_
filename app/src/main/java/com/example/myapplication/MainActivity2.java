package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main2);
        Button Login = findViewById(R.id.Login);
        Login.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(),Login.class);
            startActivity(i);
        });
        Button Register = findViewById(R.id.Register);
        Register.setOnClickListener(view -> {
            Intent i1 = new Intent(getApplicationContext(),Register.class);
            startActivity(i1);
        });
    }
}