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
import ua.edu.universityprograms.app.models.DtoComment;

/**
 * Created by vcaciuc on 6/17/2014.
 */
public class CommentAdapter extends ArrayAdapter<DtoComment> {

    public CommentAdapter(Context context, ArrayList<DtoComment> items) {
        super(context, R.layout.list_cell_comment, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DtoComment item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_cell_comment, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(item.commentTitle);
        holder.tvComment.setText(item.commentText);
        return convertView;
    }
    static class ViewHolder {

        @InjectView(R.id.tvTitle)
        TextView tvTitle;
        @InjectView(R.id.tvComment)
        TextView tvComment;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
