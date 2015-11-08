package peterbliss.twitterburrito.controllers;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import peterbliss.twitterburrito.models.Keyword;
import peterbliss.twitterburrito.models.Tweet;
import peterbliss.twitterburrito.twitter.Twitter;

/**
 * Created by pbliss on 11/7/2015.
 */
public class TweetController {

    //need to store the times of the requests, to keep within the rate limit


    private Boolean checkRateLimit() {
        //TODO

        return true;
    }

    public void refreshTweets(final Keyword keyword) {
        //check against the rate limit, only refresh if
        //the tweets are more stale than the limit

        if(checkRateLimit()) {

            //refresh the local cache with tweets
            Twitter.search(keyword.getKeyword(), new Twitter.AsyncResponse() {
                @Override
                public void processFinish(List<Tweet> tweets) {
                    Realm realm = Realm.getDefaultInstance();
                    //begin our write transaction
                    realm.beginTransaction();

                    keyword.setTweetList(tweets);

                    //commit the changes
                    realm.commitTransaction();
                }
            });
        }
    }

    //get all favorites from a list of keywords
    public List<Tweet> getAllFavorites(List<Keyword> keywords) {

        ArrayList<Tweet> tweets = new ArrayList<>();

        for(Keyword keyword : keywords) {
            tweets.addAll(getFavorites(keyword));
        }

        return tweets;
    }

    //query the realm database and pull back a list of cached tweets that have
    //the flag of favorited
    public List<Tweet> getFavorites(Keyword keyword) {

        ArrayList<Tweet> tweets = new ArrayList<>();

        //realm doesnt support this type of query
        //have to manually search through the keyword list and find the favorites
        for(Tweet tweet : keyword.getTweetList()) {
            if(tweet.getFavorited()) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }
}