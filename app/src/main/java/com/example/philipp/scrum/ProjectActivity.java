package com.example.philipp.scrum;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;


public class ProjectActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        //Get Project Title
        Intent intent = getIntent();
        String name = intent.getStringExtra("projectName");

        Bundle bundle = new Bundle();
        bundle.putString("edttext", "From Activity");

        // set Fragmentclass Arguments
        A_OverView fragobj = new A_OverView();
        fragobj.setArguments(bundle);

    }
}
