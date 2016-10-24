package com.example.kaina.anagram;

import android.app.Application;

public class MainGlobal extends Application {

    public Category[] globalData = new Category[7]; // Number of categories
    public int currCategory = 0;
    public int currLevel = 0;

    public MainGlobal() {
        // Declare categories and levels
        globalData[0] = new Category("Foods", new Level[]{
                new Level("ham"),
                new Level("apple"),
                new Level("bread"),
                new Level("juice"),
                new Level("pasta"),
                new Level("carrot"),
                new Level("chicken")
        });
        globalData[1] = new Category("Animals", new Level[]{
                new Level("dog"),
                new Level("bird"),
                new Level("bear"),
                new Level("worm"),
                new Level("snake")
        });
        globalData[2] = new Category("Colors", new Level[]{
                new Level("red"),
                new Level("blue"),
                new Level("pink"),
                new Level("green"),
                new Level("white")
        });
        globalData[3] = new Category("German Words", new Level[]{
                new Level("hund"),
                new Level("drei"),
                new Level("apfel"),
                new Level("gurke"),
                new Level("katze")
        });
        globalData[4] = new Category("Kitchen", new Level[]{
                new Level("pot"),
                new Level("fork"),
                new Level("wisk"),
                new Level("knife"),
                new Level("spoon")
        });
        globalData[5] = new Category("US States", new Level[]{
                new Level("texas"),
                new Level("maine"),
                new Level("kansas"),
                new Level("hawaii"),
                new Level("arizona"),
                new Level("wyoming"),
                new Level("virginia")
        });
        globalData[6] = new Category("Countries", new Level[]{
                new Level("cuba"),
                new Level("chile"),
                new Level("haiti"),
                new Level("canada"),
                new Level("finland"),
                new Level("iceland"),
                new Level("zimbabwe")
        });
    }

    public void setCategory(int index) { currCategory = index; }
    public void setLevel(int index) { currLevel = index; }

    public int getCategory() { return currCategory; }
    public int getLevelIndex() { return currLevel; }

    public Category[] getCategories() {
        return this.globalData;
    }
    public Category getCurrentCategory() {
        return this.globalData[this.currCategory];
    }
    public Level getCurrentLevel() {
        return this.globalData[this.currCategory].getWords()[this.currLevel];
    }
}

// Class declaring the category object with custom methods and such
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

// Class declaring the level object
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