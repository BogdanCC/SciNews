package com.example.android.spacenews;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
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
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.List;

public class LiveNewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>>{

    private static final String THEGUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search?tag=science/space&show-fields=trailText%2Cheadline%2Cthumbnail%2Cwordcount&show-tags=contributor&api-key=test";
    private View fragmentLayout;
    private static final int NEWS_LOADER_ID = 1;
    ProgressBar newsProgerssBar;
    public LiveNewsFragment(){
        // required public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FrameLayout emptyView = fragmentLayout.findViewById(R.id.empty_view);
        newsProgerssBar = fragmentLayout.findViewById(R.id.news_progress);
        if(isConnected()) {
            emptyView.setVisibility(View.GONE);
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // TODO : Choose empty layout if no internet connection
            newsProgerssBar.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.fragment_live_news, container, false);
        newsProgerssBar = fragmentLayout.findViewById(R.id.news_progress);
        return fragmentLayout;
    }
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // Create a new {@link NewsLoader} instance and pass in the context
        // and the string url to be used in the background
        return new NewsLoader(getActivity(), THEGUARDIAN_REQUEST_URL, true);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        updateUi(news);
        newsProgerssBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
    }

    /** Method to return true if connected to the internet and false otherwise */
    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /** Method tu update the UI in the onLoadFinished method */
    private void updateUi(List<News> news){
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView mRecyclerView = fragmentLayout.findViewById(R.id.news_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Set the custom adapter to the RecyclerView
        RecyclerView.Adapter mAdapter = new NewsAdapter(news);
        mRecyclerView.setAdapter(mAdapter);
    }
}
