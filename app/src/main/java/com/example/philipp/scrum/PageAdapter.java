package com.example.philipp.scrum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * This class is a FragmentPagerAdapter, it creates the Fragments for the ViewPager depending on
 * its position
 */

public class PageAdapter extends FragmentPagerAdapter {


    // The array of titles for the various pages. First comes the overview, then all the categories.
    CharSequence[] titles = {"Overview","To Do","Emergency","In Progress","Testing","Completed"};

    // Constructor is already implemented by the superclass, so we use that.
    public PageAdapter(FragmentManager fm)
    {
        super(fm);
    }

    // Returns the title of the page at the specified position
    @Override
    public CharSequence getPageTitle(int position)
    {
        return titles[position];
    }

    // Returns length of the title array = number of pages
    @Override
    public int getCount() {
        return titles.length;
    }

    // Returns the fragment that corresponds to the given position.
    @Override
    public Fragment getItem(int position)
    {

        Fragment returningFragment = null;

        if(position == 0)
        {
            returningFragment = new A_OverView();
        }
        else
        {
            returningFragment = new B_ToDo();
            Bundle fragmentArgs = new Bundle();
            fragmentArgs.putInt("category", position);
            returningFragment.setArguments(fragmentArgs);
        }
        return returningFragment;
    }
}