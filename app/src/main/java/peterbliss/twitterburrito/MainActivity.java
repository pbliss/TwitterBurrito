package peterbliss.twitterburrito;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import io.realm.RealmList;
import peterbliss.twitterburrito.controllers.KeywordsController;

public class MainActivity extends AppCompatActivity {
    private ImageButton favoritesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoritesButton = (ImageButton) findViewById(R.id.openFavorites);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }
        });

        KeywordsController.keywordList = new RealmList<>();

        //call the keywords setup
        //defaulting to 5
        KeywordsController.addKeyword("android");
        KeywordsController.addKeyword("angularJS");
        KeywordsController.addKeyword("java");
        KeywordsController.addKeyword("burrito");
        KeywordsController.addKeyword("beer");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}