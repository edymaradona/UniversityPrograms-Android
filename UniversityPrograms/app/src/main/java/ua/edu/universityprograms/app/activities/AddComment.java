package ua.edu.universityprograms.app.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ua.edu.universityprograms.app.Asyncs.AddCommentAsync;
import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.models.DtoAddComment;
import ua.edu.universityprograms.app.models.DtoEventBase;
import ua.edu.universityprograms.app.models.User;

public class AddComment extends Activity {

    @InjectView(R.id.etTitle)
    EditText etTitlel;
    @InjectView(R.id.etComment)
    EditText etComment;
    @InjectView(R.id.bSendComment)
    Button bSend;
    User u;


    public void ActionBarRefresher() {
        getActionBar().setTitle("Add Comment");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(PreferenceManager.getDefaultSharedPreferences(this).getInt("theme", android.R.style.Theme_Holo));
        super.onCreate(savedInstanceState);
        ActionBarRefresher();
        setContentView(R.layout.comment_page);
        ButterKnife.inject(this);
        try {
            u = getIntent().getParcelableExtra("Comment");
        }catch(NullPointerException e){
            Toast.makeText(AddComment.this, "Unable to load user", Toast.LENGTH_SHORT).show();
            finish();
        }
        bSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddCommentAsync add_comment = new AddCommentAsync(AddComment.this){
                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        if(aBoolean){
                            Toast.makeText(AddComment.this, "Comment Successful", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AddComment.this, aBoolean+": ", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                };
                add_comment.execute(new DtoAddComment(u.uCwid, etComment.getText().toString(), etTitlel.getText().toString(), u.uEmail ));
            }
        });
    }

}
