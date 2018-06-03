package com.example.android.spacenews;

import android.graphics.Bitmap;

public class News {
    private String mAuthor;
    private String mTitle;
    private String mDescription;
    private int mWordCount;
    private String mDate;
    private String mArticleUrl;
    private Bitmap mImageBitmap;

    public News(String author, String title, String description, int worCount, String date, String articleUrl, Bitmap imageBitmap) {
        mAuthor = author;
        mTitle = title;
        mDescription = description;
        mWordCount = worCount;
        mDate = date;
        mArticleUrl = articleUrl;
        mImageBitmap = imageBitmap;
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

    public int getWordCount() {
        return mWordCount;
    }

    public String getDate() {
        return mDate;
    }

    public String getArticleUrl() {
        return mArticleUrl;
    }

    public Bitmap getImageBitmap() {
        return mImageBitmap;
    }
}
