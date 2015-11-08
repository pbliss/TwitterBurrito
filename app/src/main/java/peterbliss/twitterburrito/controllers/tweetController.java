package peterbliss.twitterburrito.controllers;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import peterbliss.twitterburrito.models.Tweet;
import peterbliss.twitterburrito.twitter.Twitter;

/**
 * Created by pbliss on 11/7/2015.
 */
public class tweetController {
    public List<Tweet> getTweets(String keyword, int page) {

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Tweet> query = realm.where(Tweet.class);

        //check against the rate limit, only refresh if
        //the tweets are more stale than the limit

        //refresh the local cache with tweets
        Twitter.search(keyword);

        //TODO page the results

        //pull the tweets from realm ordered date desc
        return query.findAllSorted("created_at", false);
    }

    //query the realm database and pull back a list of cached tweets that have
    //the flag of favorited
    public List<Tweet> getFavorites() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Tweet> query = realm.where(Tweet.class);

        query.equalTo("favorited", true);

        return query.findAll();
    }
}