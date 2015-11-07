package peterbliss.twitterburrito.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import peterbliss.twitterburrito.R;

public class Splash extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //setup the realm data store defaults
        RealmConfiguration config = new RealmConfiguration.Builder(getBaseContext()).build();
        Realm.setDefaultConfiguration(config);

        new Handler().postDelayed(new Runnable() {
            /*
            * Showing splash screen with a timer.
            */
            @Override
            public void run() {
                //handle some initial data loading

            }
        }, SPLASH_TIME_OUT); //this does assume we will be done in the timeout period
    }
}
