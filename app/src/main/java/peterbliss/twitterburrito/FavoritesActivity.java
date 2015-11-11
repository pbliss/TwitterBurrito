package peterbliss.twitterburrito;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import peterbliss.twitterburrito.controllers.KeywordsController;
import peterbliss.twitterburrito.controllers.TweetController;
import peterbliss.twitterburrito.models.Tweet;

public class FavoritesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView favoriteImage;
    private LinearLayout cardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        //setup the toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        //turn on the <-
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Favorites");

        //handle the <- click to go back to the main activity
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });

        //get the favorites list for this keyword
        List<Tweet> favorites = TweetController.getAllFavorites(KeywordsController.keywordList);

        //get the card view and the favorite image
        cardFragment = (LinearLayout) findViewById(R.id.favoritescards);
        favoriteImage = (ImageView) findViewById(R.id.favoritesLogo);

        //setup handling of the background image and cards fragment
        if(favorites.size() > 0) {

            //hide the no favorites image
            favoriteImage.setVisibility(View.GONE);

            //setup the cardview scroll
            NestedScrollView scrollView = (NestedScrollView)findViewById(R.id.nestedScrollview);
            CoordinatorLayout.LayoutParams params =
                    (CoordinatorLayout.LayoutParams) scrollView.getLayoutParams();
            AppBarLayout.ScrollingViewBehavior behavior =
                    (AppBarLayout.ScrollingViewBehavior) params.getBehavior();
            behavior.setOverlayTop(170); // Note: in pixels

            //instantiate the fragment for the cardview
            //-1 will signify the use of the favorites
            KeywordTabFragment fragment = new KeywordTabFragment();
            Bundle b = new Bundle();
            b.putInt("idx", -1);
            fragment.setArguments(b);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.favoritescards, fragment).commit();
        }
        else
        {
            //show the no favorites graphic
            favoriteImage.setVisibility(View.VISIBLE);
        }
    }
}
