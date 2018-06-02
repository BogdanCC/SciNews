package com.example.android.spacenews;

public class News {
    private String mAuthor;
    private String mTitle;
    private String mDescription;

    public News(String author, String title, String description) {
        mAuthor = author;
        mTitle = title;
        mDescription = description;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}
