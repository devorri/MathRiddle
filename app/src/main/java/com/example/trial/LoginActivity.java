package com.example.trial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import com.example.trial.data.AppDatabase;
import com.example.trial.data.User;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerLink;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = AppDatabase.getDatabase(this);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registerLink);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void performLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("Email required");
            return;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password required");
            return;
        }

        // Verify with DB
        User user = db.userDao().login(email, password);
        if (user != null) {
            Toast.makeText(this, "Welcome back, Medic " + user.lastName, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, IntroActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Access Denied: Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
