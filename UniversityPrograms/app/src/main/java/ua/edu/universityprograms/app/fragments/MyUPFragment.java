package ua.edu.universityprograms.app.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.activities.Settings;

/**
 * Created by vcaciuc on 6/3/2014.
 */
public class MyUPFragment extends Fragment {

    @InjectView(R.id.tvName)
    TextView name;
    @InjectView(R.id.tvEmail)
    TextView email;
    @InjectView(R.id.bRSVP)
    Button crsvp;
    @InjectView(R.id.bComment)
    Button bcomment;

    SharedPreferences preferences;

    private static final String ARG_SECTION_NUMBER = "";

    public static MyUPFragment fragmentInstance(int sectionNumber) {
        MyUPFragment fragment = new MyUPFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MyUPFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_up, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String name = preferences.getString("Name", "");
        String email = preferences.getString("Email", "");
        if(!name.equalsIgnoreCase(""))
            this.name.setText(name);
        if(!email.equalsIgnoreCase(""))
            this.email.setText(email);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_my_up_settings:
                Intent i = new Intent(getActivity(), Settings.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.my_up, menu);

    }
}
