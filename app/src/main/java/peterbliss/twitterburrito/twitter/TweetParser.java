package peterbliss.twitterburrito.twitter;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import io.realm.RealmList;
import peterbliss.twitterburrito.models.Tweet;
import peterbliss.twitterburrito.models.User;

/**
 * Created by pbliss on 11/8/2015.
 */
public class TweetParser {
    public static Tweet parseTweet(JSONObject tweetObj) {
        Tweet tweet = new Tweet();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);

        try {
            if(tweetObj != null) {

                try {
                    tweet.setCreated_at(dateFormat.parse(tweetObj.getString("created_at")));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                tweet.setFavorite_count(tweetObj.getInt("favorite_count"));
                tweet.setId(tweetObj.getLong("id"));
                tweet.setRetweet_count(tweetObj.getInt("retweet_count"));
                tweet.setText(tweetObj.getString("text"));

                User user = new User();

                JSONObject userObject = tweetObj.getJSONObject("user");

                //parse the user object from the tweet
                if (userObject != null) {
                    user.setId(userObject.getInt("id"));
                    user.setName(userObject.getString("name"));
                    user.setProfile_image_url(userObject.getString("profile_image_url"));
                    user.setScreen_name(userObject.getString("screen_name"));
                }

                tweet.setUser(user);
            }
        }
        catch(org.json.JSONException ex) {
            tweet.setId(-1);

            System.out.println(ex.getMessage());
        }

        return tweet;
    }

    public static RealmList<Tweet> parseTweets(JSONObject jsonObject) {
        RealmList<Tweet> tweets = new RealmList<Tweet>();

        try {

            //get the main root object
            JSONArray rootArray = jsonObject.getJSONArray("statuses");

            //loop through each of the tweets
            for(int i = 0; i < rootArray.length(); i++) {

                //parse the tweet object from the json
                JSONObject tweetObj = rootArray.getJSONObject(i);

                //parse tweet and add to list
                Tweet tweet = parseTweet(tweetObj);

                //dont add invalid or error parsing tweets
                if(tweet.getId() != -1) {
                    tweets.add(tweet);
                }
            }
        }
        catch(org.json.JSONException ex) {
            System.out.println(ex.getMessage());
        }
        return tweets;
    }
}
