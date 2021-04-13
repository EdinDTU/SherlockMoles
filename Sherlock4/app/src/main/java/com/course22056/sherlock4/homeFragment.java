package com.course22056.sherlock4;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class homeFragment extends Fragment {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    private View v;

    private ImageView selectedImage;


    public homeFragment() {
        // Required empty public constructor
    }

    TextView logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);

        cameraButton();
        verifyEmail();

        final ImageView popImg = v.findViewById(R.id.settingsCogHome);
        popImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(getContext(),popImg);

                popupMenu.getMenuInflater().inflate(R.menu.options,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getItemId() == R.id.resetPassword){
                            Toast.makeText(getContext(), "Resetting password...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(),resettingPassword.class));
                        }else if(item.getItemId() == R.id.aboutUs){
                            Toast.makeText(getContext(), "About us...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(),AboutUs.class));
                        }else if (item.getItemId() == R.id.logoutPOP){
                            Toast.makeText(getContext(), "Logging out...", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getContext(),login.class));
                        }
                        return true;
                    }
                });

                popupMenu.show();

            }
        });
        return v;
    }

    private void verifyEmail() {
        FirebaseAuth fAuth;

        fAuth = FirebaseAuth.getInstance();

        String email_login = fAuth.getCurrentUser().getEmail();
        TextView verifyText = v.findViewById(R.id.emailVerify);
        TextView legitText = v.findViewById(R.id.legitEmail);
        if(!fAuth.getCurrentUser().isEmailVerified()){
            verifyText.setVisibility(View.VISIBLE);
        }else{
            // Add logged in as: xxxx@gmail.com
            verifyText.setVisibility(View.GONE);
            legitText.setVisibility(View.VISIBLE);
            legitText.setText("Logged in as: " + email_login);
            legitText.setTextColor(getResources().getColor(R.color.black));
        }

        verifyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Verification Email Sent!", Toast.LENGTH_SHORT).show();
                        verifyText.setVisibility(View.GONE);
                        legitText.setVisibility(View.VISIBLE);
                        legitText.setText("Logged in as: " + email_login);
                        legitText.setTextColor(getResources().getColor(R.color.black));
                        ;

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



    private void cameraButton() {

        ImageButton cameraBtn = v.findViewById(R.id.cameraBTN);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You clicked the button!", Toast.LENGTH_SHORT).show();

                askCameraPermissions();


            }
        });
    }

    private void askCameraPermissions() {

        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(getContext(), "Camera Permission is Required to Use Camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Toast.makeText(getContext(), "Camera Open Request", Toast.LENGTH_SHORT).show();

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }


}