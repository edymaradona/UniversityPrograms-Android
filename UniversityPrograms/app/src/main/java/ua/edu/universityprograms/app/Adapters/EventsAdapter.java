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
        holder.time.setText(event.startDate);
        Picasso.with(mcontext).load(event.imageUrl).error(error).resize(width, width).centerCrop().into(holder.pic);
        return convertView;
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
