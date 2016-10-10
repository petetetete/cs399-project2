package com.example.kaina.anagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CategoriesActivity extends AppCompatActivity {

    private int[] categories = {R.id.foodsCategory, R.id.animalsCategory};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back navigation

        initNavigationListeners();
    }

    private void initNavigationListeners() {
        // Create a generic listener to be applied to all levels
        View.OnClickListener listen = new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                Button button = (Button) e;

                // Create intent and bundle to be passed to levels activity
                Intent i = new Intent(getApplicationContext(), LevelActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("category", button.getText().toString());
                i.putExtras(bundle);
                startActivity(i);
            }
        };

        // Iterate through all levels and apply listener
        for (int id : categories) findViewById(id).setOnClickListener(listen);
    }
}
