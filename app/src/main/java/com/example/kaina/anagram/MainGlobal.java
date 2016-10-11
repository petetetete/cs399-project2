package com.example.kaina.anagram;

import android.app.Application;

public class MainGlobal extends Application {

    public Category[] globalData;

    public void main(String[] args) {
        // Example of how to add a new category
        this.globalData[0] = new Category("Foods", new String[]{
                "fish",
                "apple"
        });
    }

}

class Category {

    private String title;
    private String[] words;

    Category(String title, String[] words) {
        this.title = title;
        this.words = words;
    }

    public String getTitle() {
        return this.title;
    }

    public String[] getWords() {
        return this.words;
    }
}
