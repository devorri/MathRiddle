package com.example.trial;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stage3Activity extends AppCompatActivity {

    private final String TARGET = "LIPID";
    private String current = "";
    private char[] shuffledGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage3_granulosum);

        final GridLayout grid = findViewById(R.id.spellingGrid);
        final LinearLayout display = findViewById(R.id.currentSpellingContainer);
        final Button btnComplete = findViewById(R.id.btnComplete);

        prepareGrid();

        // Setup Target Display
        for (int i = 0; i < TARGET.length(); i++) {
            TextView t = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dpToPx(40), dpToPx(50));
            lp.setMarginStart(dpToPx(4));
            lp.setMarginEnd(dpToPx(4));
            t.setLayoutParams(lp);
            t.setGravity(Gravity.CENTER);
            t.setText("_");
            t.setTextSize(18);
            t.setTextColor(0xFFFDA4AF); // Neon Pink
            t.setBackgroundResource(R.drawable.bg_glass_card);
            display.addView(t);
        }

        // Setup Grid
        for (char c : shuffledGrid) {
            Button b = new Button(this, null, 0, R.style.PuzzleTileStyle);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = dpToPx(70);
            params.height = dpToPx(70);
            params.setMargins(dpToPx(6), dpToPx(6), dpToPx(6), dpToPx(6));
            b.setLayoutParams(params);
            b.setText(String.valueOf(c));
            b.setOnClickListener(v -> {
                if (current.length() < TARGET.length()) {
                    current += b.getText();
                    updateDisplay(display);
                    if (current.length() == TARGET.length()) {
                        checkResult(btnComplete);
                    }
                }
            });
            grid.addView(b);
        }

        btnComplete.setOnClickListener(v -> showEducationalFact());
    }

    private void updateDisplay(LinearLayout container) {
        for (int i = 0; i < container.getChildCount(); i++) {
            TextView t = (TextView) container.getChildAt(i);
            if (i < current.length()) {
                t.setText(String.valueOf(current.charAt(i)));
            } else {
                t.setText("_");
            }
        }
    }

    private void checkResult(Button btnComplete) {
        if (current.equals(TARGET)) {
            Toast.makeText(this, "LIPIDS RELEASED! BARRIER SECURED! ✅", Toast.LENGTH_SHORT).show();
            btnComplete.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "GLANULAR FAILURE. RETRY!", Toast.LENGTH_SHORT).show();
            current = "";
            updateDisplay((LinearLayout) findViewById(R.id.currentSpellingContainer));
        }
    }

    private void showEducationalFact() {
        new AlertDialog.Builder(this)
                .setTitle("Anatomy Fact: Stratum Granulosum")
                .setMessage("Stage 3 Secure! The Stratum Granulosum is where skin cells produce lipids (fats) that create a critical waterproof seal for your body.")
                .setPositiveButton("CONTINUE DESCENT", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void prepareGrid() {
        List<Character> letters = new ArrayList<>();
        // Target: LIPID
        for (char c : "LIPID".toCharArray()) letters.add(c);
        // Add more decoys to make it harder as requested
        for (char c : "ABCEFGHJK".toCharArray()) letters.add(c); 
        
        Collections.shuffle(letters);
        shuffledGrid = new char[letters.size()];
        for (int i = 0; i < letters.size(); i++) shuffledGrid[i] = letters.get(i);
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
