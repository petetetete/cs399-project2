package com.example.kaina.anagram;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private MainGlobal mainGlobal;
    private Level level;
    private String[] currGuess;

    private TextView timerTextView;
    private TextView levelStatus;
    private LinearLayout wordLayout;
    private LinearLayout letterLayout;

    // Timer shenanigans
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

        // Grab views from display
        timerTextView = (TextView) findViewById(R.id.levelTimer);
        levelStatus = (TextView) findViewById(R.id.levelStatus);
        wordLayout = (LinearLayout) findViewById(R.id.levelWord);
        letterLayout = (LinearLayout) findViewById(R.id.levelLetters);

        initGame();
    }


    // Save time when page is left
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                stopTimer();
                finish();
                break;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        stopTimer();
        super.onBackPressed();
    }

    private void updateGuess() {
        boolean full = true;
        for (int i = 0; i < wordLayout.getChildCount(); i++) {
            TextView word = (TextView) wordLayout.getChildAt(i);
            word.setText(currGuess[i]);
            if (currGuess[i] == "_") full = false;
        }

        // Victory achieved
        if (level.getWord().equals(TextUtils.join("", currGuess))) {
            levelStatus.setText("Victory!");
            level.setCompleted();
            stopTimer();
        }
        else if (full) {
            levelStatus.setText("No dice.");
        }
        else levelStatus.setText("");
    }

    // Method used to add letter to selected
    private void addLetter(String letter) {
        for (int i = 0; i < currGuess.length; i++) {
            if (currGuess[i] == "_") {
                currGuess[i] = letter;
                break;
            }
        }
    }

    // Method used to remove letter from selected
    private void removeLetter(String letter) {
        for (int i = 0; i < letterLayout.getChildCount(); i++) {
            TextView word = (TextView) letterLayout.getChildAt(i);
            if (word.getText() == letter && !word.isEnabled()) {
                word.setEnabled(true);
                break;
            }
        }
    }

    // Method used to stop timer
    private void stopTimer() {
        timerHandler.removeCallbacks(timerRunnable);
        level.setTime(currTime);
    }

    private void initGame() {
        // Save current level as a global variable
        level = mainGlobal.globalData[mainGlobal.getCategory()].getWords()[mainGlobal.getLevel()];

        // Start timer
        startTime = System.currentTimeMillis();
        oldTime = level.getTime();
        timerHandler.postDelayed(timerRunnable, 0);

        // Reset current guess
        currGuess = new String[level.getWord().length()];
        Arrays.fill(currGuess, "_");

        // Set current category text by data passed in
        TextView currLevel = (TextView) findViewById(R.id.currLevel);
        currLevel.setText("Level " + (mainGlobal.getLevel() + 1));

        // Reset and create layouts
        wordLayout.removeAllViews();
        letterLayout.removeAllViews();
        for (int i = 0; i < level.getWord().length(); i++) {

            // Create word displays
            final int letterIndex = i;
            TextView l1 = new TextView(this);
            l1.setTextSize(52);
            l1.setGravity(Gravity.CENTER);
            l1.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT, 1f));

            // Letter button click listener
            l1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    TextView t = (TextView) v;
                    if (t.getText() != "_") {
                        removeLetter(t.getText().toString());
                        currGuess[letterIndex] = "_";
                        updateGuess();
                    }
                }
            });
            wordLayout.addView(l1);

            // Create letter button
            Button l2 = new Button(this);
            l2.setText(level.getWord().charAt(i) + "");
            l2.setTextSize(24);
            l2.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT, 1f));

            // Letter button click listener
            l2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Button b = (Button) v;
                    addLetter(b.getText().toString());
                    b.setEnabled(false);
                    updateGuess();
                }
            });
            letterLayout.addView(l2, new Random().nextInt(wordLayout.getChildCount()));
        }

        // Initialize next and previous buttons
        Button nextLevel = (Button) findViewById(R.id.nextLevel);
        if (mainGlobal.getLevel() + 1 >= mainGlobal.globalData[mainGlobal.getCategory()].getWords().length) nextLevel.setEnabled(false);
        else nextLevel.setEnabled(true);
        nextLevel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainGlobal.setLevel(mainGlobal.getLevel() + 1);
                stopTimer();
                initGame();
            }
        });

        Button prevLevel = (Button) findViewById(R.id.prevLevel);
        if (mainGlobal.getLevel() == 0) prevLevel.setEnabled(false);
        else prevLevel.setEnabled(true);
        prevLevel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainGlobal.setLevel(mainGlobal.getLevel() - 1);
                stopTimer();
                initGame();
            }
        });

        // Trigger initial update
        updateGuess();
    }
}
