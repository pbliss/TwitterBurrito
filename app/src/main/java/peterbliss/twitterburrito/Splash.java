package peterbliss.twitterburrito;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class Splash extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
