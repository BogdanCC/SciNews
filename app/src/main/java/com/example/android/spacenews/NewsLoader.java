package com.example.android.spacenews;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.Nullable;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrlLink;
    private List<News> news;

    public NewsLoader(Context context, String link){
        super(context);
        mUrlLink = link;
    }

    @Override
    protected void onStartLoading() {
        if(news != null) {
            deliverResult(news);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<News> loadInBackground() {
        // Make an http request and return the data in form of a JSON String
        String jsonResponse = QueryUtils.makeHttpRequest(QueryUtils.createUrl(mUrlLink));
        // Add in the list of {@link News} objects from the JSON response
        news = QueryUtils.extractNews(jsonResponse);

        return news;
    }

    @Override
    public void deliverResult(@Nullable List<News> data) {
        news = data;
        super.deliverResult(data);
    }
}
