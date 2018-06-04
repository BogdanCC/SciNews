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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class LiveNewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>>{

    private String THEGUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search?section=science&show-fields=trailText%2Cheadline%2Cthumbnail%2Cwordcount&show-tags=contributor&api-key=test";
    private View fragmentLayout;
    private int news_loader_id = 1;
    ProgressBar newsProgerssBar;
    EditText searchBox;
    ImageView searchButton;
    private RecyclerView mRecyclerView;
    private ImageView notFoundImage;
    private TextView notFoundText;
    private FrameLayout emptyView;
    RecyclerView.Adapter mAdapter;

    public LiveNewsFragment(){
        // required public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        // Setting UI on activity created
        emptyView = fragmentLayout.findViewById(R.id.empty_view);
        notFoundImage = fragmentLayout.findViewById(R.id.not_found_image);
        notFoundText = fragmentLayout.findViewById(R.id.not_found_text);
        newsProgerssBar = fragmentLayout.findViewById(R.id.news_progress);
        searchBox = fragmentLayout.findViewById(R.id.search_box);
        searchButton = fragmentLayout.findViewById(R.id.search_button);
        mRecyclerView = fragmentLayout.findViewById(R.id.news_view);
        LinearLayout searchField = fragmentLayout.findViewById(R.id.search_field);
        emptyView.setVisibility(View.VISIBLE);
        notFoundImage.setImageResource(R.drawable.no_umbrella_found);
        notFoundText.setText(getResources().getString(R.string.make_search));
        newsProgerssBar.setVisibility(View.GONE);
        if(isConnected()) {
            if(searchField.getVisibility() == View.GONE) {
                searchField.setVisibility(View.VISIBLE);
            }
            // Initiate the loader when search button is pressed
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecyclerView.setVisibility(View.GONE);
                    // Create query string to concatenate the url string from the EditText
                    // Replace white spaces with %20AND%20
                    String query = searchBox.getText().toString().replace(" ", "%20AND%20");
                    THEGUARDIAN_REQUEST_URL = "http://content.guardianapis.com/search?q=" + query + "&section=science&show-fields=trailText%2Cheadline%2Cthumbnail%2Cwordcount&show-tags=contributor&api-key=test";
                    news_loader_id++;
                    LoaderManager loaderManager = getLoaderManager();
                    loaderManager.initLoader(news_loader_id, null, LiveNewsFragment.this);
                    emptyView.setVisibility(View.GONE);
                    newsProgerssBar.setVisibility(View.VISIBLE);
                }
            });


        } else {
            // TODO : Choose empty layout if no internet connection
            newsProgerssBar.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            notFoundImage.setImageResource(R.drawable.no_connection);
            notFoundText.setText(getResources().getString(R.string.no_internet));
            searchField.setVisibility(View.GONE);
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
        updateUi(news, loader);
        newsProgerssBar.setVisibility(View.GONE);
        if(news.size() == 0 || loader.isAbandoned()) {
            emptyView.setVisibility(View.VISIBLE);
            newsProgerssBar.setVisibility(View.GONE);
        }
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
    private void updateUi(List<News> news, Loader<List<News>> loader){
        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView = fragmentLayout.findViewById(R.id.news_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Set the custom adapter to the RecyclerView
        mAdapter = new NewsAdapter(news);
        mRecyclerView.setAdapter(mAdapter);
        if(mRecyclerView.getVisibility() == View.GONE) {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        if(news.size() == 0 || loader.isAbandoned()){
            notFoundImage.setImageResource(R.drawable.no_umbrella_found);
            notFoundText.setText(getResources().getString(R.string.no_data));
        }
    }
}
