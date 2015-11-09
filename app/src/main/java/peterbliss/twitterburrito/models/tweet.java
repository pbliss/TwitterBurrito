package peterbliss.twitterburrito.models;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by pbliss on 11/7/2015.
 */
public class Tweet extends RealmObject {
    //this is a truncated object of the actual Tweet api
    //only including the fields we will actually use

    private Date created_at;
    private String text;
    private long id;
    private int favorite_count;
    private Boolean favorited;
    private int retweet_count;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User value) {
        user = value;
    }

    public Date getCreated_at(){
        return created_at;
    }

    public String getText() {
        return text;
    }

    public long getId() {
        return id;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public int getRetweet_count() {
        return retweet_count;
    }

    public void setCreated_at(Date value) {
        created_at = value;
    }

    public void setText(String value) {
        text = value;
    }

    public void setId(long value) {
        id = value;
    }

    public void setFavorite_count(int value) {
        favorite_count = value;
    }

    public void setFavorited(Boolean value) {
        favorited = value;
    }

    public void setRetweet_count(int value) {
        retweet_count = value;
    }
}
