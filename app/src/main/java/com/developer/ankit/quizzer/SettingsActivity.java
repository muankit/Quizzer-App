package com.developer.ankit.quizzer;

import android.os.Bundle;
import android.preference.PreferenceFragment;


public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsPrefActivity.MainPreferenceFragment()).commit();
    }

    public static class SettingsPrefActivity extends AppCompatPreferenceActivity {
        private static final String TAG = SettingsPrefActivity.class.getSimpleName();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // load settings fragment
            getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
        }

        public static class MainPreferenceFragment extends PreferenceFragment {
            @Override
            public void onCreate(final Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                addPreferencesFromResource(R.xml.pref_main);

            }
        }
    }
}
