package ua.edu.universityprograms.app.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import ua.edu.universityprograms.app.models.User;

/**
 * Created by vcaciuc on 7/10/2014.
 */
public class PreferencesUtils {

    /**
     * Gets user from SharedPreferences
     * @param preferences
     * @param cxt
     * @return User
     */
    public static User getUser(SharedPreferences preferences, Context cxt){
        preferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        String user = preferences.getString(UpConstants.USER_KEY, "");
        return new Gson().fromJson(user,User.class);
    }
}
