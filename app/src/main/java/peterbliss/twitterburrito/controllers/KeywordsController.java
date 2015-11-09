package peterbliss.twitterburrito.controllers;

import java.util.List;

import io.realm.RealmList;
import peterbliss.twitterburrito.models.Keyword;
import peterbliss.twitterburrito.models.Tweet;
import peterbliss.twitterburrito.models.TwitterAuth;
import peterbliss.twitterburrito.twitter.Twitter;

/**
 * Created by pbliss on 11/8/2015.
 */
public class KeywordsController {

    public static RealmList<Keyword> keywordList;

    public interface AsyncResponse {
        void processFinish();
    }

    public static void addKeyword(String term, final AsyncResponse response) {
        final Keyword keyword = new Keyword();
        keyword.setKeyword(term);

        //let the UI know an async task is getting the tweets for this
        keyword.setLoading(true);

        Twitter.search(term, new Twitter.AsyncResponse() {
            @Override
            public void processFinish(RealmList<Tweet> tweets) {

                //let the UI know that this keyword list is loaded
                keyword.setLoading(false);

                keyword.setTweetList(tweets);

                //if there is a finished callback call it
                if(response != null) {
                    response.processFinish();
                }
            }
        });

        //the keyword will be added to the list before the
        //tweets will have been fully loaded
        //need to observe this in the UI.
        //a spinner showing its still loading is likely best
        keywordList.add(keyword);
    }

    //Adds a tab for a specified keyword to the system and registers it for search
    public static void addKeyword(String term) {
        addKeyword(term, null);
    }
}
