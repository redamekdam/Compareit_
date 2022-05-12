package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Register extends AppCompatActivity {
    EditText inputuser ;
    EditText inputemail ;
    EditText inputpassword ;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();
        inputuser = findViewById(R.id.inputuser);
        inputemail = findViewById(R.id.inputemail);
        inputpassword = findViewById(R.id.inputpassword);
        progressDialog = new ProgressDialog(this);
        button=findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {
        String user= inputuser.getText().toString();
        String email= inputemail.getText().toString();
        String password= inputpassword.getText().toString();
        if(user.isEmpty()){
            inputuser.setError("Enter a username");
        }else if(!email.matches(emailPattern)){
            inputemail.setError("Enter a valid mail");
        }else if(password.isEmpty() || password.length()<6){
            inputpassword.setError("Enter a valid password");
        }else{
            progressDialog.setMessage("Regestration ..");
            progressDialog.setTitle("REGISTRATION");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        SendUserToNextActivity();
                        Toast.makeText(Register.this, "Restration completed", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(Register.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void SendUserToNextActivity() {
        Intent intent = new Intent(Register.this,Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
