package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.Asyncs.GetEventAsync;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.models.DtoEvent;
import ua.edu.universityprograms.app.models.DtoLocation;
import ua.edu.universityprograms.app.models.Members;

public class Event extends Activity {
    @InjectView(R.id.tvEventName)
    TextView name;
    @InjectView(R.id.tvTimeUntil)
    TextView until;
    @InjectView(R.id.tvLocation)
    TextView location;
    @InjectView(R.id.tvDescription)
    TextView description;



    Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.inject(this);
        Intent i =  getIntent();
        int eventID = i.getIntExtra("event",-1);
        GetEventAsync gea = new GetEventAsync(this, eventID){
            @Override
            protected void onPostExecute(DtoEvent dtoEvent) {
                super.onPostExecute(dtoEvent);
                name.setText(dtoEvent.eventName);
                until.setText(dtoEvent.startDate);
                Picasso.with(mcontext).load(dtoEvent.imageUrl);
                description.setText(dtoEvent.eventDescription);
                if(dtoEvent.location != null){
                    String temp = setLocationString(dtoEvent.location);
                    location.setText(temp);
                }else{
                    location.setText("");
                }
            }

            public String setLocationString(DtoLocation loc){
                String temp = loc.name + loc.street1 + loc.street2 + loc.city
                        + loc.state + loc.zip + loc.roomNumber;
                return temp;
            }
        };
        gea.execute("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
