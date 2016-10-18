package com.example.kaina.anagram;

import android.app.Application;

public class MainGlobal extends Application {

    public Category[] globalData = new Category[5]; // Number of categories
    public int currCategory = 0;
    public int currLevel = 0;

    public MainGlobal() {
        // Example of how to add a new category
        globalData[0] = new Category("Foods", new Level[]{
                new Level("ham"),
                new Level("apple"),
                new Level("bread"),
                new Level("juice"),
                new Level("pasta")
        });
        globalData[1] = new Category("Animals", new Level[]{
                new Level("dog"),
                new Level("snake"),
                new Level("bird"),
                new Level("bear"),
                new Level("worm")
        });
        globalData[2] = new Category("Colors", new Level[]{
                new Level("blue"),
                new Level("red"),
                new Level("pink"),
                new Level("green"),
                new Level("white")
        });
        globalData[3] = new Category("German Words", new Level[]{
                new Level("apfel"),
                new Level("hund"),
                new Level("katze"),
                new Level("drei"),
                new Level("gurke")
        });
        globalData[4] = new Category("Kitchen Things", new Level[]{
                new Level("knife"),
                new Level("fork"),
                new Level("spoon"),
                new Level("pot"),
                new Level("wisk")
        });
    }

    public void setCategory(int index) { currCategory = index; }
    public void setLevel(int index) { currLevel = index; }

    public int getCategory() { return currCategory; }
    public int getLevel() { return currLevel; }
}

class Category {

    private String title;
    private Level[] words;

    Category(String title, Level[] words) {
        this.title = title;
        this.words = words;
    }

    public String getTitle() {
        return title;
    }
    public Level[] getWords() {
        return words;
    }
}

class Level {

    private String word;
    private long time;
    private boolean completed;

    Level(String word) {
        this.word = word;
        this.time = 0;
        this.completed = false;
    }

    public void setTime(long t) { time = t; }
    public void setCompleted() { completed = true; }

    public String getWord() {
        return word;
    }
    public long getTime() {
        return time;
    }
    public String getTimeString() {
        int seconds = (int) (time / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
    public boolean isCompleted() { return completed; }
}