package ua.edu.universityprograms.app.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.models.DtoEventBase;

/**
 * Created by vcaciuc on 6/10/2014.
 */
public class EventsAdapter extends ArrayAdapter<DtoEventBase>{

    Context mcontext;
    private Display display;
    private Point size;
    public static int width;
    private Drawable error;
    String startEvent, endEvent;

    public EventsAdapter(Context context, ArrayList<DtoEventBase> events) {
        super(context, R.layout.grid_cell_member, events);
        mcontext = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        width = size.x;
        Drawable dr = mcontext.getResources().getDrawable(R.drawable.alogo1);
        Bitmap bit = ((BitmapDrawable) dr).getBitmap();
        error = new BitmapDrawable(mcontext.getResources(), Bitmap.createScaledBitmap(bit, width, width, true));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DtoEventBase event = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.events, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(event.eventName);
        startEvent = event.startDate;
        endEvent = event.endDate;
        String timeLeft;
        timeLeft = timeUntil(startEvent, endEvent);
        holder.time.setText(timeLeft);
        Picasso.with(mcontext).load(event.imageUrl).error(error).resize(width, width).centerCrop().into(holder.pic);
        return convertView;
    }

    // Counts the number of days, hours and minutes from now until the event starts
    private String timeUntil(String start, String end){
        String timeUntil = "";
        DateTime dt = new DateTime();
        DateTime st = new DateTime(start);
        DateTime et = new DateTime(end);
        int days = Days.daysBetween(dt, st).getDays();
        int hours = Hours.hoursBetween(dt, st).getHours();
        int min = Minutes.minutesBetween(dt, st).getMinutes();
        int daysLeft = Days.daysBetween(dt, et).getDays();
        int hoursLeft = Hours.hoursBetween(dt, et).getHours();
        int minLeft = Minutes.minutesBetween(dt, et).getMinutes();
        if(days > 0 ){
            timeUntil = timeUntil + "in " + days + " d";
            return timeUntil;
        }else if(hours > 0){
            timeUntil = timeUntil + "in " + hours + " h";
            return timeUntil;
        }else if(min > 0){
            timeUntil = timeUntil + "in " + min + " m";
            return timeUntil;
        }else if(daysLeft > 0 || hoursLeft > 0 || minLeft > 0){
            timeUntil = timeUntil + "now";
            return timeUntil;
        }else {
            timeUntil = timeUntil + "ended";
            return timeUntil;
        }
    }

    static class ViewHolder {

        @InjectView(R.id.tvEventName)
        TextView name;
        @InjectView(R.id.tvTimeUntil)
        TextView time;
        @InjectView(R.id.ivEventPic)
        ImageView pic;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            pic.getLayoutParams().height = width;
            pic.getLayoutParams().width = width;
        }
    }


}
