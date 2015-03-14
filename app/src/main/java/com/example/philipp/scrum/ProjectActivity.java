package com.example.philipp.scrum;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

public class ProjectActivity extends FragmentActivity {

    Everything everything = new Everything();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        // Get Project position...
        int projectPosition = (int) getIntent().getExtras().get("itemPosition");

        // ... and get the whole project
        everything.load(getApplicationContext());
        Project currentProject = everything.getProject(projectPosition);

        // and its title
        CharSequence title = currentProject.getName();

    }

    /**
     * Returns the project that is currently opened (the whole one, not the position). This is
     * better than using a new Everything each fragment, since only one Everything is ever created.
     *
     * @return The currently opened project
     */
    public Project getProject()
    {
        int itemPosition = (int) getIntent().getExtras().get("itemPosition");
        return everything.getProject(itemPosition);
    }

}
