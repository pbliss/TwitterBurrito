package peterbliss.twitterburrito.models;

/**
 * Created by pbliss on 11/7/2015.
 */
public class tweet {
    //this is a truncated object of the actual tweet api
    //only including the fields we will actually use

    String created_at;
    String text;
    int id;
    int favorite_count;
    boolean favorited;
    int retweet_count;

}
