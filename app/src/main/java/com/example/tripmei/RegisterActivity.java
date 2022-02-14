package com.example.tripmei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {


    EditText edttxtRegisterName, edttxtRegisterEmail, edttxtRegisterPass;
    String userName, userEmail, userPass;
    Button btnRegister;
    Intent homeIntent;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponent();
        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatAccount(edttxtRegisterEmail.getText().toString(), edttxtRegisterPass.getText().toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }

    private void creatAccount(String email, String password) {
        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this , MainActivity.class));

                            } else {
                                Toast.makeText(RegisterActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        } catch (IllegalArgumentException e) {
            Toast.makeText(RegisterActivity.this, "Enter Email and Password", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUI(FirebaseUser user) {

    }

    private void reload() {
    }

    private void initComponent() {
        edttxtRegisterName = findViewById(R.id.edittextRegisterName);
        edttxtRegisterEmail = findViewById(R.id.edittextRegisterEmail);
        edttxtRegisterPass = findViewById(R.id.edittextRegisterPassword);
        //userEmail = edttxtRegisterEmail.getText().toString();
        //userName = edttxtRegisterName.getText().toString();
        //userPass = edttxtRegisterPass.getText().toString();
        btnRegister = findViewById(R.id.btnRegister);


    }
}