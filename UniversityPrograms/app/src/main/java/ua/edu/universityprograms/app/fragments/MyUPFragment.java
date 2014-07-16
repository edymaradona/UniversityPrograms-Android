package ua.edu.universityprograms.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.Adapters.CommentAdapter;
import ua.edu.universityprograms.app.Adapters.EventsAdapter;
import ua.edu.universityprograms.app.Adapters.MyUpAdapter;
import ua.edu.universityprograms.app.Asyncs.UpComingEventsAsync;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.Utils.UpConstants;
import ua.edu.universityprograms.app.activities.AddComment;
import ua.edu.universityprograms.app.activities.Event;
import ua.edu.universityprograms.app.activities.MainActivity;
import ua.edu.universityprograms.app.activities.Settings;
import ua.edu.universityprograms.app.models.DtoComment;
import ua.edu.universityprograms.app.models.DtoEventBase;
import ua.edu.universityprograms.app.models.User;

/**
 * Created by vcaciuc on 6/3/2014.
 */
public class MyUPFragment extends Fragment {

    @InjectView(R.id.tvName)
    TextView name;
    @InjectView(R.id.tvEmail)
    TextView email;
    @InjectView(R.id.bRSVP)
    RadioButton brsvp;
    @InjectView(R.id.bComment)
    RadioButton bcomment;
    @InjectView(R.id.lvMyUp)
    ListView lvMyUp;
    ArrayList<DtoEventBase> attending;
    ArrayList<DtoComment> comments;
    ArrayAdapter<?> adapter;
//    CommentAdapter cAdapter;

    public static MyUPFragment fragmentInstance() {
        MyUPFragment fragment = new MyUPFragment();
        return fragment;
    }

    public MyUPFragment() {
        setHasOptionsMenu(true);
    }

    // Switches between RSVP/unRSVP on click
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_up, container, false);
        ButterKnife.inject(this, rootView);
        this.lvMyUp.setEmptyView(rootView.findViewById(android.R.id.empty));
        brsvp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setRSVPListview(attending);
            }
        });
        bcomment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setCommentsListView(comments);
            }
        });
        return rootView;
    }

    // Adds to the list the events you RSVPed for
    public void setRSVPedEvents(final ArrayList<DtoEventBase> dtoEventBases) {
        attending = new ArrayList<DtoEventBase>();
        for (int i = 0; i < dtoEventBases.size(); i++) {
            if (dtoEventBases.get(i).isRegistered) {
                attending.add(dtoEventBases.get(i));
            }
        }
        setRSVPListview(attending);
    }
    // Adds to the list the events you RSVPed for
    public void setComments(final ArrayList<DtoComment> dtoComments) {
        comments = new ArrayList<DtoComment>();
        for (int i = 0; i < dtoComments.size(); i++) {
                comments.add(dtoComments.get(i));
        }
    }

    // Displays the attending list
    // Entering animation
    private void setRSVPListview(final ArrayList<DtoEventBase> attending) {
        if(adapter!=null){
            adapter=null;
        }
        adapter = new MyUpAdapter(getActivity(), attending);
        try {
            lvMyUp.setAdapter(adapter);
            lvMyUp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                    Intent intent = new Intent(getActivity(), Event.class);
                    DtoEventBase m = attending.get(pos);
                    intent.putExtra("event", m.eventId);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.abc_fade_out);
                }
            });
        } catch (NullPointerException e) {
            //There are no comments and the cAdapter returns null ( don't do anything )
        }catch (IllegalStateException e1) {

        }
    }

    // Called in MainActivity
    public void setCommentsListView(final ArrayList<DtoComment> comments) {
        if(adapter!=null){
            adapter=null;
        }
        adapter = new CommentAdapter(getActivity(), comments);
        try {
            lvMyUp.setAdapter(adapter);
            lvMyUp.setOnItemClickListener(null);
        } catch (NullPointerException e) {
            //There are no comments and the cAdapter returns null ( don't do anything )
        } catch (IllegalStateException e1){

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // Displays user's name and email on top of the page
    @Override
    public void onResume() {
        super.onResume();
        brsvp.setChecked(true);
        GetUserFromPrefs();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // Grabs the user's info from the text edit fields "Settings"
    private void GetUserFromPrefs() {
        MainActivity ma = (MainActivity) getActivity();
        if (ma != null) {
            if (ma.you != null) {
                name.setText(ma.you.getFullName());
                email.setText(ma.you.uEmail);
            }
        }
    }

    // Implements on click listener for "Add Comment" and "Settings"
    // Entering animations for both pages
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MainActivity ma = (MainActivity) getActivity();
        switch (item.getItemId()) {
            case R.id.action_my_up_settings:
                Intent i = new Intent(getActivity(), Settings.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.abc_fade_out);
                break;
            case R.id.action_add_comment:
                if (ma != null) {
                    if (ma.you != null) {
                        if (!ma.you.uCwid.isEmpty()) {
                            AddComment(ma.you);
                        } else {
                            DialogFragment dialog = new NoCWIDdialog();
                            dialog.show(getFragmentManager(), "NoticeDialogFragment");
                        }
                    } else {
                        DialogFragment dialog = new NoCWIDdialog();
                        dialog.show(getFragmentManager(), "NoticeDialogFragment");
                    }
                }
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.abc_fade_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void AddComment(User you) {
        Intent intent = new Intent(getActivity(), AddComment.class);
        intent.putExtra("Comment", you);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.my_up, menu);
    }
}
