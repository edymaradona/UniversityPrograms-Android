package ua.edu.universityprograms.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.Adapters.AboutAdapter;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.activities.ContactUs;
import ua.edu.universityprograms.app.activities.TeamMembers;
import ua.edu.universityprograms.app.activities.WhatWeDo;
import ua.edu.universityprograms.app.activities.WhoWeAre;

/**
 * Created by vcaciuc on 6/3/2014.
 */
public class AboutUPFragment extends Fragment {

    private String ABOUT_UP_TITLES[] = {"What We Do", "Who We Are", "Team Members", "Contact Us"};
    @InjectView(R.id.lvAbout)
    ListView lvAbout;

    AboutAdapter adapter;


    public static AboutUPFragment fragmentInstance() {
        AboutUPFragment fragment = new AboutUPFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AboutUPFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.about_up, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new AboutAdapter(getActivity(), ABOUT_UP_TITLES);
        lvAbout.setAdapter(adapter);
        lvAbout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Intent intent = null;
                switch (pos) {
                    case 0:
                        intent = new Intent(getActivity(), WhatWeDo.class);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), WhoWeAre.class);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), TeamMembers.class);
                        break;
                    case 3:
                       intent = new Intent(getActivity(), ContactUs.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
