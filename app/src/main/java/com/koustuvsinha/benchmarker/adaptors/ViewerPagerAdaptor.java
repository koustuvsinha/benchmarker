package com.koustuvsinha.benchmarker.adaptors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.koustuvsinha.benchmarker.views.DbTestResultDetails;
import com.koustuvsinha.benchmarker.views.DbTestResultStatus;

import java.util.ArrayList;

/**
 * Created by koustuvsinha on 5/6/15.
 * ViewerPagerAdaptor class is a FragmentPagerAdaptor to manage the fragments
 * of the Result Activity
 */
public class ViewerPagerAdaptor extends FragmentPagerAdapter {
    private final ArrayList<Fragment> fragments;

    public ViewerPagerAdaptor(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Fragment fr = fragments.get(position);
        if(fr.getClass().equals(DbTestResultStatus.class)) {
            return DbTestResultStatus.PAGE_NAME;
        } else {
            return DbTestResultDetails.PAGE_NAME;
        }
    }
}
