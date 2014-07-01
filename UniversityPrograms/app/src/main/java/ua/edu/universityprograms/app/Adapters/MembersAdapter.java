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
import ua.edu.universityprograms.app.models.Members;

/**
 * Created by vcaciuc on 6/4/2014.
 */
public class MembersAdapter extends ArrayAdapter<Members> {
    Context mcontext;
    private Display display;
    private Point size;
    public static int width;
    private Drawable error;

    public MembersAdapter(Context context, ArrayList<Members> members) {
        super(context, R.layout.grid_cell_member, members);
        mcontext = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        width = size.x;
        Drawable dr = mcontext.getResources().getDrawable(R.drawable.alogo1);
        Bitmap bit = ((BitmapDrawable) dr).getBitmap();
        error = new BitmapDrawable(mcontext.getResources(), Bitmap.createScaledBitmap(bit, width / 2, width / 2, true));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Members member = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_cell_member, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(member.name);

        Picasso.with(mcontext).load(member.url).placeholder(R.drawable.applogo_35splash).error(R.drawable.applogo_35splash).resize(width / 2, width / 2).centerCrop().into(holder.ivProfile);
        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.tvCellName)
        TextView tvName;
        @InjectView(R.id.ivProfile)
        ImageView ivProfile;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            ivProfile.getLayoutParams().height = width / 2;
            ivProfile.getLayoutParams().width = width / 2;
        }
    }
}

