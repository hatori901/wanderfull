package com.wanderfull;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DiscoverActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_explore);
        // Setup Bottom Navigation - Fixed version
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    startActivity(new Intent(DiscoverActivity.this, MainActivity.class));

                    return true;
                } else if (itemId == R.id.navigation_explore) {
                    // Handle explore
                    return true;
                } else if (itemId == R.id.navigation_bookmark) {
                    startActivity(new Intent(DiscoverActivity.this, BookmarksActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    startActivity(new Intent(DiscoverActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}
