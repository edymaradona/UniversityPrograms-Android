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
public class Director extends Activity implements AdapterView.OnItemClickListener{

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
    }

    public void ActionBarRefresher(){
        getActionBar().setTitle("Directors");
    }

    ArrayList<Members> list;
    public ArrayList<Members> getAssistants(){
        list = new ArrayList<Members>();
        Resources res = getResources();
        list.add(new Members("http://www.up.ua.edu/images/UPWebsite-StaffNew_2.jpg", res.getString(R.string.dir_name), res.getString(R.string.dir_info), res.getString(R.string.dir_about)));
        list.add(new Members("http://www.up.ua.edu/images/UPWebsite-StaffNew_7.jpg", res.getString(R.string.prog_assist_name), res.getString(R.string.prog_assist_info), res.getString(R.string.prog_assist_about)));

        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(Director.this, Member.class);
        intent.putExtra("memb",list.get(i));
        startActivity(intent);
    }
}
