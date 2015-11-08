package peterbliss.twitterburrito.twitter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pbliss on 11/7/2015.
 */
public class TwitterRoute {
    private String url;
    private String requestMethod;
    private Map<String, String> routeDataParams;
    private String baseUrl;

    public String getUrl() {
        return url;
    }

    public void setURL(String _url) {
        this.url = _url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public Map<String, String> getRouteDataParams()
    {
        return routeDataParams;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }

    public enum routes {
        SEARCH_TWEETS
    }

    public TwitterRoute(routes route)
    {
        requestMethod = "GET";
        String action = "";

        switch (route){
            case SEARCH_TWEETS:{
                action= "search/tweets";
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
        //url being api url not full url
        this.requestMethod = _method;
        routeDataParams = new HashMap<String, String>();
    }
}
