package ua.edu.universityprograms.app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.edu.universityprograms.app.R;

/**
 * Created by vcaciuc on 6/3/2014.
 */
public class AboutUPFragment extends Fragment {

    String classes[] = { "What We Do", "Who We Are", "Team Members", "Contact Us"};

    private static final String ARG_SECTION_NUMBER = "";

    public static AboutUPFragment fragmentInstance(int sectionNumber) {
        AboutUPFragment fragment = new AboutUPFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AboutUPFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about_up, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
