package peterbliss.twitterburrito;

import android.app.Application;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import peterbliss.twitterburrito.models.TwitterAuth;
import peterbliss.twitterburrito.twitter.TwitterAuthenticate;

/**
 * Created by pbliss on 11/11/2015.
 */

public class TwitterAuthTest extends AndroidTestCase {
    public TwitterAuthTest() {
        try {
            final CountDownLatch signal = new CountDownLatch(1);

            TwitterAuthenticate.authenticateConnection(new TwitterAuthenticate.AsyncResponse() {
                @Override
                public void processFinish(TwitterAuth auth) {
                    assertEquals("bearer", auth.getToken_type());
                    assertNotNull(auth.getAccess_token());
                }
            });

            signal.await(30, TimeUnit.SECONDS);
        }
        catch (java.lang.InterruptedException ex) {

        }
    }
}