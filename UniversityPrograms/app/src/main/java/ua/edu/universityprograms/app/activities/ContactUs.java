package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.R;

/**
 * Created by vcaciuc on 6/4/2014.
 */
public class ContactUs extends Activity implements View.OnClickListener {

    @InjectView(R.id.etMessage)
    EditText message;
    @InjectView(R.id.etEmail)
    EditText email;
    @InjectView(R.id.etFirst)
    EditText firstN;
    @InjectView(R.id.etLast)
    EditText lastN;
    @InjectView(R.id.bSubmit)
    Button submit;

    String msg, fname, lname, emailAdd, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        ButterKnife.inject(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        convert();
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAdd);
        emailIntent.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(emailIntent);
    }

    private void convert(){
        msg = message.getText().toString();
        fname = firstN.getText().toString();
        lname = lastN.getText().toString();
        name = fname + lname;
        emailAdd = email.getText().toString();
    }
}
