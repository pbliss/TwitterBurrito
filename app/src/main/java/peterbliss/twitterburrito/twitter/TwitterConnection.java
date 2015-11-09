package peterbliss.twitterburrito.twitter;

import android.os.AsyncTask;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;

import peterbliss.twitterburrito.models.TwitterError;
import peterbliss.twitterburrito.models.TwitterResponse;

/**
 * Created by pbliss on 11/7/2015.
 */
public class TwitterConnection extends AsyncTask<TwitterRequest, Void, TwitterResponse> {
    private boolean finished;
    private boolean ready;
    private boolean executing;

    public interface AsyncResponse {
        void processFinish(TwitterResponse output);
    }

    public AsyncResponse delegate=null;

    private void WebServiceConnection()
    {
        this.ready = true;
        this.executing = false;
        this.finished = false;
    }

    public void setResonseCallback(AsyncResponse r) {
        delegate = r;
    }

    @Override
    protected void onPostExecute(TwitterResponse response) {
        delegate.processFinish(response);
    }

    @Override
    protected TwitterResponse doInBackground(TwitterRequest... request) {
        TwitterResponse response = new TwitterResponse();

        try {
            HttpURLConnection connection = request[0].buildUrlRequest();
            connection.connect();

            if(request[0].getParamString() != null && request[0].getParamString() != "") {
                OutputStream os = connection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                osw.write(request[0].getParamString());
                osw.flush();
                osw.close();
            }

            InputStream in = new BufferedInputStream(connection.getInputStream());
            try {
                response = readStream(in);
            }
            catch(Exception exception) {
                TwitterError error = new TwitterError();
                error.setMessage(exception.getMessage());

                //TODO get proper error code
                error.setCode(0);
                response.setError(error);
            }
            finally {
                in.close();
            }

            executing = true;

        }
        catch(Exception exception) {
            this.executing = false;

            TwitterError error = new TwitterError();
            error.setMessage(exception.getMessage());
            error.setCode(100);
            response.setError(error);
        }

        return response;
    }

    public void cancel()
    {
        if (this.executing) {
            //[this.connection cancel];
        }
        this.executing = false;
        this.finished = true;
    }

    private TwitterResponse readStream(InputStream in) {

        TwitterResponse response = new TwitterResponse();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // The request is complete and data has been received
        // You can parse the stuff in your instance variable now
        this.executing = false;
        this.finished = true;

        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            JSONObject j = new JSONObject(responseStrBuilder.toString());

            response = new TwitterResponse();
            response.setStatus(true);
            response.setJsonObject(j);
        }
        catch(org.json.JSONException ex) {
            System.out.println(ex.getMessage());
            response.setStatus(false);
        }
        catch (java.io.IOException e) {
            System.out.println(e.getMessage());
            response.setStatus(false);
        }

        return response;
    }
}
