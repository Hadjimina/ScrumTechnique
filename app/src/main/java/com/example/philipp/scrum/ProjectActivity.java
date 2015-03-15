package com.example.philipp.scrum;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

public class ProjectActivity extends ActionBarActivity {

    Everything everything = new Everything();
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        // Get Project position...
        int projectPosition = (int) getIntent().getExtras().get("itemPosition");

        // ... and get the whole project
        everything.load(getApplicationContext());
        Project currentProject = everything.getProject(projectPosition);

        // and use its title as ActionBar title
        CharSequence title = currentProject.getName();
        getSupportActionBar().setTitle(title);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        // If the user clicked the add button...
        if(id == R.id.action_add_task)
        {
            // Show up a dialog to add a new Task when the user clicks the button
            AddTaskFragment dialog = new AddTaskFragment();

            // Pass the current category to the dialog as an agrument
            // -1 so that the first category, To Do, becomes the 0th item
            int category = pager.getCurrentItem() - 1;

            if(category == -1)
            {
                // In this case the fragment was overview, so let's just set the Category to "To Do"
                category = 0;
            }

            Bundle dialogArgs = new Bundle();
            dialogArgs.putInt("category", category);
            dialog.setArguments(dialogArgs);
            dialog.show(this.getFragmentManager(), "MyDialogFragment");
        }
        return true;
    }

    public void createTask(String title, String description, int category, String year, String month, String day)
    {
        // Load everything
        Everything everything = new Everything();
        everything.load(getApplicationContext());

        // Create Task
        Task taskToAdd = new Task(title, description, category, year, month, day);

        // Get the current project position
        int projectPosition = (int) getIntent().getExtras().get("itemPosition");
        Project currentProject = everything.getProject(projectPosition);
        currentProject.addTask(taskToAdd, projectPosition, getApplicationContext());
    }

}
