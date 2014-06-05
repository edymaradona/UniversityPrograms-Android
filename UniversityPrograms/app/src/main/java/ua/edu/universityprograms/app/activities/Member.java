package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.models.Members;

public class Member extends Activity {

    @InjectView(R.id.tvMemberName)
    TextView tvName;
    @InjectView(R.id.tvMemberInfo)
    TextView tvInfo;
    @InjectView(R.id.ivMemberProfile)
    ImageView ivProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ButterKnife.inject(this);
        Members member = getIntent().getParcelableExtra("memb");
        tvName.setText(member.name);
        tvInfo.setText(member.info);
        Picasso.with(this).load(member.url).into(ivProfile);
    }


}
