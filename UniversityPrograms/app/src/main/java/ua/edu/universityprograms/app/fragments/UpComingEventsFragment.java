package ua.edu.universityprograms.app.fragments;

/**
 * Created by vcaciuc on 6/3/2014.
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.Adapters.EventsAdapter;
import ua.edu.universityprograms.app.Asyncs.UpComingEventsAsync;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.activities.Event;
import ua.edu.universityprograms.app.models.DtoEventBase;

/**
 * A placeholder fragment containing a simple view.
 */
public class UpComingEventsFragment extends Fragment {

    @InjectView(R.id.listView)
    ListView list;
    EventsAdapter adapter;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static UpComingEventsFragment fragmentInstance(int sectionNumber) {
        UpComingEventsFragment fragment = new UpComingEventsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public UpComingEventsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        UpComingEventsAsync ucea = new UpComingEventsAsync(activity){
            @Override
            protected void onPostExecute(final ArrayList<DtoEventBase> dtoEventBases) {
                super.onPostExecute(dtoEventBases);
                adapter = new EventsAdapter(getActivity(), dtoEventBases);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                        Intent intent = new Intent(getActivity(), Event.class);
                        DtoEventBase m = dtoEventBases.get(pos);
                        intent.putExtra("event", m.eventId);
                        startActivity(intent);
                    }
                });
            }
        };
        ucea.execute("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }
}
