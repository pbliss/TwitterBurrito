package peterbliss.twitterburrito;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import io.realm.Realm;
import peterbliss.twitterburrito.controllers.KeywordsController;
import peterbliss.twitterburrito.models.Keyword;
import peterbliss.twitterburrito.models.Tweet;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TweetViewHolder> {

    private int keywordIndex;
    private ToggleButton favoriteButton;

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

    RecyclerViewAdapter(int idx){
        this.keywordIndex = idx;
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
                KeywordsController.keywordList.get(keywordIndex).getTweetList().get((int) v.getTag()).setFavorited(((ToggleButton)v).isChecked());
                realm.commitTransaction();
            }
        });

        return new TweetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder tweetViewHolder, int i) {
        tweetViewHolder.favorite_count.setText(String.valueOf(KeywordsController.keywordList.get(keywordIndex).getTweetList().get(i).getFavorite_count()));
        tweetViewHolder.retweet_count.setText(String.valueOf(KeywordsController.keywordList.get(keywordIndex).getTweetList().get(i).getRetweet_count()));
        tweetViewHolder.screen_name.setText("@" + KeywordsController.keywordList.get(keywordIndex).getTweetList().get(i).getUser().getScreen_name());
        tweetViewHolder.name.setText(KeywordsController.keywordList.get(keywordIndex).getTweetList().get(i).getUser().getName());
        tweetViewHolder.text.setText(KeywordsController.keywordList.get(keywordIndex).getTweetList().get(i).getText());
        tweetViewHolder.created_at.setText(KeywordsController.keywordList.get(keywordIndex).getTweetList().get(i).getCreated_at().toString());
        tweetViewHolder.itemView.findViewById(R.id.favoriteTweetToggle).setTag(i);

        //toggle button if it has been previously favorited
        if(KeywordsController.keywordList.get(keywordIndex).getTweetList().get(i).getFavorited()) {
            ((ToggleButton) tweetViewHolder.itemView.findViewById(R.id.favoriteTweetToggle)).setChecked(true);
        }


        //TODO
        //tweetViewHolder.thumbnail
    }

    @Override
    public int getItemCount() {
        if(KeywordsController.keywordList.get(keywordIndex).getTweetList() != null) {
            return KeywordsController.keywordList.get(keywordIndex).getTweetList().size();
        }
        return 0;
    }
}