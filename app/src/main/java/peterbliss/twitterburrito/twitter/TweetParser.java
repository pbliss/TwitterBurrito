package peterbliss.twitterburrito.twitter;

import android.support.annotation.NonNull;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import peterbliss.twitterburrito.models.Tweet;

/**
 * Created by pbliss on 11/8/2015.
 */
public class TweetParser {
    public static Tweet parseTweet(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        return tweet;
    }

    public static List<Tweet> parseTweets(JSONObject jsonObject) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();



        return tweets;
    }
}
