package peterbliss.twitterburrito.twitter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pbliss on 11/7/2015.
 */
public class TwitterRoute {
    private String url;
    private String requestMethod;
    private final String APIURL = "https://api.twitter.com";
    private String apiVersion = "/1.1";

    public void setApiVersion(String value) {
        apiVersion = value;
    }

    public String getUrl() {
        return url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public enum routes {
        SEARCH_TWEETS,
        AUTHENTICATE
    }

    public TwitterRoute(routes route)
    {
        requestMethod = "GET";
        String action = "";

        switch (route){
            case SEARCH_TWEETS:{
                action= "search/tweets.json";
                break;
            }
            case AUTHENTICATE: {
                //authenticate isnt part of the versioned api
                apiVersion = "";
                requestMethod = "POST";
                action = "oauth2/token";
                break;
            }
        }

        generateUrl(requestMethod, action);
    }

    public TwitterRoute(String _method, String action) {
        generateUrl(_method, action);
    }

    private void generateUrl(String _method, String action)
    {
        this.requestMethod = _method;
        url = String.format("%s%s/%s", this.APIURL, this.apiVersion, action);
    }
}