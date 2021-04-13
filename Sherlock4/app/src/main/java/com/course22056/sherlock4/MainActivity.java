package com.course22056.sherlock4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navbar_layout);



        Log.d(TAG, "onCreate: navbar_layout");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer,new homeFragment()).commit();
        Log.d(TAG, "Replace container with homeFragment()");


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setSelectedItemId(R.id.Navhome);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if(item.getItemId() == R.id.Navhome){
                        selectedFragment = new homeFragment();
                        Log.d(TAG, "onNavigationItemSelected: NavHome");
                    }else if(item.getItemId() == R.id.Navstats){
                        selectedFragment = new statsFragment();
                        Log.d(TAG, "onNavigationItemSelected: NavStats");
                    }else if (item.getItemId() == R.id.Navinfo){
                        selectedFragment = new infoFragment();
                        Log.d(TAG, "onNavigationItemSelected: NavInfo");
                    }

                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer,selectedFragment).commit();
                    return true;
                }
            };
}