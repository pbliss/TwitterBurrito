package peterbliss.twitterburrito.twitter;

import io.realm.RealmList;
import peterbliss.twitterburrito.models.Tweet;
import peterbliss.twitterburrito.models.TwitterAuth;
import peterbliss.twitterburrito.models.TwitterResponse;

/**
 * Created by pbliss on 11/7/2015.
 */
public class Twitter {

    public final static String CONSUMER_KEY = "QhvIE54fStSbl5EFcq2Vt4b9N";
    public final static String CONSUMER_SECRET = "QfwDgq4NnwbFD1YYTqh9hfkpGIqYc27oA8SOxeJ0xQFzbMhbgY";

    public interface AsyncResponse {
        void processFinish(RealmList<Tweet> tweets);
    }

    //Ping the twitter api and search for the keyword specified
    //populate the local cache with the tweets retrieved
    public static void search(final String keyword, final AsyncResponse responseCallback) {

        //connect to api and authenticate
        if(TwitterAuthenticate.auth == null || TwitterAuthenticate.auth.getToken_type().equals("INVALID")) {
            TwitterAuthenticate.authenticateConnection(new TwitterAuthenticate.AsyncResponse() {
                @Override
                public void processFinish(TwitterAuth auth) {
                    handleSearch(keyword, responseCallback);
                }
            });
        }
        else {
            handleSearch(keyword, responseCallback);
        }
    }

    private static void handleSearch(String keyword, final AsyncResponse responseCallback) {
        //build search request
        TwitterRequest request = new TwitterRequest(new TwitterRoute(TwitterRoute.routes.SEARCH_TWEETS));

        //add required header parameters
        request.addRequestProperty("Authorization", "Bearer " + TwitterAuthenticate.auth.getAccess_token());
        request.addRequestProperty("Content-Type", "application/json");
        request.addParam("q", keyword);

        TwitterConnection connection = new TwitterConnection();

        connection.setResonseCallback(new TwitterConnection.AsyncResponse() {
            @Override
            public void processFinish(TwitterResponse output) {
                //TODO handle errors from the response

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
