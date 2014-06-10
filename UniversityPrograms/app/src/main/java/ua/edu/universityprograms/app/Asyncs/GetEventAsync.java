package ua.edu.universityprograms.app.Asyncs;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.models.DtoEvent;

/**
 * Created by vcaciuc on 6/10/2014.
 */
public class GetEventAsync extends AsyncTask<String, Void, DtoEvent> {
    String TAG = "Upcoming events";
    String uRLString = "";

    public GetEventAsync(Context cxt,int eventId) {
        uRLString = cxt.getString(R.string.base_API) + cxt.getString(R.string.get_event) + eventId + "&" + cxt.getString(R.string.token_cwid);
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected DtoEvent doInBackground(String... strings) {
        return WebCall(strings[0]);
    }

    private DtoEvent WebCall(String cwid){
        DtoEvent eventsResponse = new DtoEvent();
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        uRLString = uRLString + cwid;
        try {
            Log.d(TAG, uRLString);
            HttpGet request = new HttpGet(uRLString);
            StringEntity se = new StringEntity("");
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            response = httpclient.execute(request);
            Log.i(TAG + " Response Status", response.getStatusLine().toString());
            String responseString = EntityUtils.toString(response.getEntity());
            Log.i(TAG + " Response String", responseString);
            //Parse responses
            eventsResponse = new Gson().fromJson(responseString, DtoEvent.class);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eventsResponse;
    }
}
