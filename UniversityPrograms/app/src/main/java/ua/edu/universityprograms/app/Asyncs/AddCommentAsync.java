package ua.edu.universityprograms.app.Asyncs;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.models.DtoAddComment;
import ua.edu.universityprograms.app.models.DtoEvent;

/**
 * Created by vcaciuc on 6/11/2014.
 */
public class AddCommentAsync extends AsyncTask<DtoAddComment, Void, Boolean> {
    String TAG = "Adding comment";
    String uRLString = "";

    public AddCommentAsync(Context cxt) {
        uRLString = cxt.getString(R.string.base_API) + cxt.getString(R.string.addComment);
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(DtoAddComment... strings) {
        return WebCall(strings[0]);
    }

    private Boolean WebCall(DtoAddComment addComment){
        Boolean b = false;
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        try {
            Log.d(TAG, uRLString);
            HttpPost request = new HttpPost(uRLString);
            String json = new Gson().toJson(addComment);
            StringEntity se = new StringEntity(json);
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            request.setEntity(se);
            response = httpclient.execute(request);
            Log.i(TAG + " Response Status", response.getStatusLine().toString());
            String responseString = EntityUtils.toString(response.getEntity());
            Log.i(TAG + " Response String", responseString);
            //Parse responses
            b =  Boolean.valueOf(responseString);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }
}
