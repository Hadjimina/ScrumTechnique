package com.example.philipp.scrum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * This class is a FragmentPagerAdapter. It creates the Fragments for the ViewPager depending on
 * its position.
 */

public class PageAdapter extends FragmentPagerAdapter {

    // The array of titles for the various pages. First comes the overview, then all the categories.
    CharSequence[] titles = {"Overview","To Do","Emergency","In Progress","Testing","Completed"};

    // Constructor is already implemented by the superclass, so we use that.
    public PageAdapter(FragmentManager fm)
    {
        super(fm);
    }

    /**
     * @param position - The position of the title to be returned
     * @return The category title corresponding to the given position
     */
    @Override
    public CharSequence getPageTitle(int position)
    {
        return titles[position];
    }

    /**
     * @return The number of titles = number of viewPager pages
     */
    @Override
    public int getCount() {
        return titles.length;
    }

    /**
     * @param position - The position of the Fragment to be returned
     * @return The Fragment that corresponds to the given position. It will be displayed in the
     *         ViewPager in ProjectActivity.
     */
    @Override
    public Fragment getItem(int position)
    {

        Fragment returningFragment = null;

        if(position == 0)
        {
            returningFragment = new TaskOverviewFragment();
        }
        else
        {
            returningFragment = new TaskListFragment();
            Bundle fragmentArgs = new Bundle();

            // position - 1 = category
            fragmentArgs.putInt("category", position - 1);
            returningFragment.setArguments(fragmentArgs);
        }
        return returningFragment;
    }
}