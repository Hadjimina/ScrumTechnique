package com.example.philipp.scrum;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    // Never make this a ListAdapter again, otherwise notifyDataSetChanged() will cease working
    ArrayAdapter<Project> projectsListAdapter; // = new ProjectsListAdapter(this, android.R.id.text1);
    List<Project> projectList;
    ListView projectsListView;

    /**
     * Is called when the Activity is first instantiated
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //TODO change colour of actionbar

        // Create & apply the corresponding layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link the listView in the layout to an android.widget.ListView object
        // final so that it can be accessed from the inner class at setOnItemClickListener.
        projectsListView = (ListView) findViewById(R.id.projectsListView);

        // Get the current list of projects
        Everything everything = new Everything();
        everything.load(getApplicationContext());
        projectList = everything.getProjectList();

        // Make the list adapter, pass it the project list and assign it to the list view
        projectsListAdapter = new ProjectsListAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, projectList);
        projectsListView.setAdapter(projectsListAdapter);

        // ListView Item Click Listener
        projectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int itemPosition, long id) {
                // Start ProjectActivity and pass it the index of selected Project
                Intent myIntent = new Intent(MainActivity.this, ProjectActivity.class);

                // Only the itemPosition is necessary, the Project can then be loaded
                // in ProjectActivity and is reused by all the fragments
                myIntent.putExtra("itemPosition", itemPosition);


                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    public void createNewProject(String name, String description)
    {
        // Check if name is empty (name has been trimmed before)
        if (name.length() > 0)
        {
            // Load everything
            Everything everything = new Everything();
            everything.load(getApplicationContext());

            // Create & add the Project
            everything.addProject(new Project(name, description));

            //Update projectList (before saving so UI comes first)
            List<Project> projectList = everything.getProjectList();
            projectsListAdapter = new ProjectsListAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, projectList);
            projectsListView.setAdapter(projectsListAdapter);
            projectsListAdapter.notifyDataSetChanged();

            // Save everything
            everything.save(getApplicationContext());


        }
        else
        {
            Toast.makeText(this, R.string.toast_project_name_empty, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        // If the user clicked the add button...
        if(id == R.id.action_add_project)
        {
            // ...Show up the dialog for adding a new project
            DialogFragment myDialogFragment = new ProjectDialogFragment();
            myDialogFragment.show(getFragmentManager(), "addProjectDialog");
        }
        return true;
    }
}