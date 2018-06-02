package com.example.android.spacenews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> newsList;

    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     * */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View newsListItem;
        public ViewHolder(View v) {
            super(v);
            newsListItem = v;
        }
    }

    /** Provide a suitable constructor (depends on the kind of dataset - A List here) */
    public NewsAdapter(List<News> news){
        newsList = news;
    }

    /** Create new views (invoked by the layout manager) */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /** Replace the contents of a view (invoked by the layout manager) */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the root view and the current article
        View rootView = holder.newsListItem;
        News currentArticle = newsList.get(position);

        TextView articleTitle = rootView.findViewById(R.id.article_title);
        articleTitle.setText(currentArticle.getTitle());
    }

    /** Return the size of your dataset (invoked by the layout manager) */
    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
