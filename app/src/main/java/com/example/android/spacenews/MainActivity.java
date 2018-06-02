package com.example.android.spacenews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{
    // Creating global variable references
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int NEWS_LOADER_ID = 1;
    private static final String THEGUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search?q=space&tag=science/science&show-fields=trailText&api-key=test";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;
    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isConnected()) {
            loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // TODO : Choose empty layout if no internet connection
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // Create a new {@link NewsLoader} instance and pass in the context
        // and the string url to be used in the background
        return new NewsLoader(MainActivity.this, THEGUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        updateUi(news);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
    }

    /** Method to return true if connected to the internet and false otherwise */
    private boolean isConnected() {
        cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /** Method tu update the UI in the onLoadFinished method */
    private void updateUi(List<News> news){
        mRecyclerView = findViewById(R.id.news_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Set the custom adapter to the RecyclerView
        mAdapter = new NewsAdapter(news);
        mRecyclerView.setAdapter(mAdapter);
    }
}
