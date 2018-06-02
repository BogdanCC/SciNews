package com.example.android.spacenews;

import android.content.Context;

import java.util.List;

public class NewsLoader extends android.support.v4.content.AsyncTaskLoader<List<News>> {

    private String mUrlLink;

    public NewsLoader(Context context, String link){
        super(context);
        mUrlLink = link;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        // Create a reference to a list of {@link News} objects
        List<News> news;
        // Make an http request and return the data in form of a JSON String
        String jsonResponse = QueryUtils.makeHttpRequest(QueryUtils.createUrl(mUrlLink));
        // Add in the list of {@link News} objects from the JSON response
        news = QueryUtils.extractNews(jsonResponse);

        return news;
    }
}
