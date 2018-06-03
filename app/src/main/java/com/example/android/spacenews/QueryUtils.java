package com.example.android.spacenews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    /** Creating a private constructor before noone should create an instance
     * of the {@link QueryUtils} class since all the methods will be static */
    private QueryUtils(){}

    /** Method to make an http request that will return a json string response
     * @param   url - the url object we'll use to make the connection
     * @return  a String that contains the whole JSON response from the server
     * */
    public static String makeHttpRequest(URL url) {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            // Give 10 seconds time for tha data to be available for read
            // If the timeout expires we will jump to the catch and finally block
            urlConnection.setReadTimeout(10000);
            // Same as above, give a max of 15 seconds for the connection to be established
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            // Get the input stream returned from the server
            inputStream = urlConnection.getInputStream();
            // Convert the input stream into a String
            jsonResponse = convertStreamToString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return jsonResponse;
    }

    /**
     * Method to convert an input stream (byte chunks) into a String object
     * @param   is - the response from the server in form of binary code
     * @return  the response from the server converted into a String (the json response)
     * */
    private static String convertStreamToString(InputStream is) {
        // Creating a BufferedReader to wrap the InputStreamReader
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        // Creating a StringBuilder to build the JSON String
        // StringBuilder is preferred to String because it's mutable, while a String object is not
        // Meaning when we concatenate more text to a String, a new String object will always be created
        StringBuilder jsonStringBuilder = new StringBuilder();
        String line;
        try {
            while((line = bufferedReader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonStringBuilder.toString();
    }

    /**
     * Method to create an {@link URL} object from a String link
     * @param   stringUrl - the query link in form of a String
     * @return  the url object created from the link
     * */
    public static URL createUrl(String stringUrl){
        URL url;
        try{
            url = new URL(stringUrl);
        } catch(MalformedURLException e){
            e.printStackTrace();
            return null;
        }
        return url;
    }

    /**
     * Return a list of {@link News} objects that has been built up
     * from parsing the JSON response.
     * @param   newsJSON - the whole JSON string that we got back from the server
     * @return  a list of fetched {@link News} objects from the JSON String
     * */
    public static List<News> extractNews(String newsJSON, boolean isFromNetwork, Context context) {
        List<News> news = new ArrayList<>();
        List<FeaturedArticleThumbnails> thumbnails = new ArrayList<>();
        if(!isFromNetwork){
            Log.i("QueryUtils", "DEBUG : Image resource id = " + R.drawable.thumb_1);
            thumbnails.add(new FeaturedArticleThumbnails(R.drawable.thumb_1));
            thumbnails.add(new FeaturedArticleThumbnails(R.drawable.thumb2));
            thumbnails.add(new FeaturedArticleThumbnails(R.drawable.thumb3));
            thumbnails.add(new FeaturedArticleThumbnails(R.drawable.thumb4));
            thumbnails.add(new FeaturedArticleThumbnails(R.drawable.thumb5));
            thumbnails.add(new FeaturedArticleThumbnails(R.drawable.thumb6));
            thumbnails.add(new FeaturedArticleThumbnails(R.drawable.thumb7));
            thumbnails.add(new FeaturedArticleThumbnails(R.drawable.thumb8));
            thumbnails.add(new FeaturedArticleThumbnails(R.drawable.thumb9));
            thumbnails.add(new FeaturedArticleThumbnails(R.drawable.thumb10));
        }
        // Creating reference variables to hold the news data
        String title;
        String desc;
        String author;
        int wordCount;
        String date;
        String articleUrl;
        String imageUrl;
        Bitmap bmp;
        // Try to parse the newsJSON. If there's a problem with the way the JSON
        // is formatted, a JSONException will be thrown
        // Catch the exception so the app doesn't crash, and print the error message to the logs
        try {
            // Parse the response given by the newsJSON string and
            // build up a list of News objects with the corresponding data.
            JSONObject root = new JSONObject(newsJSON);
            JSONObject response = root.getJSONObject("response");
            JSONArray newsArray = response.getJSONArray("results");

            int length = newsArray.length();
            for(int i = 0; i < length; i++) {

                JSONObject article = newsArray.getJSONObject(i);
                JSONObject fields = article.getJSONObject("fields");
                JSONArray tags = article.getJSONArray("tags");
                title = fields.getString("headline");
                desc = fields.getString("trailText");
                wordCount = fields.getInt("wordcount");
                date = article.getString("webPublicationDate");
                articleUrl = article.getString("webUrl");
                if(isFromNetwork) {
                    imageUrl = fields.getString("thumbnail");
                    bmp = getUrlArticleImage(imageUrl);
                } else {
                    Log.i("QueryUtils", "DEBUG : BitmapFactory decoding. getImageResourceId() : " + thumbnails.get(1).getImageResourceId());
                    bmp = BitmapFactory.decodeResource(context.getResources(), thumbnails.get(i).getImageResourceId());
                }
               if(tags.length() != 0 && tags.getJSONObject(0).has("webTitle")){
                   author = tags.getJSONObject(0).getString("webTitle");
               } else {
                   author = "Unknown author";
               }

               desc = android.text.Html.fromHtml(desc).toString();

                Log.i("QueryUtils", "DEBUG : bmp is now " + bmp);
                news.add(new News(author, title, desc, wordCount, date, articleUrl, bmp));
            }
        } catch (JSONException e) {
            Log.e("Query Utils", "Problem parsing the JSON response");
            e.printStackTrace();
        }
        return news;
    }
    private static Bitmap getUrlArticleImage(String imageUrl){
        URL url = null;
        Bitmap bmp = null;
        try{
            url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
             bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
