package com.example.kaina.anagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LevelActivity extends AppCompatActivity {

    private int[] levels = {R.id.levelButton0, R.id.levelButton1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back navigation

        // Get data passed into activity
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();

        // Set current category text by data passed in
        TextView currCategory = (TextView) findViewById(R.id.currCategory);
        if (extrasBundle != null) currCategory.setText(extrasBundle.getString("category"));
        else currCategory.setVisibility(View.INVISIBLE);

        initNavigationListeners();
    }

    private void initNavigationListeners() {

        // Create a generic listener to be applied to all levels
        View.OnClickListener listen = new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(i);
            }
        };

        // Iterate through all levels and apply listener
        for (int id : levels) findViewById(id).setOnClickListener(listen);
    }
}
