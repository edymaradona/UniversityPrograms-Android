package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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
 * Created by vcaciuc on 6/5/2014.
 */
public class Intern extends Activity implements AdapterView.OnItemClickListener{

    @InjectView(R.id.gvAssist)
    GridView assistants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grad_assist);
        ButterKnife.inject(this);
        MembersAdapter adapter = new MembersAdapter(this, getAssistants());
        assistants.setAdapter(adapter);
        assistants.setOnItemClickListener(this);
    }
    ArrayList<Members> list;
    public ArrayList<Members> getAssistants(){
        list = new ArrayList<Members>();
        Resources res = getResources();
        list.add(new Members("http://www.up.ua.edu/images/UPWebsite-StaffNew_18.jpg", res.getString(R.string.intern1_name), res.getString(R.string.intern1_info)));

        return list;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(Intern.this, Member.class);
        intent.putExtra("memb",list.get(i));
        startActivity(intent);
    }
}
