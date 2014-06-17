package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.Utils.UpConstants;
import ua.edu.universityprograms.app.models.User;

public class Settings extends Activity {

    @InjectView(R.id.etFirstName)
    EditText etFName;
    @InjectView(R.id.etLastName)
    EditText etLName;
    @InjectView(R.id.etEmail)
    EditText etEmail;
    @InjectView(R.id.etCwid)
    EditText etCwid;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String user = preferences.getString(UpConstants.USER_KEY, "");
        User you = new Gson().fromJson(user,User.class);
        try {
            if (!you.uFirstName.equalsIgnoreCase(""))
                etFName.setText(you.uFirstName);
            if (!you.uLastName.equalsIgnoreCase(""))
                etLName.setText(you.uLastName);
            if (!you.uEmail.equalsIgnoreCase(""))
                etEmail.setText(you.uEmail);
            if (!you.uCwid.equalsIgnoreCase(""))
                etCwid.setText(you.uCwid);
        }catch(NullPointerException e){
            // Do nothing
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_save) {
            User saveMe = new User(etFName.getText().toString(),etLName.getText().toString(), etEmail.getText().toString(),etCwid.getText().toString());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(UpConstants.USER_KEY, new Gson().toJson(saveMe));
            editor.commit();
            Toast.makeText(Settings.this, "Saved", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
