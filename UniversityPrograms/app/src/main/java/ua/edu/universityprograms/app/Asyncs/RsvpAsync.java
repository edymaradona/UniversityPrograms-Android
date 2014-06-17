package ua.edu.universityprograms.app.Asyncs;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.models.DtoAddComment;
import ua.edu.universityprograms.app.models.DtoRSVP;

/**
 * Created by vcaciuc on 6/17/2014.
 */
public class RsvpAsync extends AsyncTask<DtoRSVP, Void, Boolean> {
    String TAG = "RSVP from event";
    String uRLString = "";

    public RsvpAsync(Context cxt) {
        uRLString = cxt.getString(R.string.base_API) + cxt.getString(R.string.RSVP);
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(DtoRSVP... strings) {
        return WebCall(strings[0]);
    }

    private Boolean WebCall(DtoRSVP rsvp){
        Boolean b = false;
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        try {
            Log.d(TAG, uRLString);
            HttpPost request = new HttpPost(uRLString);
            String json = new Gson().toJson(rsvp);
            Log.i(TAG, json);
            StringEntity se = new StringEntity(json);
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            request.setEntity(se);
            response = httpclient.execute(request);
            Log.i(TAG + " Response Status", response.getStatusLine().toString());
            String responseString = EntityUtils.toString(response.getEntity());
            Log.i(TAG + " Response String", responseString);
            //Parse responses
            b = Boolean.valueOf(responseString);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }
}
