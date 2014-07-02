package ua.edu.universityprograms.app.Adapters;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.provider.CalendarContract;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.models.DtoEventBase;

/**
 * Created by vcaciuc on 7/1/2014.
 */
public class MyUpAdapter extends ArrayAdapter<DtoEventBase> {

    Context mcontext;
    public static int width;
    String subtitle;

    public MyUpAdapter(Context context, ArrayList<DtoEventBase> events) {
        super(context, R.layout.my_up_adapt, events);
        mcontext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DtoEventBase event = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_up_adapt, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DateTime dt = new DateTime(event.startDate);
        DateTime dte = new DateTime(event.endDate);
        DateTimeFormatter fmte = DateTimeFormat.forPattern("'End: '" + "MMM d," + "h:mm aa");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("'Start: '" + "MMM d," + "h:mm aa");
        String eTime = fmte.print(dte);
        String sTime = fmt.print(dt);
        subtitle = sTime + "\n" + eTime + "\n" + event.numberAttending + " Attending";
        holder.name.setText(event.eventName);
        holder.date.setText(subtitle);
        Picasso.with(mcontext).load(event.imageUrl).into(holder.pic);
        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.tvEventName)
        TextView name;
        @InjectView(R.id.tvDate)
        TextView date;
        @InjectView(R.id.ivEventPicture)
        ImageView pic;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
