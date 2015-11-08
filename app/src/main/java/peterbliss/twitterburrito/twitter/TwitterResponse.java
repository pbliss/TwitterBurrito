package peterbliss.twitterburrito.twitter;

import org.json.JSONObject;

/**
 * Created by pbliss on 11/7/2015.
 */
public class TwitterResponse {
    private boolean status;
    private TwitterError error = null;
    private JSONObject jsonObject;

    public TwitterError getError()
    {
        return error;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
    public void setJsonObject(JSONObject value) {
        jsonObject = value;
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
