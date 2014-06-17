package ua.edu.universityprograms.app.Asyncs;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.lang.reflect.Type;
import java.util.ArrayList;

import ua.edu.universityprograms.app.R;
import ua.edu.universityprograms.app.models.DtoComment;

/**
 * Created by vcaciuc on 6/11/2014.
 */
public class GetCommentAsync extends AsyncTask<Void, Void, ArrayList<DtoComment>> {
    String TAG = "Get Comments";
    String uRLString = "";

    public GetCommentAsync(Context cxt, String cwid) {
        uRLString = cxt.getString(R.string.base_API) + cxt.getString(R.string.getComments) + cwid;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<DtoComment> doInBackground(Void... strings) {
        return WebCall();
    }

    private ArrayList<DtoComment> WebCall() {
        ArrayList<DtoComment> eventsResponse = new ArrayList<DtoComment>();
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
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
            Type eventsType = new TypeToken<ArrayList<DtoComment>>() {
            }.getType();
            eventsResponse = new Gson().fromJson(responseString, eventsType);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eventsResponse;
    }
}
