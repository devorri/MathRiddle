package com.example.trial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trial.data.AppDatabase;
import com.example.trial.data.User;

public class HomeActivity extends AppCompatActivity {

    private AppDatabase db;
    private int userId;
    private String selectedDifficulty = "easy";

    // Track selected difficulty per category
    private String arithDiff = "easy", logicDiff = "easy", patternDiff = "easy", wordDiff = "easy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = AppDatabase.getDatabase(this);
        userId = getIntent().getIntExtra("userId", -1);

        setupNavigation();
        setupDifficultyChips();
        setupCategoryButtons();
        setupRandomChallenge();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStats();
    }

    private void loadStats() {
        User user = db.userDao().getUserById(userId);
        if (user != null) {
            TextView totalSolved = findViewById(R.id.statTotalSolved);
            TextView bestStreak = findViewById(R.id.statBestStreak);
            TextView accuracy = findViewById(R.id.statAccuracy);

            totalSolved.setText(String.valueOf(user.totalSolved));
            bestStreak.setText(String.valueOf(user.bestStreak));
            accuracy.setText(user.getAccuracy() + "%");
        }
    }

    private void setupNavigation() {
        findViewById(R.id.navHome).setEnabled(false); // Already on home

        findViewById(R.id.navLeaderboard).setOnClickListener(v -> {
            Intent intent = new Intent(this, LeaderboardActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        findViewById(R.id.navProfile).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        findViewById(R.id.navSettings).setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
    }

    private void setupDifficultyChips() {
        // Arithmetic difficulty chips
        setupChipGroup(
            R.id.chipArithEasy, R.id.chipArithMedium, R.id.chipArithHard,
            diff -> arithDiff = diff
        );

        // Logic difficulty chips
        setupChipGroup(
            R.id.chipLogicEasy, R.id.chipLogicMedium, R.id.chipLogicHard,
            diff -> logicDiff = diff
        );

        // Patterns difficulty chips
        setupChipGroup(
            R.id.chipPatternEasy, R.id.chipPatternMedium, R.id.chipPatternHard,
            diff -> patternDiff = diff
        );

        // Word Problems difficulty chips
        setupChipGroup(
            R.id.chipWordEasy, R.id.chipWordMedium, R.id.chipWordHard,
            diff -> wordDiff = diff
        );
    }

    private void setupChipGroup(int easyId, int mediumId, int hardId, DifficultyCallback callback) {
        TextView easy = findViewById(easyId);
        TextView medium = findViewById(mediumId);
        TextView hard = findViewById(hardId);

        // Default: easy selected (already styled via XML)
        easy.setAlpha(1.0f);
        medium.setAlpha(0.6f);
        hard.setAlpha(0.6f);

        easy.setOnClickListener(v -> {
            easy.setAlpha(1.0f); medium.setAlpha(0.6f); hard.setAlpha(0.6f);
            callback.onSelected("easy");
        });
        medium.setOnClickListener(v -> {
            easy.setAlpha(0.6f); medium.setAlpha(1.0f); hard.setAlpha(0.6f);
            callback.onSelected("medium");
        });
        hard.setOnClickListener(v -> {
            easy.setAlpha(0.6f); medium.setAlpha(0.6f); hard.setAlpha(1.0f);
            callback.onSelected("hard");
        });
    }

    private void setupCategoryButtons() {
        findViewById(R.id.btnStartArithmetic).setOnClickListener(v ->
            startChallenge("Arithmetic", arithDiff));

        findViewById(R.id.btnStartLogic).setOnClickListener(v ->
            startChallenge("Logic", logicDiff));

        findViewById(R.id.btnStartPatterns).setOnClickListener(v ->
            startChallenge("Patterns", patternDiff));

        findViewById(R.id.btnStartWord).setOnClickListener(v ->
            startChallenge("Word Problems", wordDiff));
    }

    private void setupRandomChallenge() {
        findViewById(R.id.btnRandomChallenge).setOnClickListener(v -> {
            Intent intent = new Intent(this, ChallengeActivity.class);
            intent.putExtra("userId", userId);
            intent.putExtra("category", "Random");
            intent.putExtra("difficulty", "mixed");
            startActivity(intent);
        });
    }

    private void startChallenge(String category, String difficulty) {
        Intent intent = new Intent(this, ChallengeActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("category", category);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }

    interface DifficultyCallback {
        void onSelected(String difficulty);
    }
}
