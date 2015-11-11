package peterbliss.twitterburrito.controllers;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
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

    public static void addKeyword(String term, Boolean refresh, final AsyncResponse response) {
        Realm realm = Realm.getDefaultInstance();

        //load cached data
        RealmQuery<Keyword> realmQuery = realm.where(Keyword.class);
        realmQuery.equalTo("keyword",term);

        Keyword keyword = realmQuery.findFirst();

        if(keyword == null) {
            //begin our write transaction
            realm.beginTransaction();

            keyword = realm.createObject(Keyword.class);

            keyword.setKeyword(term);

            realm.commitTransaction();
        }

        if(refresh) {
            TweetController.refreshTweets(keyword, new TweetController.AsyncResponse() {
                @Override
                public void processFinish() {
                    if (response != null) {
                        response.processFinish();
                    }
                }
            });
        }

        //the keyword will be added to the list before the
        //tweets will have been fully loaded
        //need to observe this in the UI.
        //a spinner showing its still loading is likely best
        keywordList.add(keyword);
    }

    //Adds a tab for a specified keyword to the system and registers it for search
    public static void addKeyword(String term, Boolean refresh) {
        addKeyword(term, refresh, null);
    }
}
