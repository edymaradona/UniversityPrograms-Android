package ua.edu.universityprograms.app.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.Asyncs.GetEventAsync;
import ua.edu.universityprograms.app.Asyncs.RsvpAsync;
import ua.edu.universityprograms.app.Asyncs.unRSVPAsync;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.Utils.UpConstants;
import ua.edu.universityprograms.app.fragments.NoCWIDdialog;
import ua.edu.universityprograms.app.models.DtoEvent;
import ua.edu.universityprograms.app.models.DtoLocation;
import ua.edu.universityprograms.app.models.DtoRSVP;
import ua.edu.universityprograms.app.models.DtoUnRsvp;
import ua.edu.universityprograms.app.models.User;

public class Event extends FragmentActivity {
    @InjectView(R.id.tvEventName)
    TextView name;
    @InjectView(R.id.tvTimeUntil)
    TextView until;
    @InjectView(R.id.tvLocation)
    TextView location;
    @InjectView(R.id.tvDescription)
    TextView description;
    @InjectView(R.id.ivEventPic)
    ImageView pic;

    Boolean isRSVPed = false;
    Context mcontext;
    User u;
    int eventID;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.inject(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(Event.this);
        String user = preferences.getString(UpConstants.USER_KEY, "");
        u = new Gson().fromJson(user, User.class);
        String cwid = "";
        if(u!=null){
            if(!u.uCwid.isEmpty()){
                cwid = u.uCwid;
            }
        }
        Intent i =  getIntent();
        eventID = i.getIntExtra("event",-1);
        GetEventAsync gea = new GetEventAsync(this, eventID, cwid){
            @Override
            protected void onPostExecute(DtoEvent dtoEvent) {
                super.onPostExecute(dtoEvent);
                name.setText(dtoEvent.eventName);
                DateTime dt = new DateTime(dtoEvent.startDate);
                DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM d, yy" + "\n" + "'at'" + " h:mm aa");
                String sTime = fmt.print(dt);
                until.setText(sTime);
                Picasso.with(mcontext).load(dtoEvent.imageUrl).into(pic);
                description.setText(dtoEvent.eventDescription);
                if(dtoEvent.location != null){
                    String temp = setLocationString(dtoEvent.location);
                    location.setText(temp);
                }else{
                    location.setText("");
                }
                isRSVPed = dtoEvent.isRegistered;
                invalidateOptionsMenu();
            }

            public String setLocationString(DtoLocation loc){
                String street2 = loc.street2 != null ? loc.street2: "";
                String roomN = loc.roomNumber !=null ? loc.roomNumber: "";
                String temp = loc.name + "\n" + loc.street1 + street2 + "\n" + loc.city + " "
                        + loc.state + " " + loc.zip + "\n" + roomN;
                return temp;
            }
        };
        gea.execute("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(isRSVPed){
            getMenuInflater().inflate(R.menu.event_rsvped, menu);
        }else {
            getMenuInflater().inflate(R.menu.event, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_rsvp) {
            if(u != null){
                if(!u.uCwid.isEmpty()) {
                    RSVPforEvent(eventID, u);
                }else{
                    DialogFragment dialog = new NoCWIDdialog();
                    dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                }
            }
            else{
                DialogFragment dialog = new NoCWIDdialog();
                dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
            }

            return true;
        }
        if(id == R.id.action_unrsvp){
            if(u != null){
                if(!u.uCwid.isEmpty()) {
                    unRSVP_forEvent(eventID, u);
                }else{
                    DialogFragment dialog = new NoCWIDdialog();
                    dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                }
            }
            else{
                DialogFragment dialog = new NoCWIDdialog();
                dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void RSVPforEvent(int i, User u){

        RsvpAsync rsvp = new RsvpAsync(Event.this){
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if(aBoolean){
                    isRSVPed = true;
                    Toast.makeText(Event.this, "You have RSVPed for the event", Toast.LENGTH_SHORT).show();
                    invalidateOptionsMenu();
                }else{
                    Toast.makeText(Event.this, aBoolean+": RSVP didn't work, please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        rsvp.execute(new DtoRSVP(i, u));
    }
    public void unRSVP_forEvent(int i, User u){

        unRSVPAsync rsvp = new unRSVPAsync(Event.this){
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if(aBoolean){
                    isRSVPed = false;
                    Toast.makeText(Event.this, "You have unRSVPed for the event", Toast.LENGTH_SHORT).show();
                    invalidateOptionsMenu();
                }else{
                    Toast.makeText(Event.this, aBoolean+": unRSVP didn't work, please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        rsvp.execute(new DtoUnRsvp(i, u.uCwid));
    }
}
