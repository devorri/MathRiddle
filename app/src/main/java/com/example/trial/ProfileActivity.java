package com.example.trial;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trial.data.AppDatabase;
import com.example.trial.data.User;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        int userId = getIntent().getIntExtra("userId", -1);
        AppDatabase db = AppDatabase.getDatabase(this);
        User user = db.userDao().getUserById(userId);

        if (user != null) {
            ((TextView) findViewById(R.id.profileName)).setText(user.firstName + " " + user.lastName);
            ((TextView) findViewById(R.id.profileEmail)).setText(user.email);
            ((TextView) findViewById(R.id.profileSolved)).setText(String.valueOf(user.totalSolved));
            ((TextView) findViewById(R.id.profileStreak)).setText(String.valueOf(user.bestStreak));
            ((TextView) findViewById(R.id.profileAccuracy)).setText(user.getAccuracy() + "%");
            ((TextView) findViewById(R.id.profileAttempted)).setText(String.valueOf(user.totalAttempted));
            ((TextView) findViewById(R.id.profileStudentId)).setText("Student ID: " + user.studentId);
            ((TextView) findViewById(R.id.profileAge)).setText("Age: " + user.age);
        }

        findViewById(R.id.btnBackHome).setOnClickListener(v -> finish());
    }
}
