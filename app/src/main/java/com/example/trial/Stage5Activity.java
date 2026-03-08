package com.example.trial;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Stage5Activity extends AppCompatActivity {

    private int meterValue = 0;
    private int divisionsMade = 0;
    private final int TARGET_DIVISIONS = 5;
    private boolean increasing = true;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage5_basale);

        final ImageView motherCell = findViewById(R.id.motherCell);
        final ProgressBar meter = findViewById(R.id.mitosisMeter);
        final TextView statusText = findViewById(R.id.mitosisText);
        final Button btnComplete = findViewById(R.id.btnComplete);

        startMeterAnimation(meter);

        motherCell.setOnClickListener(v -> {
            if (!isPlaying) return;
            
            if (meterValue >= 80) {
                divisionsMade++;
                float scale = 1.0f + (divisionsMade * 0.2f);
                motherCell.setScaleX(scale);
                motherCell.setScaleY(scale);
                Toast.makeText(this, "PERFECT DIVISION! ⭐", Toast.LENGTH_SHORT).show();
                
                if (divisionsMade >= TARGET_DIVISIONS) {
                    isPlaying = false;
                    statusText.setText("MITOSIS COMPLETE! ✅");
                    statusText.setTextColor(0xFF00FFCC);
                    btnComplete.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(this, "TOO EARLY!", Toast.LENGTH_SHORT).show();
                divisionsMade = Math.max(0, divisionsMade - 1);
            }
        });

        btnComplete.setOnClickListener(v -> showEducationalFact());
    }

    private void startMeterAnimation(ProgressBar meter) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isPlaying) return;
                
                if (increasing) meterValue += 5;
                else meterValue -= 5;
                
                if (meterValue >= 100) increasing = false;
                if (meterValue <= 0) increasing = true;
                
                meter.setProgress(meterValue);
                if (meterValue >= 80) meter.setProgressTintList(android.content.res.ColorStateList.valueOf(0xFF4ADE80));
                else meter.setProgressTintList(android.content.res.ColorStateList.valueOf(0xFFF43F5E));
                
                handler.postDelayed(this, 30);
            }
        }, 30);
    }

    private void showEducationalFact() {
        new AlertDialog.Builder(this)
                .setTitle("Anatomy Fact: Stratum Basale")
                .setMessage("Final Stage Complete! The Stratum Basale is the deepest layer of the epidermis. This is where mitosis happens—new skin cells are constantly born here to replace the ones you lose at the surface.")
                .setPositiveButton("FINISH", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }
}
