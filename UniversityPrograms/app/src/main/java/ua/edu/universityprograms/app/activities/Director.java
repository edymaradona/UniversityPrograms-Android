package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.models.Members;

public class Director extends Activity {
    @InjectView(R.id.tvMemberName)
    TextView tvName;
    @InjectView(R.id.tvMemberInfo)
    TextView tvInfo;
    @InjectView(R.id.ivMemberProfile)
    CircleImageView ivProfile;
    @InjectView(R.id.bEmail)
    ImageButton email;
    @InjectView(R.id.bCall)
    ImageButton call;

    /**
     * Sets on click listener for the "Call" button and "Email" button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(PreferenceManager.getDefaultSharedPreferences(this).getInt("theme", android.R.style.Theme_Holo));
        super.onCreate(savedInstanceState);
        ActionBarRefresher();
        overridePendingTransition(R.anim.slide_in_left, R.anim.abc_fade_out);
        setContentView(R.layout.activity_dir);
        ButterKnife.inject(this);
        Members member = getIntent().getParcelableExtra("memb");
        tvName.setText(member.name);
        tvInfo.setText(member.info);
        final int i = getIntent().getIntExtra("position", -1);
        Picasso.with(this).load(member.url).placeholder(R.drawable.applogo_35splash).error(R.drawable.applogo_35splash).noFade().into(ivProfile);
        final String[] txtPhn = new String[]{"2053489958", "2053487525"};
        final String[] recipients = new String[]{"scott060@sa.ua.edu", "kmjones13@sa.ua.edu"};
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + txtPhn[i]));
                startActivity(callIntent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",recipients[i], null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });
    }

    // Sets the Title for this page
    public void ActionBarRefresher() {
        getActionBar().setTitle("Director");
    }

    // Animations for exiting the page
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition( R.anim.abc_fade_in, R.anim.translucent_exit);
    }
}
