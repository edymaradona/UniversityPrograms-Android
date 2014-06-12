package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.models.User;

public class Settings extends Activity {

    @InjectView(R.id.etName)
    EditText etName;
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

        String name = preferences.getString("Name", "");
        String email = preferences.getString("Email", "");
        String cwid = preferences.getString("CWID", "");
        if(!name.equalsIgnoreCase(""))
            etName.setText(name);
        if(!email.equalsIgnoreCase(""))
            etEmail.setText(email);
        if(!cwid.equalsIgnoreCase(""))
            etCwid.setText(cwid);
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
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Name", etName.getText().toString());
            editor.putString("Email", etEmail.getText().toString());
            editor.putString("CWID", etCwid.getText().toString());
            editor.commit();
            Toast.makeText(Settings.this, "Saved", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
