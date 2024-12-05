package com.wanderfull;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView profileName;
    private Button editProfileButton;
    private Button signOutButton;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        // Setup Bottom Navigation - Fixed version
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));

                    return true;
                } else if (itemId == R.id.navigation_explore) {
                    startActivity(new Intent(ProfileActivity.this, DiscoverActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_bookmark) {
                    // Handle bookmark
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });

        profileImage = findViewById(R.id.profileImage);
        profileName = findViewById(R.id.profileName);
        editProfileButton = findViewById(R.id.editProfileButton);
        signOutButton = findViewById(R.id.signOutButton);

        // Load the user's profile information
        loadProfileData();

        // Set click listeners
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });

        signOutButton.setOnClickListener(v -> {
            // Sign the user out
            signOut();
        });
    }

    private void loadProfileData() {
        // Fetch the user's profile data from the database
        // and populate the UI elements
        String userName = "flimzgin";
        profileName.setText(userName);
        // Load the profile image
        // Glide.with(this).load(profileImageUrl).into(profileImage);
    }

    private void signOut() {
        // Sign the user out of Firebase
        FirebaseAuth.getInstance().signOut();

        // Navigate to the login screen
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}