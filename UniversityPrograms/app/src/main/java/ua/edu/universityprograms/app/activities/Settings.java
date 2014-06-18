package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

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
    @Email(order = 1, message = "Must be valid email.")
    @InjectView(R.id.etEmail)
    EditText etEmail;
    @TextRule(order = 2, minLength = 8, maxLength = 8, message = "Must be a valid CWID")
    @InjectView(R.id.etCwid)
    EditText etCwid;
    @InjectView(R.id.swTheme)
    Switch theme;

    Validator validator;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(PreferenceManager.getDefaultSharedPreferences(this).getInt("theme", android.R.style.Theme_Holo));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                User saveMe = new User(etFName.getText().toString(), etLName.getText().toString(), etEmail.getText().toString(), etCwid.getText().toString());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(UpConstants.USER_KEY, new Gson().toJson(saveMe));
                editor.commit();
                Toast.makeText(Settings.this, "Saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValidationFailed(View failedView, Rule<?> failedRule) {
                String message = failedRule.getFailureMessage();

                if (failedView instanceof EditText) {
                    failedView.requestFocus();
                    ((EditText) failedView).setError(message);
                } else {
                    Toast.makeText(Settings.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String user = preferences.getString(UpConstants.USER_KEY, "");
        User you = new Gson().fromJson(user, User.class);
        try {
            etFName.setText(you.uFirstName);
            etLName.setText(you.uLastName);
            etEmail.setText(you.uEmail);
            etCwid.setText(you.uCwid);
        } catch (NullPointerException e) {
            // Do nothing
        }
        theme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                SharedPreferences.Editor editor = preferences.edit();
                if(isChecked){
                    editor.putInt("theme", android.R.style.Theme_Holo);
                }else{
                    editor.putInt("theme", android.R.style.Theme_Holo_Light);
                }
                editor.commit();
            }
        });
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
            validator.validate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
