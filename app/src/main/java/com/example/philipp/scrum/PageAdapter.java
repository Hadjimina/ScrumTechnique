package com.example.philipp.scrum;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    String[] titles = { "Overview", "To Do", "Emergency", "In Progress","Testing" ,"Completed"};
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
    @Override
    public int getCount() {
        return 6;
    }
    @Override
    public Fragment getItem(int position) {
        return (position == 0)? new A_OverView() : new F_Completed() ;
    }
}