package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;

import ua.edu.universityprograms.app.R;

/**
 * Created by vcaciuc on 6/3/2014.
 */
public class WhoWeAre extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(PreferenceManager.getDefaultSharedPreferences(this).getInt("theme", android.R.style.Theme_Holo));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whoweare);
        overridePendingTransition(R.anim.slide_in_left, R.anim.abc_fade_out);
        ActionBarRefresher();
    }

    // Sets the Title for this page
    public void ActionBarRefresher(){
        getActionBar().setTitle(R.string.title_whoweare);
    }
}
