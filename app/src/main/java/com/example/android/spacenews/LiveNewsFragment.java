package com.example.android.spacenews;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class LiveNewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>>{

    private static final String THEGUARDIAN_REQUEST_URL = "http://content.guardianapis.com/search?";
    private View fragmentLayout;
    private int news_loader_id = 1;
    private ProgressBar newsProgerssBar;
    private EditText searchBox;
    private ImageView searchButton;
    private RecyclerView mRecyclerView;
    private ImageView notFoundImage;
    private TextView notFoundText;
    private FrameLayout emptyView;
    private RecyclerView.Adapter mAdapter;
    private RelativeLayout searchField;
    public LiveNewsFragment(){
        // required public constructor
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Initialise layout when activity is created
        activityCreatedScreen();
        // Display layout for connected or disconnected internet
        if(isConnected()) {
            if(searchField.getVisibility() == View.GONE) {
                searchField.setVisibility(View.VISIBLE);
            }
            // Initiate the loader when search button is pressed
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showLoadingScreen();
                    if(!isConnected()) {
                        showErrorScreen(false);
                    } else {
                        news_loader_id++;
                        LoaderManager loaderManager = getLoaderManager();
                        loaderManager.initLoader(news_loader_id, null, LiveNewsFragment.this);
                    }
                }
            });
        } else {
            showErrorScreen(false);
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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // Create query string to concatenate the url string from the EditText
        // Replace white spaces with %20AND%20
        String query = searchBox.getText().toString();

        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String minArticles = sharedPreferences.getString(
                getString(R.string.settings_min_article_shown_key),
                getString(R.string.settings_min_article_shown_default_value));

        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default_value)
        );
        boolean includesScience = sharedPreferences.getBoolean(
                getString(R.string.settings_sections_science_key), true);
        boolean includesTechnology = sharedPreferences.getBoolean(
                getString(R.string.settings_sections_technology_key), true);
        boolean includesArt = sharedPreferences.getBoolean(
                getString(R.string.settings_sections_art_key), true);
        boolean includesBooks = sharedPreferences.getBoolean(
                getString(R.string.settings_sections_books_key), true);
        boolean includesCulture = sharedPreferences.getBoolean(
                getString(R.string.settings_sections_culture_key), true);
        boolean includesEducation = sharedPreferences.getBoolean(
                getString(R.string.settings_sections_education_key), true);
        boolean includesEnvironment = sharedPreferences.getBoolean(
                getString(R.string.settings_sections_environment_key), true);
        boolean includesSports = sharedPreferences.getBoolean(
                getString(R.string.settings_sections_sports_key), true);
        String sections = "";
        if(includesScience){
            sections += "science|";
        }
        if(includesTechnology){
            sections += "technology|";
        }
        if(includesArt){
            sections += "art|";
        }
        if(includesBooks){
            sections += "books|";
        }
        if(includesCulture){
            sections += "culture|";
        }
        if(includesEducation){
            sections += "education|";
        }
        if(includesEnvironment){
            sections += "environment|";
        }
        if(includesSports){
            sections += "sport|";
        }
        if(sections.endsWith("|")){
            sections = sections.substring(0, sections.length()-1);
        }
        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(THEGUARDIAN_REQUEST_URL);
        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameters and its value
        uriBuilder.appendQueryParameter("q", query);
        uriBuilder.appendQueryParameter("section", sections);
        uriBuilder.appendQueryParameter("show-fields", "trailText,headline,thumbnail,wordcount");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("page-size", minArticles);
        uriBuilder.appendQueryParameter("api-key", "test");
        // Create a new {@link NewsLoader} instance and pass in the context
        // and the string url to be used in the background
        return new NewsLoader(getActivity(), uriBuilder.toString(), true);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        updateUi(news, loader);
        newsProgerssBar.setVisibility(View.GONE);
        if(news.size() == 0 || loader.isAbandoned()) {
            showErrorScreen(true);
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
    // Method to show the loading screen
    private void showLoadingScreen(){
        mRecyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        newsProgerssBar.setVisibility(View.VISIBLE);
    }
    // Method to show error screen if connected or not to the internet
    private void showErrorScreen(boolean isConnected){
        if(isConnected){
            mRecyclerView.setVisibility(View.GONE);
            newsProgerssBar.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else{
            newsProgerssBar.setVisibility(View.GONE);
            searchField.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            notFoundImage.setImageResource(R.drawable.no_connection);
            notFoundText.setText(getResources().getString(R.string.no_internet));
        }
    }
    private void activityCreatedScreen(){
        // Setting UI on activity created
        emptyView = fragmentLayout.findViewById(R.id.empty_view);
        notFoundImage = fragmentLayout.findViewById(R.id.not_found_image);
        notFoundText = fragmentLayout.findViewById(R.id.not_found_text);
        newsProgerssBar = fragmentLayout.findViewById(R.id.news_progress);
        searchBox = fragmentLayout.findViewById(R.id.search_box);
        searchButton = fragmentLayout.findViewById(R.id.search_button);
        mRecyclerView = fragmentLayout.findViewById(R.id.news_view);
        searchField = fragmentLayout.findViewById(R.id.search_field);
        emptyView.setVisibility(View.VISIBLE);
        notFoundImage.setImageResource(R.drawable.you_can_search);
        notFoundText.setText(getResources().getString(R.string.make_search));
        newsProgerssBar.setVisibility(View.GONE);
    }
}
