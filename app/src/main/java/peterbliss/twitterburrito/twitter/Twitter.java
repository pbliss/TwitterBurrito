package peterbliss.twitterburrito.twitter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import peterbliss.twitterburrito.models.Tweet;
import peterbliss.twitterburrito.models.TwitterAuth;

/**
 * Created by pbliss on 11/7/2015.
 */
public class Twitter {

    public final static String CONSUMER_KEY = "QhvIE54fStSbl5EFcq2Vt4b9N";
    public final static String CONSUMER_SECRET = "QfwDgq4NnwbFD1YYTqh9hfkpGIqYc27oA8SOxeJ0xQFzbMhbgY";
    public static TwitterAuth auth;

    public interface AsyncResponse {
        void processFinish(List<Tweet> tweets);
    }

    //Ping the twitter api and search for the keyword specified
    //populate the local cache with the tweets retrieved
    public static void search(String keyword, final AsyncResponse responseCallback) {

        //connect to api and authenticate
        if(auth == null || auth.getToken_type().equals("INVALID")) {
            auth = TwitterAuthenticate.authenticateConnection();
        }

        //build search request
        TwitterRequest request = new TwitterRequest(new TwitterRoute(TwitterRoute.routes.SEARCH_TWEETS));

        //add required header parameters
        request.addRequestProperty("Authorization", "Bearer " + auth.getAccess_token());
        request.addRequestProperty("Content-Type", "application/json");
        request.addURLParam("q", keyword);

        TwitterConnection connection = new TwitterConnection();

        connection.setResonseCallback(new TwitterConnection.AsyncResponse() {
            @Override
            public void processFinish(TwitterResponse output) {
                //examine the results
                if(output.getStatus()) {

                    //parse the reponse into a list of tweets
                    responseCallback.processFinish(TweetParser.parseTweets(output.getJsonObject()));
                }
            }
        });

        connection.execute(request);
    }
}
