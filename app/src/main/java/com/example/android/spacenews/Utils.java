package com.example.android.spacenews;

import java.util.Locale;

public final class Utils {
    private Utils() {
    }
    public static String removeSubStringAt(String mainString, String removeString) {
        String[] output = mainString.split(removeString);
        return output[0];
    }
    public static String calculateReadTime(int wordCount){
        int readTimeMilliSeconds = (wordCount/2) * 1000;
        int minutes = readTimeMilliSeconds / (60 * 1000);
        int seconds = (readTimeMilliSeconds / 1000) % 60;
        if(seconds > 30) {
            minutes += 1;
        }
        return String.format(Locale.getDefault(),"%d min read", minutes);
    }
}
