package com.sachet.bloodsearch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by AJ on 3/19/2018.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    Integer tabitems = 3;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Search";
            case 1:
                return "Donor List";
            case 2:
                return "Details";

        }
        return null;
    }



    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                SearchFragment searchFragment = new SearchFragment();
                return searchFragment;
            case 1:
                DonorListFragment donorListFragment = new DonorListFragment();
                return donorListFragment;
            case 2:
                DetailsFragment detailsFragment = new DetailsFragment();
                return detailsFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabitems;
    }
}
