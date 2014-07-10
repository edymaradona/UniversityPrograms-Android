package ua.edu.universityprograms.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.R;

/**
 * Created by vcaciuc on 6/4/2014.
 */
public class AboutAdapter extends ArrayAdapter<String> {

    // Sets the layout file
    public AboutAdapter(Context context, String[] items) {
        super(context, R.layout.list_cell_about, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_cell_about, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvItem.setText(item);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tvItem)
        TextView tvItem;
        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}