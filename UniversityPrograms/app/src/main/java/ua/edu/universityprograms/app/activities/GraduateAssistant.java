package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.Adapters.MembersAdapter;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.models.Members;

/**
 * Created by vcaciuc on 6/4/2014.
 */
public class GraduateAssistant extends Base implements AdapterView.OnItemClickListener {

    @InjectView(R.id.gvAssist)
    GridView assistants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(PreferenceManager.getDefaultSharedPreferences(this).getInt("theme", android.R.style.Theme_Holo));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grad_assist);
        ButterKnife.inject(this);
        MembersAdapter adapter = new MembersAdapter(this, getAssistants());
        assistants.setAdapter(adapter);
        assistants.setOnItemClickListener(this);
        ActionBarRefresher();
        overridePendingTransition(R.anim.slide_in_left, R.anim.abc_fade_out);
    }

    // Sets the Title for this page
    public void ActionBarRefresher() {
        getActionBar().setTitle("Graduate Assistants");
    }

    // Gets graduate assistant's picture from the website and set their info from the "strings" file
    ArrayList<Members> list;
    public ArrayList<Members> getAssistants() {
        list = new ArrayList<Members>();
        Resources res = getResources();
        list.add(new Members("http://www.up.ua.edu/images/UPWebsite-StaffNew_15.jpg", res.getString(R.string.grad_assist1_name), res.getString(R.string.grad_assist1_info), res.getString(R.string.grad_assist1_about)));
        list.add(new Members("http://www.up.ua.edu/images/UPWebsite-StaffNew_9.jpg", res.getString(R.string.grad_assist2_name), res.getString(R.string.grad_assist2_info), res.getString(R.string.grad_assist2_about)));
        list.add(new Members("http://www.up.ua.edu/images/UPWebsite-StaffNew_13.jpg", res.getString(R.string.grad_assist3_name), res.getString(R.string.grad_assist3_info), res.getString(R.string.grad_assist3_about)));
        return list;
    }

    // Clicking the photo, opens each member personal page
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(GraduateAssistant.this, Member.class);
        intent.putExtra("memb", list.get(i));
        intent.putExtra("title","Graduate Assistant");
        startActivity(intent);
    }
}
