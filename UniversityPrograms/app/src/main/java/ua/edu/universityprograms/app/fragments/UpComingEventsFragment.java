package ua.edu.universityprograms.app.fragments;

/**
 * Created by vcaciuc on 6/3/2014.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ua.edu.universityprograms.app.Adapters.EventsAdapter;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.activities.Event;
import ua.edu.universityprograms.app.models.DtoEventBase;

/**
 * A placeholder fragment containing a simple view.
 */
public class UpComingEventsFragment extends Fragment {
    GridView grid;
    EventsAdapter adapter;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static UpComingEventsFragment fragmentInstance() {
        UpComingEventsFragment fragment = new UpComingEventsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public UpComingEventsFragment() {

    }

    public void setUpcomingEventsList(final ArrayList<DtoEventBase> dtoEventBases) {
        adapter = new EventsAdapter(getActivity(), dtoEventBases);
            grid.setAdapter(adapter);
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                    Intent intent = new Intent(getActivity(), Event.class);
                    DtoEventBase m = dtoEventBases.get(pos);
                    intent.putExtra("event", m.eventId);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.abc_fade_out);
                }
            });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Intialize(rootView);
        return rootView;
    }

    private void Intialize(View rootView) {
        grid = (GridView) rootView.findViewById(R.id.gridView);
    }
}
