package com.example.android.spacenews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class LiveNewsFragment extends android.support.v4.app.Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<News>>{

    private static final String THEGUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search?q=space&tag=science/science&show-fields=trailText&api-key=test";
    private View fragmentLayout;
    private static final int NEWS_LOADER_ID = 1;

    public LiveNewsFragment(){
        // required public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        android.support.v4.app.LoaderManager loaderManager;
        if(isConnected()) {
            loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // TODO : Choose empty layout if no internet connection
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.fragment_live_news, container, false);

        return fragmentLayout;
    }
    @Override
    public android.support.v4.content.Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // Create a new {@link NewsLoader} instance and pass in the context
        // and the string url to be used in the background
        return new NewsLoader(getContext(), THEGUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<News>> loader, List<News> news) {
        updateUi(news);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<News>> loader) {
    }

    /** Method to return true if connected to the internet and false otherwise */
    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /** Method tu update the UI in the onLoadFinished method */
    private void updateUi(List<News> news){
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView mRecyclerView = fragmentLayout.findViewById(R.id.news_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Set the custom adapter to the RecyclerView
        RecyclerView.Adapter mAdapter = new NewsAdapter(news);
        mRecyclerView.setAdapter(mAdapter);
    }
}
