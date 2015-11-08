package peterbliss.twitterburrito.models;

import io.realm.RealmObject;

/**
 * Created by pbliss on 11/7/2015.
 */
public class User extends RealmObject {
    //truncated version of the twitter User object
    private String profile_image_url;
    private String name;
    private String screen_name;
    private int id;

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public String getName() {
        return name;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public int getId() {
        return id;
    }

    public void setProfile_image_url(String value) {
        profile_image_url = value;
    }

    public void setName(String value) {
        name = value;
    }

    public void setScreen_name(String value) {
        screen_name = value;
    }

    public void setId(int value) {
        id = value;
    }
}
