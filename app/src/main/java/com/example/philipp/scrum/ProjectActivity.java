package com.example.philipp.scrum;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import java.util.GregorianCalendar;

public class ProjectActivity extends ActionBarActivity {

    ViewPager pager;
    PageAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        pager = (ViewPager) findViewById(R.id.pager);
        viewPagerAdapter = new PageAdapter(getSupportFragmentManager());
        viewPagerAdapter.setProjectPosition((int) getIntent().getExtras().get("itemPosition"));
        viewPagerAdapter.setContext(getApplicationContext());
        pager.setAdapter(viewPagerAdapter);

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setIndicatorColor(0xFF9C27B0);
        tabs.setViewPager(pager);

        // Get Project position...
        int projectPosition = (int) getIntent().getExtras().get("itemPosition");

        // ... and get the whole project
        Everything everything = new Everything();
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
        // Find out which project is currently opened
        int itemPosition = (int) getIntent().getExtras().get("itemPosition");

        // Load the current Everything object
        Everything everything = new Everything();
        everything.load(getApplicationContext());

        // And get the Project object out of it
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

        // If the user clicked the add button, show the Dialog that adds a new Task
        if(id == R.id.action_add_task)
        {
            // Show up a dialog to add a new Task when the user clicks the button
            AddTaskFragment dialog = new AddTaskFragment();

            // Pass the current category to the dialog as an argument
            // -1 so that the first category, To Do, becomes the 0th item
            int category = pager.getCurrentItem() - 1;

            if(category == -1)
            {
                // In this case the fragment was overview, so let's just set the Category to "To Do"
                category = 0;
            }

            // Tell the dialog in which category to create a task
            Bundle dialogArgs = new Bundle();
            dialogArgs.putInt("category", category);

            // It needs to make a new task, not edit an existing one
            dialogArgs.putBoolean("editTask", false);
            dialog.setArguments(dialogArgs);

            dialog.show(this.getFragmentManager(), "MyDialogFragment");
        }
        return true;
    }

    /**
     * - Load the current Everything
     * - Make a Task using the given parameters
     * - Add the Task to Everything
     * - save the Everything
     * - refresh each taskListFragment
     */
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

        /**
         * Refresh all the fragments using their refresh
         * Begin at one since 0 is the Overview fragment and crashes when cast to TaskListFragment
         * Decrement getCount() by 1 so that it stops at the last fragment again
         */
        for (int i = 1; i < viewPagerAdapter.getCount() - 1; i++) {
            TaskListFragment refreshingFragment = (TaskListFragment) viewPagerAdapter.getItem(i);
            refreshingFragment.refresh(getApplicationContext());
        }

        /**
         * Now that all the listViews are properly refreshed, we refresh the ViewPager. Because it
         * resets its position while refreshing, we save the position and set it to the saved
         * position after the refresh.
         */
        int page = pager.getCurrentItem();
        pager.setAdapter(viewPagerAdapter);
        pager.setCurrentItem(page);
    }

    /**
     * This method edits a task by giving it the values in taskInfo
     * @param taskInfo - A bundle with all the fields that belong in the task
     */
    public void editTask(Bundle taskInfo)
    {
        // Find out position and category
        int taskPosition = taskInfo.getInt("taskPosition");
        int taskCategory = taskInfo.getInt("category");

        // Load the current project
        Project currentProject = getProject();

        // Get the correct task from the project
        Task taskToEdit = currentProject.getTask(taskCategory, taskPosition);

        boolean moveTask = false;
        if(taskToEdit.getCategory() != taskCategory)
        {
            moveTask = true;
        }

        /**
         * The following block of code fills taskToEdit with the values in the parameter Bundle.
         */
        taskToEdit.setTitle(taskInfo.getString("taskName"));
        taskToEdit.setDescription(taskInfo.getString("taskDesc"));
        taskToEdit.setCategory(taskInfo.getInt("category"));

        int year = taskInfo.getInt("year");
        int month = taskInfo.getInt("month");
        int day = taskInfo.getInt("day");

        if(year != 0 && month != 0 && day != 0)
        {
            GregorianCalendar taskDate = new GregorianCalendar(year, month, day);
            taskToEdit.setDate(taskDate);
        }
        else
        {
            taskToEdit.setHasDate(false);
        }

        // Now that taskToEdit is filled with the new values, we may save it.
        if(!moveTask)
        {
            currentProject.setTask(taskCategory, taskPosition, taskToEdit);
        }
        else
        {
            //currentProject.removeTask(taskCategory, taskPosition);
            int projectPosition = getIntent().getExtras().getInt("itemPosition");
            currentProject.addTask(taskToEdit, projectPosition ,getApplicationContext());
        }

        Everything everything = new Everything();
        everything.load(getApplicationContext());

    }

    /**
     * This method updates the description of the currently opened project by setting it to the text
     * the user entered in the description field.
     *
     * Sometimes, when ProjectActivity has been open for a long time and the app has been put in the
     * background and opened again, descEditText can't be retrieved anymore and is a null object.
     * In that case the java.lang.IllegalStateException is caught. The user is then given feedback
     * whether the description was or was not saved.
     *
     * @param v
     * the view that was pressed, in this case it will always be the button R.id.update_description
     */
    public void updateDescription(View v)
    {
        boolean successful = false;
        try {
            // Get the layout of the overview page
            View layout = pager.getChildAt(0);

            // Get the EditText from the layout and the project index from the intent from MainActivity
            EditText descEditText = (EditText) layout.findViewById(R.id.project_description);
            int projectPosition = (int) getIntent().getExtras().get("itemPosition");

            // Load the current Everything object
            Everything everything = new Everything();
            everything.load(this);

            // Get the current project, set its description to the entered text and save it
            Project currentProject = everything.getProject(projectPosition);
            currentProject.setDescription(descEditText.getText().toString());
            everything.setProject(projectPosition, currentProject);

            // Save the modified Everything object with fancy exception handling
            everything.save(this);
            successful = true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            successful = false;
        } finally {
            if(successful) {
                Toast.makeText(this, "Description saved successfully", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Description could not be saved", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
