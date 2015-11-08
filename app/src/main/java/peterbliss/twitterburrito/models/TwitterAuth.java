package peterbliss.twitterburrito.models;

/**
 * Created by pbliss on 11/8/2015.
 */
public class TwitterAuth {
    private String token_type;
    private String access_token;

    public String getToken_type() {
        return token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setToken_type(String value) {
        token_type = value;
    }

    public void setAccess_token(String value) {
        access_token = value;
    }
}
