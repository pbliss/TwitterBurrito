package peterbliss.twitterburrito.models;

import io.realm.RealmObject;

/**
 * Created by pbliss on 11/7/2015.
 */
public class User extends RealmObject {
    //truncated version of the twitter User object
    String profile_image_url;
    String name;
    String url;
    String screen_name;
    int id;

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
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

    public void setUrl(String value) {
        url = value;
    }

    public void setScreen_name(String value) {
        screen_name = value;
    }

    public void setId(int value) {
        id = value;
    }
}
