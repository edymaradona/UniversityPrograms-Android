package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;

import ua.edu.universityprograms.app.R;

/**
 * Created by vcaciuc on 6/3/2014.
 */
public class WhoWeAre extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(PreferenceManager.getDefaultSharedPreferences(this).getInt("theme", android.R.style.Theme_Holo));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.whoweare);
        ActionBarRefresher();
    }
    public void ActionBarRefresher(){
        getActionBar().setTitle(R.string.title_whoweare);
    }
}
