package com.example.trial;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Stage2Activity extends AppCompatActivity {

    private int r1 = 45, r2 = 45, r3 = 45;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage2_lucidum);

        final Button opt1 = findViewById(R.id.option1);
        final Button opt2 = findViewById(R.id.option2);
        final Button opt3 = findViewById(R.id.option3);
        final Button opt4 = findViewById(R.id.option4);
        final TextView status = findViewById(R.id.lucidityText);
        final Button btnComplete = findViewById(R.id.btnComplete);

        View.OnClickListener checker = v -> {
            Button selected = (Button) v;
            if (selected.getText().toString().contains("Palms and Soles")) {
                status.setText("CORRECT! LUCIDITY RESTORED ✅");
                status.setTextColor(0xFF00FFCC);
                btnComplete.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Master Knowledge!", Toast.LENGTH_SHORT).show();
                disableAll(opt1, opt2, opt3, opt4);
            } else {
                status.setText("INCORRECT. RE-READ THE DATA!");
                status.setTextColor(0xFFFF3366);
                Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        };

        opt1.setOnClickListener(checker);
        opt2.setOnClickListener(checker);
        opt3.setOnClickListener(checker);
        opt4.setOnClickListener(checker);

        btnComplete.setOnClickListener(v -> showEducationalFact());
    }

    private void disableAll(Button... buttons) {
        for (Button b : buttons) b.setEnabled(false);
    }

    private void showEducationalFact() {
        new AlertDialog.Builder(this)
                .setTitle("Anatomy Fact: Stratum Lucidum")
                .setMessage("Stage 2 Clear! The Stratum Lucidum is a clear, translucent layer that refracts light. It's only found in thick skin, like your palms and soles, for extra protection.")
                .setPositiveButton("CONTINUE DESCENT", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }
}
