package com.example.philipp.scrum;

import android.content.Context;
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

    private int projectPosition = 0;
    private Context context;

    public Context getContext()
    {
        return this.context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }
    public int getProjectPosition()
    {
        return this.projectPosition;
    }

    public void setProjectPosition(int position)
    {
        this.projectPosition = position;
    }

    // Default Constructor
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

    public void notifyDatasetChanged() {
        super.notifyDataSetChanged();

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

        // Initialise the fragment that will later be returned
        Fragment returningFragment;

        // For the leftmost position we generate an overview Fragment. Pass it the project position
        // so that it can change the description
        if(position == 0)
        {
            returningFragment = new ProjectOverviewFragment();
            Bundle fragmentArgs = new Bundle();
            fragmentArgs.putInt("project", this.projectPosition);
            returningFragment.setArguments(fragmentArgs);
        }
        // For all other positions, we make a TaskListFragment and tell it which category to display
        else
        {
            returningFragment = TaskListFragment.newInstance(this.context);
            Bundle fragmentArgs = new Bundle();

            // Pass the returning Fragment some arguments - the current category and project
            // position - 1 = category
            fragmentArgs.putInt("category", position - 1);
            fragmentArgs.putInt("project", this.projectPosition);
            returningFragment.setArguments(fragmentArgs);
        }
        return returningFragment;
    }
}