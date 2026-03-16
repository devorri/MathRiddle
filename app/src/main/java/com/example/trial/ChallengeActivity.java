package com.example.trial;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import com.example.trial.data.AppDatabase;
import com.example.trial.data.Riddle;
import com.example.trial.data.RiddleBank;
import com.example.trial.data.User;

import java.util.List;

public class ChallengeActivity extends AppCompatActivity {

    private AppDatabase db;
    private int userId;
    private String category;
    private String difficulty;

    private List<Riddle> riddles;
    private int currentIndex = 0;
    private int score = 0;
    private int streak = 0;
    private int bestStreak = 0;
    private boolean answered = false;

    private TextView questionText, scoreText, streakText, progressText;
    private ProgressBar progressBar;
    private MaterialButton[] answerButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        db = AppDatabase.getDatabase(this);
        userId = getIntent().getIntExtra("userId", -1);
        category = getIntent().getStringExtra("category");
        difficulty = getIntent().getStringExtra("difficulty");

        // Setup header
        TextView title = findViewById(R.id.challengeTitle);
        TextView subtitle = findViewById(R.id.challengeSubtitle);
        title.setText(category + " Challenge");
        subtitle.setText("Difficulty: " + capitalize(difficulty));

        // Bind views
        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);
        streakText = findViewById(R.id.streakText);
        progressText = findViewById(R.id.progressText);
        progressBar = findViewById(R.id.progressBar);

        answerButtons = new MaterialButton[]{
            findViewById(R.id.answerA),
            findViewById(R.id.answerB),
            findViewById(R.id.answerC),
            findViewById(R.id.answerD)
        };

        // Load riddles
        if ("Random".equals(category)) {
            riddles = RiddleBank.getRandomRiddles();
        } else {
            riddles = RiddleBank.getRiddles(category, difficulty);
        }

        progressBar.setMax(riddles.size());
        loadQuestion();
    }

    private void loadQuestion() {
        if (currentIndex >= riddles.size()) {
            showResults();
            return;
        }

        answered = false;
        Riddle riddle = riddles.get(currentIndex);

        questionText.setText(riddle.getQuestion());
        progressBar.setProgress(currentIndex + 1);
        progressText.setText((currentIndex + 1) + "/" + riddles.size());
        scoreText.setText(String.valueOf(score));
        streakText.setText("🔥 " + streak);

        String[] labels = {"A", "B", "C", "D"};
        String[] choices = riddle.getChoices();

        for (int i = 0; i < 4; i++) {
            final int index = i;
            answerButtons[i].setText(labels[i] + ". " + choices[i]);
            answerButtons[i].setEnabled(true);

            // Reset button appearance
            answerButtons[i].setBackgroundTintList(ColorStateList.valueOf(0x00000000));
            answerButtons[i].setTextColor(getResources().getColor(R.color.text_primary, getTheme()));
            answerButtons[i].setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.primary, getTheme())));
            answerButtons[i].setRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.primary, getTheme())).withAlpha(40));

            answerButtons[i].setOnClickListener(v -> {
                if (!answered) {
                    answered = true;
                    handleAnswer(index, riddle.getCorrectIndex());
                }
            });
        }
    }

    private void handleAnswer(int selectedIndex, int correctIndex) {
        // Disable all buttons
        for (MaterialButton btn : answerButtons) {
            btn.setEnabled(false);
        }

        // Highlight correct answer green
        answerButtons[correctIndex].setBackgroundTintList(
            ColorStateList.valueOf(0xFF10B981));
        answerButtons[correctIndex].setTextColor(0xFFFFFFFF);

        if (selectedIndex == correctIndex) {
            score++;
            streak++;
            if (streak > bestStreak) bestStreak = streak;
            scoreText.setText(String.valueOf(score));
            streakText.setText("🔥 " + streak);
        } else {
            // Highlight wrong answer red
            answerButtons[selectedIndex].setBackgroundTintList(
                ColorStateList.valueOf(0xFFEF4444));
            answerButtons[selectedIndex].setTextColor(0xFFFFFFFF);
            streak = 0;
            streakText.setText("🔥 " + streak);
        }

        // Auto-advance after 1.2 seconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            currentIndex++;
            loadQuestion();
        }, 1200);
    }

    private void showResults() {
        // Update user stats in DB
        User user = db.userDao().getUserById(userId);
        if (user != null) {
            int newTotalSolved = user.totalSolved + score;
            int newBestStreak = Math.max(user.bestStreak, bestStreak);
            int newTotalCorrect = user.totalCorrect + score;
            int newTotalAttempted = user.totalAttempted + riddles.size();

            db.userDao().updateStats(userId, newTotalSolved, newBestStreak, newTotalCorrect, newTotalAttempted);
        }

        int accuracy = riddles.size() > 0 ? Math.round((float) score / riddles.size() * 100) : 0;

        new AlertDialog.Builder(this)
            .setTitle("🎉 Challenge Complete!")
            .setMessage(
                "Category: " + category + "\n" +
                "Score: " + score + "/" + riddles.size() + "\n" +
                "Accuracy: " + accuracy + "%\n" +
                "Best Streak: " + bestStreak + " 🔥\n\n" +
                (accuracy >= 80 ? "Amazing job! 🌟" :
                 accuracy >= 60 ? "Good effort! Keep practicing! 💪" :
                 "Don't give up! Try again! 🎯")
            )
            .setPositiveButton("Back to Home", (dialog, which) -> finish())
            .setNegativeButton("Try Again", (dialog, which) -> {
                currentIndex = 0;
                score = 0;
                streak = 0;
                bestStreak = 0;
                if ("Random".equals(category)) {
                    riddles = RiddleBank.getRandomRiddles();
                } else {
                    riddles = RiddleBank.getRiddles(category, difficulty);
                }
                loadQuestion();
            })
            .setCancelable(false)
            .show();
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
