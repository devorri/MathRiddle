package com.example.trial;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trial.data.AppDatabase;
import com.example.trial.data.User;

import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        LinearLayout container = findViewById(R.id.leaderboardContainer);
        AppDatabase db = AppDatabase.getDatabase(this);
        List<User> users = db.userDao().getAllUsersByScore();

        int currentUserId = getIntent().getIntExtra("userId", -1);

        String[] medals = {"🥇", "🥈", "🥉"};

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            row.setPadding(dpToPx(16), dpToPx(14), dpToPx(16), dpToPx(14));
            row.setBackgroundResource(R.drawable.bg_category_card);

            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rowParams.bottomMargin = dpToPx(8);
            row.setLayoutParams(rowParams);

            // Highlight current user
            if (user.id == currentUserId) {
                row.setBackgroundResource(R.drawable.bg_stat_card);
                row.setAlpha(1.0f);
            }

            // Rank
            String rankText = i < 3 ? medals[i] : String.valueOf(i + 1);
            TextView rank = new TextView(this);
            rank.setText(rankText);
            rank.setTextSize(i < 3 ? 24 : 18);
            rank.setGravity(Gravity.CENTER);
            rank.setWidth(dpToPx(44));
            row.addView(rank);

            // Name column
            LinearLayout nameCol = new LinearLayout(this);
            nameCol.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            nameParams.setMarginStart(dpToPx(12));
            nameCol.setLayoutParams(nameParams);

            TextView name = new TextView(this);
            name.setText(user.firstName + " " + user.lastName);
            name.setTextColor(getResources().getColor(R.color.text_primary, getTheme()));
            name.setTextSize(16);
            name.setTypeface(null, android.graphics.Typeface.BOLD);
            nameCol.addView(name);

            TextView stats = new TextView(this);
            stats.setText(user.totalSolved + " solved · " + user.getAccuracy() + "% accuracy");
            stats.setTextColor(getResources().getColor(R.color.text_secondary, getTheme()));
            stats.setTextSize(12);
            nameCol.addView(stats);

            row.addView(nameCol);

            // Score
            TextView scoreView = new TextView(this);
            scoreView.setText(String.valueOf(user.totalSolved));
            scoreView.setTextColor(getResources().getColor(R.color.primary, getTheme()));
            scoreView.setTextSize(22);
            scoreView.setTypeface(null, android.graphics.Typeface.BOLD);
            row.addView(scoreView);

            container.addView(row);
        }

        if (users.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("No players yet. Be the first to complete a challenge! 🚀");
            empty.setTextColor(getResources().getColor(R.color.text_secondary, getTheme()));
            empty.setTextSize(16);
            empty.setGravity(Gravity.CENTER);
            empty.setPadding(dpToPx(16), dpToPx(48), dpToPx(16), dpToPx(48));
            container.addView(empty);
        }
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}
