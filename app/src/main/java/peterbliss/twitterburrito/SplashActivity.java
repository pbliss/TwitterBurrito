package peterbliss.twitterburrito;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import peterbliss.twitterburrito.controllers.KeywordsController;
import peterbliss.twitterburrito.models.TwitterAuth;
import peterbliss.twitterburrito.twitter.TwitterAuthenticate;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //setup the realm data store defaults
        RealmConfiguration config = new RealmConfiguration.Builder(getBaseContext()).build();
        Realm.setDefaultConfiguration(config);

        //authenticate against twitter api here
        TwitterAuthenticate.authenticateConnection(new TwitterAuthenticate.AsyncResponse() {
            @Override
            public void processFinish(TwitterAuth auth) {

            if (auth != null) {
                //now that its authenticated we can query up the keyword list
                KeywordsController.keywordList = new RealmList<>();

                //call the keywords setup
                //defaulting to 5

                //first keyword we will wait until it has loaded to continue
                //this will ensure we enter the app loaded ready to use, not spinning
                KeywordsController.addKeyword("android", new KeywordsController.AsyncResponse() {
                    @Override
                    public void processFinish() {
                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(i);
                        // close this activity
                        finish();
                    }
                });
                KeywordsController.addKeyword("angularJS");
                KeywordsController.addKeyword("java");
                KeywordsController.addKeyword("burrito");
                KeywordsController.addKeyword("beer");
            } else {
                //error during authentication
                Toast.makeText(getApplicationContext(), "Error authenticating twitter connection", Toast.LENGTH_SHORT);

                //todo need to do something from here
            }
            }
        });
    }
}
