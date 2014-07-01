package ua.edu.universityprograms.app.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.Asyncs.GetEventAsync;
import ua.edu.universityprograms.app.Asyncs.RsvpAsync;
import ua.edu.universityprograms.app.Asyncs.unRSVPAsync;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.Utils.DateUtils;
import ua.edu.universityprograms.app.Utils.IntentUtils;
import ua.edu.universityprograms.app.Utils.UpConstants;
import ua.edu.universityprograms.app.fragments.NoCWIDdialog;
import ua.edu.universityprograms.app.models.DtoEvent;
import ua.edu.universityprograms.app.models.DtoLocation;
import ua.edu.universityprograms.app.models.DtoRSVP;
import ua.edu.universityprograms.app.models.DtoUnRsvp;
import ua.edu.universityprograms.app.models.User;

public class Event extends FragmentActivity {
    @InjectView(R.id.tvEventAttending)
    TextView attending;
    @InjectView(R.id.tvTimeUntil)
    TextView until;
    @InjectView(R.id.tvLocation)
    TextView location;
    @InjectView(R.id.tvDescription)
    TextView description;
    @InjectView(R.id.ivEventPic)
    ImageView pic;
    @InjectView(R.id.ibGoTo)
    ImageButton map;

    Boolean isRSVPed = false;
    Context mcontext;
    User u;
    int eventID, attend;
    SharedPreferences preferences;

    private final String[] INTENT_FILTER = new String[] {
            "com.twitter.android",
            "com.facebook.katana"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(PreferenceManager.getDefaultSharedPreferences(this).getInt("theme", android.R.style.Theme_Holo));
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
            protected void onPostExecute(final DtoEvent dtoEvent) {
                super.onPostExecute(dtoEvent);
                attend = dtoEvent.numberAttending;
                attending.setText(Attending(attend));
                DateTime dt = new DateTime(dtoEvent.startDate);
                DateTime dte = new DateTime(dtoEvent.endDate);
                DateTimeFormatter fmte = DateTimeFormat.forPattern("h:mm aa");
                DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM d, yyyy" + "\n" + "h:mm aa" + "'-'");
                String eTime = fmte.print(dte);
                String sTime = fmt.print(dt);
                sTime = sTime + eTime;
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
                ActionBarRefresher(dtoEvent);
                invalidateOptionsMenu();
                if(dtoEvent.location == null){
                    map.setVisibility(View.GONE);
                }
                map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IntentUtils.goTo(Event.this, intentLocation(dtoEvent.location), getFragmentManager());
                    }
                });
            }

            public String intentLocation(DtoLocation loc){
                String street2 = loc.street2 != null ? loc.street2: "";
                String temp = loc.street1 + "+" + street2 + "+" + loc.city + "+"
                        + loc.state + "+" + loc.zip;
                return temp;
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

    private String Attending(int a){
        String att;
        if(a == 0){
            att = "No one is attending yet";
        }else{
            att = a + " people are attending";
        }
        return att;
    }

    private void ActionBarRefresher(DtoEvent event){
        getActionBar().setTitle(event.eventName);
        getActionBar().setSubtitle(DateUtils.timeUntilLongFormat(event.startDate, event.endDate));
    }
    ShareActionProvider mShareActionProvider;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "This is a message for you");
        setShareIntent(intent);
        return super.onCreateOptionsMenu(menu);
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(isRSVPed){
            getMenuInflater().inflate(R.menu.event_rsvped, menu);
        }else {
            getMenuInflater().inflate(R.menu.event, menu);
            MenuItem item = menu.findItem(R.id.menu_item_share);
            mShareActionProvider = new ShareActionProvider(this);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Share event via");
            setShareIntent(intent);
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
//        if(id == R.id.action_share){
//            IntentUtils.shareChooser(Event.this);
//        }
        return super.onOptionsItemSelected(item);
    }
    public void RSVPforEvent(int i, User u){

        RsvpAsync rsvp = new RsvpAsync(Event.this){
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if(aBoolean){
                    isRSVPed = true;
                    attending.setText(Attending(++attend));
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
                    attending.setText(Attending(--attend));
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
