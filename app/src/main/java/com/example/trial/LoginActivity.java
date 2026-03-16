package com.example.trial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

        // Auto-login check
        SharedPreferences prefs = getSharedPreferences("math_riddles_prefs", MODE_PRIVATE);
        int savedUserId = prefs.getInt("userId", -1);
        if (savedUserId != -1) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("userId", savedUserId);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        db = AppDatabase.getDatabase(this);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registerLink);

        loginButton.setOnClickListener(v -> performLogin());
        registerLink.setOnClickListener(v ->
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );
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

        User user = db.userDao().login(email, password);
        if (user != null) {
            // Save session
            SharedPreferences prefs = getSharedPreferences("math_riddles_prefs", MODE_PRIVATE);
            prefs.edit().putInt("userId", user.id).apply();

            Toast.makeText(this, "Welcome back, " + user.firstName + "! 🎉", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("userId", user.id);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}
