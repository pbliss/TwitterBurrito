package peterbliss.twitterburrito.twitter;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import peterbliss.twitterburrito.models.TwitterAuth;
import peterbliss.twitterburrito.models.TwitterResponse;

/**
 * Created by pbliss on 11/8/2015.
 */
public class TwitterAuthenticate {

    public static TwitterAuth auth;

    public interface AsyncResponse {
        void processFinish(TwitterAuth auth);
    }

    public static void authenticateConnection(final AsyncResponse responseCallback) {

        try {
            // URL encode the consumer key and secret
            String urlApiKey = URLEncoder.encode(Twitter.CONSUMER_KEY, "UTF-8");
            String urlApiSecret = URLEncoder.encode(Twitter.CONSUMER_SECRET, "UTF-8");

            // Concatenate the encoded consumer key, a colon character, and the
            // encoded consumer secret
            String combined = urlApiKey + ":" + urlApiSecret;

            // Base64 encode the string
            String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

            //build a new request object for the authentication route
            TwitterRequest request = new TwitterRequest(new TwitterRoute(TwitterRoute.routes.AUTHENTICATE));

            //add required header parameters
            request.addRequestProperty("Authorization", "Basic " + base64Encoded);
            request.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            request.addParam("grant_type", "client_credentials");

            //call the connection on the request
            TwitterConnection connection = new TwitterConnection();
            connection.setResonseCallback(new TwitterConnection.AsyncResponse() {
                @Override
                public void processFinish(TwitterResponse output) {
                    auth = new TwitterAuth();

                    try {
                        //process the response from the auth request
                        auth.setToken_type(output.getJsonObject().getString("token_type"));
                        auth.setAccess_token(output.getJsonObject().getString("access_token"));

                        responseCallback.processFinish(auth);
                    }
                    catch(org.json.JSONException ex) {
                        System.out.println(ex.getMessage());
                        responseCallback.processFinish(null);
                    }
                }
            });

            //call the request for the auth
            connection.execute(request);
        }
        catch(UnsupportedEncodingException ex) {
            TwitterAuth auth = new TwitterAuth();
            auth.setToken_type("INVALID");
            responseCallback.processFinish(auth);
        }
        catch(IllegalStateException ex1) {
            TwitterAuth auth = new TwitterAuth();
            auth.setToken_type("INVALID");
            responseCallback.processFinish(auth);
        }
    }
}
