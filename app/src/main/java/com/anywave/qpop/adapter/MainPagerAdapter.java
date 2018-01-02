package com.anywave.qpop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 *
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
//    private String[] titles;
    private int[] subsectionIds;

    public MainPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        this(fm);
        this.fragments = fragments;
    }
    public MainPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles, int[] subsectionIds) {
        this(fm);
        this.fragments = fragments;
        this.subsectionIds = subsectionIds;
    }

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titles[position];
//    }

    public int getId(int position) {
        return subsectionIds[position];
    }

}
