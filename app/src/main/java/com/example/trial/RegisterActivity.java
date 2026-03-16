package com.example.trial;

import android.os.Bundle;
import android.util.Patterns;
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
        lastNameEditText = findViewById(R.id.lastNameEditText);
        studentIdEditText = findViewById(R.id.studentIdEditText);
        ageEditText = findViewById(R.id.ageEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);

        registerButton.setOnClickListener(v -> performRegister());
        loginLink.setOnClickListener(v -> finish());
    }

    private void performRegister() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String studentId = studentIdEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (firstName.isEmpty()) { firstNameEditText.setError("First name required"); return; }
        if (lastName.isEmpty()) { lastNameEditText.setError("Last name required"); return; }
        if (studentId.isEmpty()) { studentIdEditText.setError("Student ID required"); return; }
        if (age.isEmpty()) { ageEditText.setError("Age required"); return; }
        if (email.isEmpty()) { emailEditText.setError("Email required"); return; }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { emailEditText.setError("Invalid email"); return; }
        if (password.length() < 6) { passwordEditText.setError("Password must be at least 6 characters"); return; }

        if (db.userDao().getUserByEmail(email) != null) {
            emailEditText.setError("This email is already registered!");
            return;
        }

        User newUser = new User(firstName, lastName, studentId, age, email, password);
        db.userDao().registerUser(newUser);

        Toast.makeText(this, "Account created successfully! 🎉", Toast.LENGTH_SHORT).show();
        finish();
    }
}
