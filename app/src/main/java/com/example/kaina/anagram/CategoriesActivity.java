package com.example.kaina.anagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class CategoriesActivity extends AppCompatActivity {

    private MainGlobal mainGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back navigation
        this.mainGlobal = ((MainGlobal) this.getApplication());

        initButtons();
    }

    private void initButtons() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.buttonLayout); // Save layout where buttons will be added
        for (int i = 0; i < mainGlobal.globalData.length; i++) {

            // Save variables for future use
            Button nb = new Button(this);
            final int catIndex = i;

            nb.setText(mainGlobal.globalData[i].getTitle()); // Set button text

            nb.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mainGlobal.setCategory(catIndex);

                    // Create intent to navigate to levels activity
                    Intent intent = new Intent(getApplicationContext(), LevelActivity.class);
                    startActivity(intent);
                }
            });

            // Change button properties
            ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(nb, lp);
        }
    }
}
