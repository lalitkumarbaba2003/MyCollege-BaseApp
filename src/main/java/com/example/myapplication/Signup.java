package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_signup);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        buttonReg.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            String email, password;
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());
            if(TextUtils.isEmpty(email)){
                Toast.makeText(Signup.this,"Enter email", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(Signup.this,"Enter password", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(Signup.this, "Account created Successfully.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}