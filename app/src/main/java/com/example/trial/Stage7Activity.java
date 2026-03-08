package com.example.trial;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stage7Activity extends AppCompatActivity {

    private final String[] BOSS_WORDS = {"EPIDERMIS", "KERATIN", "MELANIN", "SEBUM", "COLLAGEN", "ELASTIN", "FOLLICLE", "DERMIS", "SWEAT", "HYPODERMIS"};
    private int currentWordIndex = 0;
    private String currentGuess = "";
    private int timeLeft = 60;
    private CountDownTimer timer;
    
    private TextView timerText, statusText, progressText, targetWordView;
    private ImageView bossImg;
    private LinearLayout slotContainer;
    private GridLayout keyboard;
    private Button btnFinish;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage3_hypodermis);

        timerText = findViewById(R.id.timerText);
        statusText = findViewById(R.id.bossStatus);
        progressText = findViewById(R.id.bossProgress);
        targetWordView = findViewById(R.id.targetWord);
        bossImg = findViewById(R.id.bossCharacter);
        slotContainer = findViewById(R.id.bossSpellingSlots);
        keyboard = findViewById(R.id.bossKeyboard);
        btnFinish = findViewById(R.id.btnComplete3);

        startBossTimer();
        loadNextWord();

        btnFinish.setOnClickListener(v -> showEducationalFact());
    }

    private void startBossTimer() {
        if (timer != null) timer.cancel();
        timerText.setTextColor(0xFF00FFCC); 
        timer = new CountDownTimer(10000, 100) {
            public void onTick(long millisUntilFinished) {
                double seconds = millisUntilFinished / 1000.0;
                timerText.setText(String.format("TIME: %.1fs", seconds));
                if (millisUntilFinished < 1500) timerText.setTextColor(0xFFFF3366);
            }

            public void onFinish() {
                timerText.setText("TIME OUT!");
                gameOver("THE OVERLORD STRUCK TOO FAST! RECOVER AND RETRY.");
            }
        }.start();
    }

    private void loadNextWord() {
        if (currentWordIndex >= BOSS_WORDS.length) {
            victory();
            return;
        }

        currentGuess = "";
        progressText.setText("WORDS DEFEATED: " + currentWordIndex + "/10");
        String target = BOSS_WORDS[currentWordIndex];
        targetWordView.setText(target); // SHOW THE WORD TO TYPE
        
        // Boss Attack Animation when "throwing" word
        bossAttackAnimation();
        startBossTimer();

        slotContainer.removeAllViews();
        for (int i = 0; i < target.length(); i++) {
            TextView slot = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dpToPx(35), dpToPx(45));
            lp.setMargins(dpToPx(2), 0, dpToPx(2), 0);
            slot.setLayoutParams(lp);
            slot.setGravity(Gravity.CENTER);
            slot.setText("_");
            slot.setTextSize(14);
            slot.setTextColor(0xFFFACC15);
            slot.setBackgroundResource(R.drawable.bg_glass_card);
            slotContainer.addView(slot);
        }

        keyboard.removeAllViews();
        List<Character> letters = new ArrayList<>();
        for (char c : target.toCharArray()) letters.add(c);
        while (letters.size() < 12) letters.add((char) ('A' + (int)(Math.random() * 26)));
        Collections.shuffle(letters);

        for (char c : letters) {
            Button b = new Button(this, null, 0, R.style.PuzzleTileStyle);
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = dpToPx(45);
            lp.height = dpToPx(45);
            lp.setMargins(dpToPx(2), dpToPx(2), dpToPx(2), dpToPx(2));
            b.setLayoutParams(lp);
            b.setText(String.valueOf(c));
            b.setOnClickListener(v -> {
                if (currentGuess.length() < target.length()) {
                    currentGuess += b.getText();
                    updateBossSlots();
                    if (currentGuess.length() == target.length()) checkBossWord();
                }
            });
            keyboard.addView(b);
        }
    }

    private void updateBossSlots() {
        for (int i = 0; i < slotContainer.getChildCount(); i++) {
            TextView tv = (TextView) slotContainer.getChildAt(i);
            tv.setText(i < currentGuess.length() ? String.valueOf(currentGuess.charAt(i)) : "_");
        }
    }

    private void checkBossWord() {
        if (currentGuess.equals(BOSS_WORDS[currentWordIndex])) {
            currentWordIndex++;
            bossHurtAnimation();
            if (timer != null) timer.cancel(); // Stop timer before loading next
            loadNextWord();
        } else {
            currentGuess = "";
            updateBossSlots();
        }
    }

    private void bossAttackAnimation() {
        ScaleAnimation anim = new ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(200);
        anim.setRepeatCount(1);
        anim.setRepeatMode(Animation.REVERSE);
        bossImg.startAnimation(anim);
    }

    private void bossHurtAnimation() {
        bossImg.setAlpha(0.5f);
        handler.postDelayed(() -> bossImg.setAlpha(1.0f), 200);
    }

    private void gameOver(String msg) {
        timer.cancel();
        new AlertDialog.Builder(this)
            .setTitle("GAME OVER")
            .setMessage(msg)
            .setPositiveButton("RETRY", (d, w) -> recreate())
            .setNegativeButton("ABANDON", (d, w) -> finish())
            .setCancelable(false).show();
    }

    private void victory() {
        timer.cancel();
        statusText.setText("OVERLORD DEFEATED! 🏆");
        statusText.setTextColor(0xFFFACC15);
        progressText.setText("WORDS DEFEATED: 10/10");
        progressText.setTextColor(0xFF00FFCC);
        btnFinish.setVisibility(View.VISIBLE);
        bossImg.setVisibility(View.GONE);
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private void showEducationalFact() {
        new AlertDialog.Builder(this)
                .setTitle("Anatomy Fact: Hypodermis")
                .setMessage("THE CORE REACHED! You've successfully traversed all skin layers. The Hypodermis (subcutaneous fat) provides the final buffer for your internal organs, storing energy and keeping you warm. Mission Complete!")
                .setPositiveButton("STORY COMPLETE", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }
}
