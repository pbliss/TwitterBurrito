package peterbliss.twitterburrito.twitter;

/**
 * Created by pbliss on 11/7/2015.
 */
public class TwitterResponse {
    private boolean status;
    private TwitterError error = null;

    public TwitterError getError()
    {
        return error;
    }

    public void setError(TwitterError err) {
        error = err;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean value) {
        status = value;
    }

}
