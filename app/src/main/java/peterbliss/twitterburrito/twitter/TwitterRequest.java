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
    private TwitterRoute route;
    private String paramString = "";
    private HashMap<String, String> requestProperties;
    private HashMap<String, String> params;

    public TwitterRequest(TwitterRoute _route) {
        route = _route;
        params = new HashMap<>();
        requestProperties = new HashMap();
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public void addRequestProperty(String key, String value) {
        requestProperties.put(key, value);
    }

    public String getParamString() {
        return paramString;
    }

    public HttpURLConnection buildUrlRequest() throws Exception
    {
        if (route == null) {
            throw new Exception("A route must be specified");
        }

        URL url = new URL(route.getUrl());

        for (Map.Entry<String, String> entry : params.entrySet())
        {
            paramString += entry.getKey() + "=" + entry.getValue() + "&";
        }

        //trim the trailing &
        paramString = paramString.substring(0, paramString.length() - 1);

        if(route.getRequestMethod().equals("GET")) {
            url = new URL(route.getUrl() + "?" + paramString);
            paramString = null;
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if(route.getRequestMethod().equals("POST")) {
            if (paramString != null && paramString != "") {
                connection.setRequestProperty("Content-Length", String.format("%d", paramString.getBytes().length));
            }
        }

        connection.setRequestMethod(route.getRequestMethod());

        if(!requestProperties.isEmpty()) {
            for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        return connection;
    }
}
