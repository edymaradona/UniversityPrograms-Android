package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;

import ua.edu.universityprograms.app.R;

/**
 * Created by vcaciuc on 6/3/2014.
 */
public class WhatWeDo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(PreferenceManager.getDefaultSharedPreferences(this).getInt("theme", android.R.style.Theme_Holo));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.whatwedo);
        ActionBarRefresher();
    }
    
    public void ActionBarRefresher(){
        getActionBar().setTitle(R.string.title_whatwedo);
    }
}
