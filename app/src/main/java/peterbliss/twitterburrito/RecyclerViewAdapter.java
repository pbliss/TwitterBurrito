package peterbliss.twitterburrito;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.android.volley.toolbox.ImageLoader;
import io.realm.Realm;
import io.realm.RealmList;
import peterbliss.twitterburrito.models.Tweet;
import peterbliss.twitterburrito.util.VolleySingleton;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TweetViewHolder> {

    private ToggleButton favoriteButton;
    private RealmList<Tweet> tweetList;

    public static class TweetViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        com.android.volley.toolbox.NetworkImageView thumbnail;
        TextView name;
        TextView screen_name;
        TextView text;
        TextView retweet_count;
        TextView favorite_count;
        TextView created_at;

        TweetViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            thumbnail = (com.android.volley.toolbox.NetworkImageView) itemView.findViewById(R.id.thumbnail);
            name = (TextView)itemView.findViewById(R.id.name);
            screen_name = (TextView)itemView.findViewById(R.id.screen_name);
            text = (TextView) itemView.findViewById(R.id.text);
            retweet_count = (TextView) itemView.findViewById(R.id.retweet_count);
            favorite_count = (TextView) itemView.findViewById(R.id.favorite_count);
            created_at = (TextView) itemView.findViewById(R.id.created_at);
        }
    }

    RecyclerViewAdapter(RealmList<Tweet> tweetlist){
        tweetList = tweetlist;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tweet_card, viewGroup, false);

        favoriteButton = (ToggleButton) v.findViewById(R.id.favoriteTweetToggle);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                //begin our write transaction
                realm.beginTransaction();
                tweetList.get((int) v.getTag()).setFavorited(((ToggleButton) v).isChecked());
                realm.commitTransaction();
            }
        });

        return new TweetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder tweetViewHolder, int i) {

        //load the tweet data to the holder
        tweetViewHolder.favorite_count.setText(String.valueOf(tweetList.get(i).getFavorite_count()));
        tweetViewHolder.retweet_count.setText(String.valueOf(tweetList.get(i).getRetweet_count()));
        tweetViewHolder.screen_name.setText("@" + tweetList.get(i).getUser().getScreen_name());
        tweetViewHolder.name.setText(tweetList.get(i).getUser().getName());
        tweetViewHolder.text.setText(tweetList.get(i).getText());
        tweetViewHolder.created_at.setText(tweetList.get(i).getCreated_at().toString());
        tweetViewHolder.itemView.findViewById(R.id.favoriteTweetToggle).setTag(i);

        //toggle button if it has been previously favorited
        if (tweetList.get(i).getFavorited()) {
            ((ToggleButton) tweetViewHolder.itemView.findViewById(R.id.favoriteTweetToggle)).setChecked(true);
        }

        //pull down the profile image
        ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();
        tweetViewHolder.thumbnail.setImageUrl(tweetList.get(i).getUser().getProfile_image_url(), imageLoader);
        tweetViewHolder.thumbnail.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        if(tweetList != null) {
            return tweetList.size();
        }
        return 0;
    }
}