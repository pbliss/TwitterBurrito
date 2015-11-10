package peterbliss.twitterburrito;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import peterbliss.twitterburrito.controllers.KeywordsController;
import peterbliss.twitterburrito.controllers.TweetController;

public class KeywordTabFragment extends Fragment {

    private int keywordIDX;
    private View progressView;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 2;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    public static KeywordTabFragment newInstance(int k) {
        KeywordTabFragment fragment = new KeywordTabFragment();
        Bundle args = new Bundle();
        args.putInt("idx", k);
        fragment.setArguments(args);
        return fragment;
    }

    public KeywordTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //index of the keyword list for this keyword
        keywordIDX = getArguments().getInt("idx");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_keyword_tab, container, false);

        progressView = view.findViewById(R.id.tweetprogress);

        //setup the recyclerview
        final RecyclerView rv = (RecyclerView)view.findViewById(R.id.rv);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(linearLayoutManager);

        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {

            //adapted from
            //http://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = rv.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    doRefresh(rv);
                    loading = true;
                }
            }
        });

        doRefresh(rv);

        return view;
    }

    private void doRefresh(final RecyclerView rv) {
        showProgress(true);
        //call refresh on the tweet list
        TweetController.refreshTweets(KeywordsController.keywordList.get(keywordIDX), new TweetController.AsyncResponse() {
            @Override
            public void processFinish() {
                showProgress(false);
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(keywordIDX);
                rv.setAdapter(recyclerViewAdapter);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
