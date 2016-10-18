package com.example.kaina.anagram;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private MainGlobal mainGlobal;
    private Level level;

    // Timer shenanigans
    TextView timerTextView;
    long startTime = 0;
    long oldTime = 0;
    long currTime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = currTime = oldTime + System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back navigation
        mainGlobal = ((MainGlobal) this.getApplication());
        level = mainGlobal.globalData[mainGlobal.getCategory()].getWords()[mainGlobal.getLevel()];

        timerTextView = (TextView) findViewById(R.id.levelTimer);
        startTime = System.currentTimeMillis();
        oldTime = level.getTime();
        timerHandler.postDelayed(timerRunnable, 0);

        // Set current category text by data passed in
        TextView currLevel = (TextView) findViewById(R.id.currLevel);
        currLevel.setText("Level " + (mainGlobal.getLevel() + 1));

        initGame();
    }


    // Save time when page is left
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                timerHandler.removeCallbacks(timerRunnable);
                level.setTime(currTime);
                finish();
                break;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        timerHandler.removeCallbacks(timerRunnable);
        level.setTime(currTime);
        super.onBackPressed();
    }

    private void initGame() {
        LinearLayout lw = (LinearLayout) findViewById(R.id.levelWord);
        LinearLayout ll = (LinearLayout) findViewById(R.id.levelLetters);
        for (int i = 0; i < level.getWord().length(); i++) {
            // Create word buttons
            Button l1 = new Button(this);
            l1.setText(level.getWord().charAt(i) + "");
            l1.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT, 1f));
            lw.addView(l1);

            // Create letter button
            Button l2 = new Button(this);
            l2.setText(level.getWord().charAt(i) + "");
            l2.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT, 1f));
            ll.addView(l2, new Random().nextInt(lw.getChildCount()));
        }
    }
}
