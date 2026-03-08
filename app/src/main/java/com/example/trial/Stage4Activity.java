package com.example.trial;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.util.Random;

public class Stage4Activity extends AppCompatActivity {

    private int neutralizedCount = 0;
    private final int TARGET_COUNT = 15;
    private final Random random = new Random();
    private final Handler handler = new Handler();
    private FrameLayout infectionZone;
    private TextView statusText;
    private Button btnComplete;
    private boolean isGameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage4_spinosum);

        infectionZone = findViewById(R.id.infectionZone);
        statusText = findViewById(R.id.infectionStatusText);
        btnComplete = findViewById(R.id.btnComplete);

        startInfection();

        btnComplete.setOnClickListener(v -> showEducationalFact());
    }

    private void startInfection() {
        if (isGameOver) return;
        
        handler.postDelayed(() -> {
            if (!isGameOver) {
                spawnVirus();
                startInfection();
            }
        }, 1000);
    }

    private void spawnVirus() {
        final ImageView virus = new ImageView(this);
        virus.setImageResource(R.drawable.ic_virus);
        int size = dpToPx(60);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size);
        
        int maxX = infectionZone.getWidth() - size;
        int maxY = infectionZone.getHeight() - size;
        
        if (maxX > 0 && maxY > 0) {
            params.leftMargin = random.nextInt(maxX);
            params.topMargin = random.nextInt(maxY);
        }
        
        virus.setLayoutParams(params);
        virus.setOnClickListener(v -> {
            infectionZone.removeView(virus);
            neutralizedCount++;
            statusText.setText("NEUTRALIZED: " + neutralizedCount + "/" + TARGET_COUNT);
            
            if (neutralizedCount >= TARGET_COUNT) {
                isGameOver = true;
                statusText.setText("INFECTION CLEARED! ✅");
                statusText.setTextColor(0xFFA855F7);
                btnComplete.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Immune System Victorious!", Toast.LENGTH_SHORT).show();
            }
        });

        infectionZone.addView(virus);

        // Auto remove virus after 2 seconds if not tapped
        handler.postDelayed(() -> {
            if (infectionZone.indexOfChild(virus) != -1) {
                infectionZone.removeView(virus);
            }
        }, 2000);
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private void showEducationalFact() {
        new AlertDialog.Builder(this)
                .setTitle("Anatomy Fact: Stratum Spinosum")
                .setMessage("Stage 4 Secured! The Stratum Spinosum contains Langerhans cells—specialized immune cells that act as sentinels to capture and destroy invading viruses and bacteria.")
                .setPositiveButton("REACH THE ROOT", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }
}
