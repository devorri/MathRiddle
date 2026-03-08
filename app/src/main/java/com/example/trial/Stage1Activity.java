package com.example.trial;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Gravity;
import android.view.View;
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

public class Stage1Activity extends AppCompatActivity {

    private final String TARGET_WORD = "SHIELD";
    private String currentGuess = "";
    private char[] shuffledLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage1_corneum);

        final LinearLayout slotsContainer = findViewById(R.id.letterSlotsContainer);
        final GridLayout keyboard = findViewById(R.id.keyboardGrid);
        final Button btnComplete = findViewById(R.id.btnComplete);

        prepareKeyboard();

        // Setup Slots
        for (int i = 0; i < TARGET_WORD.length(); i++) {
            TextView slot = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dpToPx(45), dpToPx(55));
            lp.setMarginStart(dpToPx(4));
            lp.setMarginEnd(dpToPx(4));
            slot.setLayoutParams(lp);
            slot.setGravity(Gravity.CENTER);
            slot.setText("_");
            slot.setTextSize(20);
            slot.setTextColor(0xFF22D3EE); // Neon Cyan
            slot.setBackgroundResource(R.drawable.bg_glass_card);
            slotsContainer.addView(slot);
        }

        // Setup Keyboard
        for (char c : shuffledLetters) {
            Button key = new Button(this, null, 0, R.style.PuzzleTileStyle);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = dpToPx(55);
            params.height = dpToPx(55);
            params.setMargins(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));
            key.setLayoutParams(params);
            key.setText(String.valueOf(c));
            key.setOnClickListener(v -> {
                if (currentGuess.length() < TARGET_WORD.length()) {
                    currentGuess += key.getText();
                    updateDisplay(slotsContainer);
                    if (currentGuess.length() == TARGET_WORD.length()) {
                        checkAnswer(btnComplete);
                    }
                }
            });
            keyboard.addView(key);
        }

        btnComplete.setOnClickListener(v -> showEducationalFact());
    }

    private void updateDisplay(LinearLayout container) {
        for (int i = 0; i < container.getChildCount(); i++) {
            TextView slot = (TextView) container.getChildAt(i);
            if (i < currentGuess.length()) {
                slot.setText(String.valueOf(currentGuess.charAt(i)));
            } else {
                slot.setText("_");
            }
        }
    }

    private void checkAnswer(Button btnComplete) {
        if (currentGuess.equals(TARGET_WORD)) {
            Toast.makeText(this, "BARRIER RESTORED! ✅", Toast.LENGTH_SHORT).show();
            btnComplete.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "INCORRECT. TRY AGAIN!", Toast.LENGTH_SHORT).show();
            currentGuess = "";
            updateDisplay((LinearLayout) findViewById(R.id.letterSlotsContainer));
        }
    }

    private void showEducationalFact() {
        new AlertDialog.Builder(this)
                .setTitle("Anatomy Fact: Stratum Corneum")
                .setMessage("Stage 1 Secure! The Stratum Corneum is your 'Shield'. It consists of flattened, dead cells that act as your body's primary waterproof barrier.")
                .setPositiveButton("PROCEED", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void prepareKeyboard() {
        List<Character> letters = new ArrayList<>();
        for (char c : "SHIELDOPXTRN".toCharArray()) letters.add(c);
        Collections.shuffle(letters);
        shuffledLetters = new char[letters.size()];
        for (int i = 0; i < letters.size(); i++) shuffledLetters[i] = letters.get(i);
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
