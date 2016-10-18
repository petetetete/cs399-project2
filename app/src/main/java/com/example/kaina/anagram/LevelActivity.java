package com.example.kaina.anagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LevelActivity extends AppCompatActivity {

    private MainGlobal mainGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back navigation
        mainGlobal = ((MainGlobal) this.getApplication());

        // Set current category text by data passed in
        TextView currCategory = (TextView) findViewById(R.id.currCategory);
        currCategory.setText(mainGlobal.globalData[mainGlobal.getCategory()].getTitle());

        initButtons();
    }

    @Override
    protected void onResume() {
        initButtons();
        super.onResume();
    }

    private void initButtons() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.buttonLayout);
        ll.removeAllViews();
        for (int i = 0; i < mainGlobal.globalData[mainGlobal.getCategory()].getWords().length; i++) {

            // Save variables for future use
            Button nb = new Button(this);
            final int levelIndex = i;
            String time = mainGlobal.globalData[mainGlobal.getCategory()].getWords()[i].getTimeString();

            if (mainGlobal.globalData[mainGlobal.getCategory()].getWords()[i].isCompleted()) {
                nb.setText(mainGlobal.globalData[mainGlobal.getCategory()].getWords()[i].getWord() + " - " + time);
                nb.setEnabled(false);
            }
            else nb.setText("Level " + (i+1) + " - " + time); // Set button text

            nb.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mainGlobal.setLevel(levelIndex);

                    // Create intent to navigate to game activity
                    Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                    startActivity(intent);
                }
            });

            // Change button properties
            ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(nb, lp);
        }
    }
}
