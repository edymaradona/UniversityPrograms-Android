package ua.edu.universityprograms.app.activities;

import android.app.Activity;
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
 * Created by vcaciuc on 6/4/2014.
 */
public class GraduateAssistant extends Activity implements AdapterView.OnItemClickListener{

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

    public ArrayList<Members> getAssistants(){
        ArrayList<Members> list = new ArrayList<Members>();
        Resources res = getResources();
        list.add(new Members("http://www.up.ua.edu/images/UPWebsite-StaffNew_15.jpg", res.getString(R.string.grad_assist1_name), res.getString(R.string.grad_assist1_info)));
        list.add(new Members("http://www.up.ua.edu/images/UPWebsite-StaffNew_9.jpg", res.getString(R.string.grad_assist2_name), res.getString(R.string.grad_assist2_info)));
        list.add(new Members("http://www.up.ua.edu/images/UPWebsite-StaffNew_13.jpg", res.getString(R.string.grad_assist3_name), res.getString(R.string.grad_assist3_info)));

        return list;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
