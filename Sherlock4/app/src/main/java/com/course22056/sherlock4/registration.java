package com.course22056.sherlock4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registration extends AppCompatActivity {
    TextView alreadyAccText;

    EditText registerFName, registerLName, registerEmail, registerPassword, registerConfPass;
    Button registerCreateBtn;

     FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.registration);

        registerFName = findViewById(R.id.FirstName);
        registerLName = findViewById(R.id.LastName);
        registerEmail = findViewById(R.id.CreateEmail);
        registerPassword = findViewById(R.id.CreatePassword);
        registerConfPass = findViewById(R.id.RepeatPassword);
        registerCreateBtn = findViewById(R.id.createAccBtn);
        TextView verifyText = findViewById(R.id.emailVerify);

        alreadyAccText = findViewById(R.id.AlreadyAccount);

        fAuth = FirebaseAuth.getInstance();

        alreadyAccText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), login.class));
                finish();
            }

        });

        registerCreateBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Fname = registerFName.getText().toString();
                String Lname = registerLName.getText().toString();
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String confPass = registerConfPass.getText().toString();

                if (Fname.isEmpty()) {
                    registerFName.setError("Please fill out this field");
                    //return;
                }

                if (Lname.isEmpty()) {
                    registerLName.setError("Please fill out this field");
                    //return;
                }

                if (email.isEmpty()) {
                    registerEmail.setError("Please fill out this field");
                    //return;
                }

                if (password.isEmpty()) {
                    registerPassword.setError("Please fill out this field");
                    //return;
                }

                if (confPass.isEmpty()) {
                    registerConfPass.setError("Please fill out this field");
                    // return;
                }

                if (!password.equals(confPass)) {
                    registerConfPass.setError("The two passwords do not match");
                    //return;
                }

                if (!Fname.isEmpty() && !Fname.isEmpty() && !email.isEmpty() &&
                        !password.isEmpty() && !confPass.isEmpty() && password.equals(confPass)) {
                    Toast.makeText(registration.this, "Data Validated!", Toast.LENGTH_SHORT).show();


                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }));



    }
}