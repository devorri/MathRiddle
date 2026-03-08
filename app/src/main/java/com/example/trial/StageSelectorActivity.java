package com.example.trial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StageSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_selector);

        setupStage(R.id.btnStage1, Stage1Activity.class);
        setupStage(R.id.btnStage2, Stage2Activity.class);
        setupStage(R.id.btnStage3, Stage3Activity.class);
        setupStage(R.id.btnStage4, Stage4Activity.class);
        setupStage(R.id.btnStage5, Stage5Activity.class);
        setupStage(R.id.btnStage6, Stage6Activity.class);
        setupStage(R.id.btnStage7, Stage7Activity.class);
    }

    private void setupStage(int buttonId, final Class<?> activityClass) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StageSelectorActivity.this, activityClass));
            }
        });
    }
}
