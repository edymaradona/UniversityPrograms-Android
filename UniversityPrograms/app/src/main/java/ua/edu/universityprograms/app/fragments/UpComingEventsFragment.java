package ua.edu.universityprograms.app.fragments;

/**
 * Created by vcaciuc on 6/3/2014.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class UpComingEventsFragment extends Fragment {

    @InjectView(R.id.section_label)
    TextView tv;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int section = getArguments().getInt(ARG_SECTION_NUMBER);
        tv.setText(section+"");
    }
}
