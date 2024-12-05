package com.wanderfull;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class EditProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText dobEditText;
    private EditText locationEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private Button saveProfileButton;
    private Button backProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        usernameEditText = findViewById(R.id.username_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        dobEditText = findViewById(R.id.dob_edit_text);
        locationEditText = findViewById(R.id.location_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        saveProfileButton = findViewById(R.id.save_profile_button);
        backProfileButton = findViewById(R.id.back_button);

        // Populate the form with current user data
        populateUserData();

        saveProfileButton.setOnClickListener(v -> updateUserProfile());
        backProfileButton.setOnClickListener(v -> startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class)));
    }

    private void populateUserData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            usernameEditText.setText(currentUser.getDisplayName());
            emailEditText.setText(currentUser.getEmail());

            mFirestore.collection("users")
                    .document(currentUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            dobEditText.setText(documentSnapshot.getString("dateOfBirth"));
                            locationEditText.setText(documentSnapshot.getString("location"));
                            phoneEditText.setText(documentSnapshot.getString("phoneNumber"));
                        }
                    });
        }
    }

    private void updateUserProfile() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Update Firebase Authentication user profile
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(usernameEditText.getText().toString())
                    .build();
            currentUser.updateProfile(profileUpdates)
                    .addOnSuccessListener(aVoid -> {
                        // Update Firestore user document
                        Map<String, Object> userUpdates = new HashMap<>();
                        userUpdates.put("dateOfBirth", dobEditText.getText().toString());
                        userUpdates.put("location", locationEditText.getText().toString());
                        userUpdates.put("phoneNumber", phoneEditText.getText().toString());

                        mFirestore.collection("users")
                                .document(currentUser.getUid())
                                .update(userUpdates)
                                .addOnSuccessListener(aVoid1 -> {
                                    // Password update
                                    String newPassword = passwordEditText.getText().toString();
                                    if (!TextUtils.isEmpty(newPassword)) {
                                        currentUser.updatePassword(newPassword)
                                                .addOnSuccessListener(aVoid2 -> {
                                                    // Profile update successful
                                                    Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                                })
                                                .addOnFailureListener(e -> {
                                                    // Password update failed
                                                    Toast.makeText(EditProfileActivity.this, "Failed to update password: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                });
                                    } else {
                                        // Profile update successful
                                        Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    // Firestore update failed
                                    Toast.makeText(EditProfileActivity.this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    })
                    .addOnFailureListener(e -> {
                        // Authentication update failed
                        Toast.makeText(EditProfileActivity.this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}