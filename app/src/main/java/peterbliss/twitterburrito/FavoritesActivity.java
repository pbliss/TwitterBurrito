package peterbliss.twitterburrito;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import peterbliss.twitterburrito.R;
import peterbliss.twitterburrito.controllers.KeywordsController;
import peterbliss.twitterburrito.controllers.TweetController;
import peterbliss.twitterburrito.models.Tweet;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        List<Tweet> favorites = TweetController.getAllFavorites(KeywordsController.keywordList);
    }
}
