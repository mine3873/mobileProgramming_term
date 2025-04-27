package com.example.mobileprogramming_termproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    public static final String baseUrl = "https://415f-221-165-253-74.ngrok-free.app";
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationMenu);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.nav_home){

                } else if(itemId == R.id.nav_barcode){
                    Intent intent = new Intent(getApplicationContext(), BarcodeActicity.class);
                    startActivity(intent);
                } else if(itemId == R.id.nav_list){
                    Intent intent = new Intent(getApplicationContext(), ListTestActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}
