package peterbliss.twitterburrito;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import peterbliss.twitterburrito.controllers.KeywordsController;

/**
 * Created by pbliss on 11/9/2015.
 */
public class TabFragmentAdapter extends FragmentPagerAdapter {
    private String tabTitles[];
    private Context context;

    public TabFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        if(KeywordsController.keywordList != null) {
            //setup the tab titles
            tabTitles = new String[KeywordsController.keywordList.size()];

            for (int i = 0; i < KeywordsController.keywordList.size(); i++) {
                tabTitles[i] = KeywordsController.keywordList.get(i).getKeyword();
            }
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return KeywordTabFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
