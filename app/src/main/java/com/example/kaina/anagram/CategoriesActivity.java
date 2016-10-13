package com.example.kaina.anagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.logging.Level;

public class CategoriesActivity extends AppCompatActivity {

    private int[] categories = {R.id.foodsCategory, R.id.animalsCategory};
    private MainGlobal mainGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back navigation

        this.mainGlobal =  ((MainGlobal) this.getApplication());

        initNavigationListeners();
    }

    private void initButtons() {
        for (int i = 0; i < mainGlobal.globalData.length; i++) {
            Button nb = new Button(this);
            nb.setText(mainGlobal.globalData[i].getTitle());

            nb.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), LevelActivity.class);
                    startActivity(i);
                }
            });

            LinearLayout ll = (LinearLayout) findViewById(R.id.buttonLayout);
            ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(nb, lp);
        }
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
