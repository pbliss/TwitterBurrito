package peterbliss.twitterburrito.controllers;

import java.util.List;

import io.realm.RealmList;
import peterbliss.twitterburrito.models.Keyword;
import peterbliss.twitterburrito.models.Tweet;
import peterbliss.twitterburrito.twitter.Twitter;

/**
 * Created by pbliss on 11/8/2015.
 */
public class KeywordsController {

    public static RealmList<Keyword> keywordList;

    //Adds a tab for a specified keyword to the system and registers it for search
    public static void addKeyword(String term) {
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
            }
        });

        //the keyword will be added to the list before the
        //tweets will have been fully loaded
        //need to observe this in the UI.
        //a spinner showing its still loading is likely best
        keywordList.add(keyword);
    }
}
