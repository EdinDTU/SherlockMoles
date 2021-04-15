package com.course22056.sherlock4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class resettingPassword extends AppCompatActivity {
    EditText newPassword,newConfPass;
    Button returnBtn,saveBtn;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetting_password);

        user = FirebaseAuth.getInstance().getCurrentUser();

        newPassword = findViewById(R.id.newPassword);
        newConfPass = findViewById(R.id.newConfPass);
        returnBtn = findViewById(R.id.returnBtn);
        saveBtn = findViewById(R.id.saveChangesBtn);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(resettingPassword.this,homeFragment.class));
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newPassword.getText().toString().isEmpty()){
                    newPassword.setError("Required field");
                }
                if(newConfPass.getText().toString().isEmpty()){
                    newConfPass.setError("Required field");
                }
                if (!newConfPass.getText().toString().equals(newPassword.getText().toString())){
                    newConfPass.setError("Passwords doesn't match");
                }

                user.updatePassword(newPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(resettingPassword.this, "Password updated.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(resettingPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });



            }
        });


    }
}