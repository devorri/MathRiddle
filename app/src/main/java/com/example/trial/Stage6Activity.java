package com.example.trial;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Stage6Activity extends AppCompatActivity {

    private final String[] TARGET_WORDS = {"DERMIS", "DERM", "SEBUM", "NERVE"};
    private final Set<String> foundWords = new HashSet<>();
    private final StringBuilder currentWord = new StringBuilder();
    
    // Grid Positions (6x6): 0-35
    private final Map<String, int[]> wordIndices = new HashMap<>();
    private TextView[] gridCells = new TextView[36];
    
    private TextView timerText, draftText;
    private GridLayout crosswordGrid;
    private RelativeLayout circularPool;
    private Button btnComplete, btnClear, btnSubmit;
    private CountDownTimer gameTimer;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage2_dermis);

        timerText = findViewById(R.id.wordTimer);
        draftText = findViewById(R.id.wordDraft);
        crosswordGrid = findViewById(R.id.crosswordGrid);
        circularPool = findViewById(R.id.circularPool);
        btnComplete = findViewById(R.id.btnComplete2);
        btnClear = findViewById(R.id.btnClearWord);
        btnSubmit = findViewById(R.id.btnSubmitWord);

        initCrosswordData();
        setupGrid();
        setupCircularPool();
        startTimer();

        btnClear.setOnClickListener(v -> {
            currentWord.setLength(0);
            draftText.setText("");
        });

        btnSubmit.setOnClickListener(v -> checkWord());
        btnComplete.setOnClickListener(v -> showEducationalFact());
    }

    private void initCrosswordData() {
        // DERMIS (Horizontal Row 2): 12(D), 13(E), 14(R), 15(M), 16(I), 17(S)
        wordIndices.put("DERMIS", new int[]{12, 13, 14, 15, 16, 17});
        // NERVE (Vertical Col 1): 7(N), 13(E), 19(R), 25(V), 31(E) -> Shared 'E' at 13
        wordIndices.put("NERVE", new int[]{7, 13, 19, 25, 31});
        // DERM (Vertical Col 2): 2(D), 8(E), 14(R), 20(M) -> Shared 'R' at 14
        wordIndices.put("DERM", new int[]{2, 8, 14, 20});
        // SEBUM (Horizontal Row 5): 30(S), 31(E), 32(B), 33(U), 34(M) -> Shared 'E' at 31
        wordIndices.put("SEBUM", new int[]{30, 31, 32, 33, 34});
    }

    private void setupGrid() {
        crosswordGrid.removeAllViews();
        Set<Integer> activeCells = new HashSet<>();
        for (int[] indices : wordIndices.values()) {
            for (int idx : indices) activeCells.add(idx);
        }

        for (int i = 0; i < 36; i++) {
            TextView cell = new TextView(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = dpToPx(38);
            params.height = dpToPx(42);
            params.setMargins(dpToPx(1), dpToPx(1), dpToPx(1), dpToPx(1));
            cell.setLayoutParams(params);
            cell.setGravity(Gravity.CENTER);
            cell.setTextSize(14);
            cell.setTypeface(null, android.graphics.Typeface.BOLD);
            
            if (activeCells.contains(i)) {
                cell.setBackgroundResource(R.drawable.bg_glass_card);
                cell.setTextColor(0xFF00FFCC);
            } else {
                cell.setVisibility(View.INVISIBLE);
            }
            
            gridCells[i] = cell;
            crosswordGrid.addView(cell);
        }
    }

    private void setupCircularPool() {
        circularPool.removeAllViews();
        // DERMIS-SEBUM-NERVE unique letters: D,E,R,M,I,S, B,U,N,V
        char[] letters = {'D', 'E', 'R', 'M', 'I', 'S', 'B', 'U', 'N', 'V'};
        int radius = dpToPx(95);
        int centerX = dpToPx(130) - dpToPx(23); 
        int centerY = dpToPx(130) - dpToPx(23);

        for (int i = 0; i < letters.length; i++) {
            final char letter = letters[i];
            TextView node = new TextView(this);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(dpToPx(46), dpToPx(46));
            node.setLayoutParams(lp);
            
            float angle = (float) Math.toRadians(i * (360.0 / letters.length) - 90);
            node.setX(centerX + radius * (float)Math.cos(angle));
            node.setY(centerY + radius * (float)Math.sin(angle));
            
            node.setText(String.valueOf(letter));
            node.setGravity(Gravity.CENTER);
            node.setTextColor(0xFFFFFFFF);
            node.setTextSize(18);
            node.setTypeface(null, android.graphics.Typeface.BOLD);
            node.setBackgroundResource(R.drawable.bg_glass_card);
            
            node.setClickable(true);
            node.setOnClickListener(v -> {
                v.animate().scaleX(1.3f).scaleY(1.3f).setDuration(80).withEndAction(() -> 
                    v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(80).start()
                ).start();
                currentWord.append(letter);
                draftText.setText(currentWord.toString());
            });
            circularPool.addView(node);
        }
    }

    private void checkWord() {
        String word = currentWord.toString().toUpperCase();
        if (wordIndices.containsKey(word)) {
            if (foundWords.contains(word)) {
                Toast.makeText(this, "ALREADY FOUND!", Toast.LENGTH_SHORT).show();
            } else {
                foundWords.add(word);
                int[] indices = wordIndices.get(word);
                for (int i = 0; i < word.length(); i++) {
                    gridCells[indices[i]].setText(String.valueOf(word.charAt(i)));
                }
                Toast.makeText(this, "EXCELLENT: " + word + "!", Toast.LENGTH_SHORT).show();
                if (foundWords.size() == TARGET_WORDS.length) victory();
            }
        } else {
            Toast.makeText(this, "NOT IN CROSSWORD", Toast.LENGTH_SHORT).show();
        }
        currentWord.setLength(0);
        draftText.setText("");
    }


    private void startTimer() {
        gameTimer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                int mins = (int) (millisUntilFinished / 60000);
                int secs = (int) (millisUntilFinished % 60000 / 1000);
                timerText.setText(String.format("%02d:%02d", mins, secs));
                if (millisUntilFinished < 30000) timerText.setTextColor(0xFFFF3366);
            }

            public void onFinish() {
                timerText.setText("00:00");
                if (foundWords.size() < TARGET_WORDS.length) gameOver();
            }
        }.start();
    }

    private void victory() {
        if (gameTimer != null) gameTimer.cancel();
        draftText.setText("DERMIS STABILIZED! ✅");
        draftText.setTextColor(0xFF00FFCC);
        btnComplete.setVisibility(View.VISIBLE);
    }

    private void gameOver() {
        new AlertDialog.Builder(this)
                .setTitle("TIME'S UP!")
                .setMessage("The DERMIS is overheating! You need to solve the crossword faster.")
                .setPositiveButton("RETRY", (d, w) -> recreate())
                .setNegativeButton("RETREAT", (d, w) -> finish())
                .setCancelable(false).show();
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private void showEducationalFact() {
        new AlertDialog.Builder(this)
                .setTitle("Anatomy Fact: Dermis")
                .setMessage("Great Job! The Dermis is the layer of skin between the epidermis and subcutaneous tissues. It serves as the 'engine room', housing blood vessels, nerves, and glands.")
                .setPositiveButton("FINAL DESCENT", (dialog, which) -> finish())
                .setCancelable(false).show();
    }
}
