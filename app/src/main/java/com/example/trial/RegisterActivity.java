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

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText firstNameEditText, lastNameEditText, studentIdEditText, ageEditText, emailEditText, passwordEditText;
    private Button registerButton;
    private TextView loginLink;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = AppDatabase.getDatabase(this);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        // ... (rest of findViewByIds)
        lastNameEditText = findViewById(R.id.lastNameEditText);
        studentIdEditText = findViewById(R.id.studentIdEditText);
        ageEditText = findViewById(R.id.ageEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegister();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to Login
            }
        });
    }

    private void performRegister() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String studentId = studentIdEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // (Validation logic remains same)
        if (firstName.isEmpty()) { firstNameEditText.setError("First name required"); return; }
        if (lastName.isEmpty()) { lastNameEditText.setError("Surname required"); return; }
        if (studentId.isEmpty()) { studentIdEditText.setError("Student ID required"); return; }
        if (age.isEmpty()) { ageEditText.setError("Age required"); return; }
        if (email.isEmpty()) { emailEditText.setError("Email required"); return; }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { emailEditText.setError("Invalid email"); return; }
        if (password.length() < 6) { passwordEditText.setError("Password too short"); return; }

        // Check if user exists
        if (db.userDao().getUserByEmail(email) != null) {
            emailEditText.setError("This medic sequence is already registered!");
            return;
        }

        // Save to DB
        User newUser = new User(firstName, lastName, studentId, age, email, password);
        db.userDao().registerUser(newUser);

        Toast.makeText(this, "Clinical record established. Access granted.", Toast.LENGTH_SHORT).show();
        finish(); // Return to Login
    }
}
