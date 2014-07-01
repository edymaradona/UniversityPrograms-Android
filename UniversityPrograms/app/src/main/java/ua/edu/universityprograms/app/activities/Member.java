package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.models.Members;

public class Member extends Activity {

    @InjectView(R.id.tvMemberName)
    TextView tvName;
    @InjectView(R.id.tvMemberAbout)
    TextView tvAbout;
    @InjectView(R.id.tvMemberInfo)
    TextView tvInfo;
    @InjectView(R.id.ivMemberProfile)
    CircleImageView ivProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(PreferenceManager.getDefaultSharedPreferences(this).getInt("theme", android.R.style.Theme_Holo));

        super.onCreate(savedInstanceState);
        String name = getIntent().getStringExtra("title");
        getActionBar().setTitle(name);
        setContentView(R.layout.activity_member);
        ButterKnife.inject(this);
        Members member = getIntent().getParcelableExtra("memb");
        tvName.setText(member.name);
        tvInfo.setText(member.info);
        tvAbout.setText(member.about);
        Picasso.with(this).load(member.url).placeholder(R.drawable.applogo_35splash).error(R.drawable.applogo_35splash).noFade().into(ivProfile);
    }


}
