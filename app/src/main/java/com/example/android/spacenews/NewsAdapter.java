package com.example.android.spacenews;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        // Get the root view and the current article
        final View rootView = holder.newsListItem;
        News currentArticle = newsList.get(position);
        String date = Utils.removeSubStringAt(currentArticle.getDate(), "T");
        String author = currentArticle.getAuthor();
        String publishedOn;
        // If the author is unknown, just show the Published date
        if(author.equals("Unknown author")){
            publishedOn = rootView.getResources().getString(R.string.published) + " " + date;
        } else {
            publishedOn = rootView.getResources().getString(R.string.published) + " " + date
                    + " " + rootView.getResources().getString(R.string.published_by)
                    + " " + currentArticle.getAuthor();
        }
        // Get the views that we want to populate
        TextView articleTitle = rootView.findViewById(R.id.article_title);
        TextView articleDesc = rootView.findViewById(R.id.article_desc);
        TextView articleReadTime = rootView.findViewById(R.id.article_read_time);
        TextView articleDate = rootView.findViewById(R.id.article_date);
        TextView articleReadButton = rootView.findViewById(R.id.article_open_link);
        ImageView articleThumbnail = rootView.findViewById(R.id.article_thumbnail);
        // Set the data on those views
        articleTitle.setText(currentArticle.getTitle());
        articleDesc.setText(currentArticle.getDescription());
        articleReadTime.setText(Utils.calculateReadTime(currentArticle.getWordCount()));
        articleDate.setText(publishedOn);
        articleThumbnail.setImageBitmap(currentArticle.getImageBitmap());
        // Set a click listener on the read button
        final Intent i = new Intent(Intent.ACTION_VIEW);
        final String articleLink = currentArticle.getArticleUrl();
        articleReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.setData(Uri.parse(articleLink));
                rootView.getContext().startActivity(i);
            }
        });
    }
    /** Return the size of your dataset (invoked by the layout manager) */
    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
