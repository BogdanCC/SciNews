package com.example.android.spacenews;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

public class SettingsActivity extends AppCompatActivity {
    private static EditTextPreference editTextPreference;
    private final static String LOG_TAG = SettingsActivity.class.getSimpleName();
    private final static int MAX_ARTICLES = 40;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Make the app full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
            editTextPreference = (EditTextPreference) findPreference(getString(R.string.settings_min_article_shown_key));
            bindPreferenceSummaryToValue(editTextPreference);
            bindPreferenceSummaryToValue(findPreference(getString(R.string.settings_order_by_key)));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            // Check if the articles number preference was greater than 40
            // If it was, change it to 40 (max value)
            if(preference == editTextPreference){
                Log.i(LOG_TAG, "It was editTextPreference. Value is " + newValue.toString());
                if(Integer.valueOf(newValue.toString()) > MAX_ARTICLES){
                    Log.i(LOG_TAG, "It was greater than 40");
                    newValue = String.valueOf(MAX_ARTICLES);
                    Log.i(LOG_TAG, "Value now is" + newValue.toString());
                }
            }
            String stringValue = newValue.toString();
            // If the preference is a ListPreference
            if(preference instanceof ListPreference){
                // Get the view
                ListPreference listPreference = (ListPreference) preference;
                // Get the index of the value chosen
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if(prefIndex >= 0) {
                    // Get the list entries
                    CharSequence[] labels = listPreference.getEntries();
                    // Set the label at this index as summary
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }
        /** This method ensures that the summary is displayed when activity is created
         *  by getting the SharedPreferences values when activity is created */
        private void bindPreferenceSummaryToValue(Preference preference){
            // Set the onPreferenceChange listener from this class
            preference.setOnPreferenceChangeListener(this);
            // Get the shared preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            // Get the preference value for this preference using its key
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }

    }
}
