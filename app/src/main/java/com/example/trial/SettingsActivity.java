package com.example.trial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trial.data.AppDatabase;
import com.example.trial.data.User;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        int userId = getIntent().getIntExtra("userId", -1);
        AppDatabase db = AppDatabase.getDatabase(this);
        User user = db.userDao().getUserById(userId);

        if (user != null) {
            ((TextView) findViewById(R.id.settingsEmail)).setText("Logged in as: " + user.email);
        }

        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Log Out", (dialog, which) -> {
                    // Clear session
                    SharedPreferences prefs = getSharedPreferences("math_riddles_prefs", MODE_PRIVATE);
                    prefs.edit().clear().apply();

                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });

        findViewById(R.id.btnBackHome).setOnClickListener(v -> finish());
    }
}
