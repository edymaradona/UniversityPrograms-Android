package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.Adapters.AboutAdapter;
import ua.edu.universityprograms.app.R;

/**
 * Created by vcaciuc on 6/4/2014.
 */
public class TeamMembers extends Activity {
    private String categories[] = {"Director", "Graduate Assistant", "Event Programmer",
            "Communication Team", "Intern"};
    @InjectView(R.id.lvAbout)
    ListView lvMembers;

    AboutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(PreferenceManager.getDefaultSharedPreferences(this).getInt("theme", android.R.style.Theme_Holo));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_members);
        ButterKnife.inject(this);
        ActionBarRefresher();
        overridePendingTransition(R.anim.slide_in_left, R.anim.abc_fade_out);
        initialize();
    }

    // Sets the Title for this page
    public void ActionBarRefresher(){
        getActionBar().setTitle("Team Members");
    }

    // On click listener for member list
    public void initialize(){
        adapter = new AboutAdapter(TeamMembers.this, categories);
        lvMembers.setAdapter(adapter);
        lvMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Intent intent = null;
                switch (pos) {
                    case 0:
                        intent = new Intent(TeamMembers.this, Directors.class);
                        break;
                    case 1:
                        intent = new Intent(TeamMembers.this, GraduateAssistant.class);
                        break;
                    case 2:
                        intent = new Intent(TeamMembers.this, EventProgrammer.class);
                        break;
                    case 3:
                        intent = new Intent(TeamMembers.this, CommunicationTeam.class);
                        break;
                    case 4:
                        intent = new Intent(TeamMembers.this, Intern.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }

    // Animations for exiting the page
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition( R.anim.abc_fade_in, R.anim.translucent_exit);
    }
}
