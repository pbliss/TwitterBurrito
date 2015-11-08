package peterbliss.twitterburrito.twitter;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pbliss on 11/7/2015.
 */
public class TwitterRequest {
    TwitterRoute route;
    ArrayList<String> params;
    String jsondata = null;

    public TwitterRequest(TwitterRoute _route) {
        route = _route;
        params = new ArrayList<String>();
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public String getJsondata() {
        return jsondata;
    }

    public HttpURLConnection buildUrlRequest() throws Exception
    {
        if (route == null) {
            throw new Exception("A route must be specified");
        }

        URL url = new URL(route.getUrl());

        if(route.getRequestMethod().equals("GET")) {
            String paramString = "";

            for (String s : params)
            {
                paramString += "&" + s;
            }

            url = new URL(route.getUrl() + paramString);
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(route.getRequestMethod());

        return connection;
    }
}
