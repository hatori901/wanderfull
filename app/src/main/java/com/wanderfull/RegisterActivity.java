package com.wanderfull;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private TextInputEditText editTextUsername, editTextEmail, editTextPassword;
    private MaterialButton buttonCreateAccount, buttonLogin;
    private ImageView buttonGoogleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        // Initialize views
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonCreateAccount = findViewById(R.id.button_create_account);
        buttonLogin = findViewById(R.id.button_login);
        buttonGoogleSignIn = findViewById(R.id.button_google_sign_in);

        // Set click listeners
        buttonCreateAccount.setOnClickListener(v -> handleRegistration());
        buttonLogin.setOnClickListener(v -> navigateToLogin());
        buttonGoogleSignIn.setOnClickListener(v -> handleGoogleSignIn());
    }

    private void handleRegistration() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validate input
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add email validation
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email address");
            return;
        }

        // Add password validation
        if (password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters long");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration success
                        FirebaseUser user = mAuth.getCurrentUser();
                        if(user != null) {
                            // Update user profile with username
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();
                            mFirestore.collection("users")
                                    .document(user.getUid())
                                    .set(new HashMap<String, Object>() {{
                                        put("username", username);
                                    }});

                        }
                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        navigateToLogin();
                    } else {
                        // Registration failed
                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void navigateToLogin() {
        // Navigate back to login activity
        finish();
    }

    private void handleGoogleSignIn() {
        // Implement Google Sign In
    }
}