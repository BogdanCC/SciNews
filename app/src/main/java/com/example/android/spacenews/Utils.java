package com.example.android.spacenews;

import java.util.Locale;

public final class Utils {
    private final static int WORDS_PER_SECOND = 2;
    private final static int SECONDS_PER_MINUTE = 60;
    private final static int HALF_A_MINUTE = 30;
    private Utils() {
    }
    /** Method to remove a part of a string from a certain point
     * @param  removeString  removes the string starting at that point
     * @return output[0]     split() splits the string in 2,
     *                       we return the first part of the string
     * */
    public static String removeSubStringAt(String mainString, String removeString) {
        String[] output = mainString.split(removeString);
        return output[0];
    }
    /** Method to calculate the read time of an article given the number of words in that article
     * We assume the reader reads 2 words per second
     * @param wordCount - the number of words
     * @return          a formatted String that shows the read time in minutes */
    public static String calculateReadTime(int wordCount){
        int readTimeSeconds = wordCount / WORDS_PER_SECOND;
        int minutes = readTimeSeconds / SECONDS_PER_MINUTE;
        int seconds = readTimeSeconds % SECONDS_PER_MINUTE; // returns the remainder of number of seconds / 60
        if(seconds > HALF_A_MINUTE) {
            minutes++;
        } else if(minutes == 0) {
            minutes++;
        }
        return String.format(Locale.getDefault(),"%d min read", minutes);
    }
}
