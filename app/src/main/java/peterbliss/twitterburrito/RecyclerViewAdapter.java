package peterbliss.twitterburrito;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import peterbliss.twitterburrito.models.Tweet;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TweetViewHolder> {

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

    List<Tweet> tweets;

    RecyclerViewAdapter(List<Tweet> tweets){
        this.tweets = tweets;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tweet_card, viewGroup, false);
        return new TweetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder tweetViewHolder, int i) {
        tweetViewHolder.favorite_count.setText(String.valueOf(tweets.get(i).getFavorite_count()));
        tweetViewHolder.retweet_count.setText(String.valueOf(tweets.get(i).getRetweet_count()));
        tweetViewHolder.screen_name.setText("@" + tweets.get(i).getUser().getScreen_name());
        tweetViewHolder.name.setText(tweets.get(i).getUser().getName());
        tweetViewHolder.text.setText(tweets.get(i).getText());
        tweetViewHolder.created_at.setText(tweets.get(i).getCreated_at().toString());

        //TODO
        //tweetViewHolder.thumbnail
    }

    @Override
    public int getItemCount() {
        if(tweets != null) {
            return tweets.size();
        }
        return 0;
    }
}