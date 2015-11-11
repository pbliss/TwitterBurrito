package peterbliss.twitterburrito.models;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by pbliss on 11/8/2015.
 */
public class Keyword extends RealmObject{
    private String keyword;
    private RealmList<Tweet> tweetList;
    private Date lastRefresh;

    public void setKeyword(String value) {
        keyword = value;
    }

    public void setTweetList(RealmList<Tweet> value) {
        tweetList = value;
    }

    public void setLastRefresh(Date value) {
        lastRefresh = value;
    }

    public String getKeyword() {
        return keyword;
    }

    public RealmList<Tweet> getTweetList() {
        return tweetList;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }
}
