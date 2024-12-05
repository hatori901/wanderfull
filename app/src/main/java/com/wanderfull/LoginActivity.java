package com.wanderfull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private TextInputEditText editTextEmail, editTextPassword;
    private MaterialButton buttonSignIn, buttonCreateAccount;
    private TextView textForgotPassword;
    private ImageView buttonGoogleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonCreateAccount = findViewById(R.id.button_create_account);
        textForgotPassword = findViewById(R.id.text_forgot_password);
        buttonGoogleSignIn = findViewById(R.id.button_google_sign_in);

        // Set click listeners
        buttonSignIn.setOnClickListener(v -> loginUser());
        buttonCreateAccount.setOnClickListener(v -> registerUser());
        buttonGoogleSignIn.setOnClickListener(v -> loginGoogle());
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validate input
        if (email.isEmpty() || password.isEmpty()) {
            if(email.isEmpty()){
                editTextEmail.setError("Email harus diisi");
            }
            if(password.isEmpty()) {
                editTextPassword.setError("Password harus diisi");
            }
            return;
        }

        // Show progress dialog
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        // Sign in success
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Login berhasil",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        // If sign in fails, add error message on the email field and password field
                        editTextEmail.setError("Invalid email");
                        editTextPassword.setError("Invalid password");
                        Toast.makeText(LoginActivity.this, "Login failed: " +
                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginGoogle() {

    }

    private void registerUser() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
