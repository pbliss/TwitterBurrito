package peterbliss.twitterburrito.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import peterbliss.twitterburrito.models.Keyword;
import peterbliss.twitterburrito.models.Tweet;
import peterbliss.twitterburrito.twitter.Twitter;

/**
 * Created by pbliss on 11/7/2015.
 */
public class TweetController {
    //need to store the times of the requests, to keep within the rate limit
    private static List<Calendar> requestTimes = new ArrayList<>();

    private static final int SEARCHLIMIT = 180;
    private static final int TIMEFRAME = 15;

    public interface AsyncResponse {
        void processFinish();
    }

    //check the time frame for the limit of request made in it
    //the time frame is a sliding window of the last X minutes
    private static Boolean checkRateLimit() {

        Calendar comp = Calendar.getInstance();
        comp.setTime(new Date());
        comp.add(Calendar.MINUTE, -TIMEFRAME); // removing 15 minutes

        //remove any requests older than 15 mins from the list
        for(Calendar c : requestTimes ){
            if(c.before(comp)) {
                requestTimes.remove(c);
            }
        }

        if(requestTimes.size() < SEARCHLIMIT) {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());

            requestTimes.add(now);
            return true;
        }

        return false;
    }

    public static void refreshTweets(final Keyword keyword, final AsyncResponse response) {
        //check against the rate limit, only refresh if
        //we have not gone over the limit

        if(checkRateLimit()) {

            //refresh the local cache with tweets
            Twitter.search(keyword.getKeyword(), new Twitter.AsyncResponse() {
                @Override
                public void processFinish(RealmList<Tweet> tweets) {
                    Realm realm = Realm.getDefaultInstance();

                    //begin our write transaction
                    realm.beginTransaction();

                    //init the list if its null
                    if(keyword.getTweetList() == null) {
                        keyword.setTweetList(new RealmList<Tweet>());
                    }
                    else {
                        //trim the list of duplicates
                        //normally would use a query but realm doesnt support that type
                        for(Tweet existing : keyword.getTweetList()) {
                            for (Tweet tweet : tweets) {
                                if(tweet.getId() == existing.getId()) {
                                    tweets.remove(tweet);
                                }
                            }
                        }
                    }

                    keyword.getTweetList().addAll(0, tweets);

                    //commit the changes
                    realm.commitTransaction();

                    //if there is a finished callback call it
                    if(response != null) {
                        response.processFinish();
                    }
                }
            });
        }
    }

    //get all favorites from a list of keywords
    public static RealmList<Tweet> getAllFavorites(List<Keyword> keywords) {

        RealmList<Tweet> tweets = new RealmList<>();

        for(Keyword keyword : keywords) {
            tweets.addAll(getFavorites(keyword));
        }

        return tweets;
    }

    //query the realm database and pull back a list of cached tweets that have
    //the flag of favorited
    public static RealmList<Tweet> getFavorites(Keyword keyword) {

        RealmList<Tweet> tweets = new RealmList<>();

        //avoid crashing on using a null list
        if(keyword.getTweetList() != null) {
            //realm doesnt support this type of query
            //have to manually search through the keyword list and find the favorites
            for (Tweet tweet : keyword.getTweetList()) {
                if (tweet.getFavorited()) {
                    tweets.add(tweet);
                }
            }
        }

        return tweets;
    }

    //remove tweets from local database over a 5 days old and not favorited
    public static void cleanOldData(List<Keyword> keywords){
        Realm realm = Realm.getDefaultInstance();

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -5); // removing 5 days

        //begin our write transaction
        realm.beginTransaction();

        //realm doesnt support these complex query like this
        //have to manually loop...
        for(Keyword keyword : keywords) {
            for(Tweet tweet :keyword.getTweetList())
            {
                Calendar created = Calendar.getInstance();
                created.setTime(tweet.getCreated_at());
                //remove old and non favorite tweets
                if(created.before(c) && tweet.getFavorited() == false)  {
                    keyword.getTweetList().remove(tweet);
                }
            }
        }

        realm.commitTransaction();

    }
}