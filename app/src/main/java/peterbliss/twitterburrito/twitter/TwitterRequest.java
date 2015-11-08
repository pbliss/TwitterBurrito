package peterbliss.twitterburrito.twitter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pbliss on 11/7/2015.
 */
public class TwitterRequest {
    private TwitterRoute route;
    private String jsondata = null;
    private HashMap<String, String> requestProperties;
    private HashMap<String, String> params;

    public TwitterRequest(TwitterRoute _route) {
        route = _route;
        params = new HashMap<>();
        requestProperties = new HashMap();
    }

    public void addURLParam(String key, String value) {
        params.put(key, value);
    }

    public void addRequestProperty(String key, String value) {
        requestProperties.put(key, value);
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

            for (Map.Entry<String, String> entry : params.entrySet())
            {
                paramString += "&" + entry.getKey() + "=" + entry.getValue();
            }

            url = new URL(route.getUrl() + paramString);
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(route.getRequestMethod());

        if(!requestProperties.isEmpty()) {
            for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        return connection;
    }
}
