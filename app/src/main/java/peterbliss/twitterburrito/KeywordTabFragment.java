package peterbliss.twitterburrito;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class KeywordTabFragment extends Fragment {

    private int keywordIDX;

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
        return inflater.inflate(R.layout.fragment_keyword_tab, container, false);
    }
}
