package com.example.android.wellnessjournal.Food;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Maryline on 10/12/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                GridTab gridTab = new GridTab();
                return gridTab;
            case 1:
                DetailsTab detailsTab = new DetailsTab();
                return detailsTab;
            case 2:
                ScoreTab scoreTab = new ScoreTab();
                return scoreTab;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
