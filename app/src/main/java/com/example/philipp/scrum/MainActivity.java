package com.example.philipp.scrum;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    ListAdapter projectsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().setTitle(R.string.main_activity_title);

        // final so that it can be accessed from the inner class at setOnItemClickListener.
        final ListView projectsListView = (ListView) findViewById(R.id.projectsListView);

        // Get the current Everything object and get its project list for the list adapter
        Everything everything = new Everything();
        everything.load();
        everything.addProject(new Project("this is a name", "this is a description"));
        List<Project> projectsList = everything.getProjectList();

        //
        projectsListAdapter = new ProjectsListAdapter(this, android.R.id.text1);
        projectsListView.setAdapter(projectsListAdapter);

        // ListView Item Click Listener
        projectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int itemPosition, long id) {

                // ListView Clicked item value
                String itemValue = (String) projectsListView.getItemAtPosition(itemPosition);

                // Start ProjectActivity and pass it the selected Project
                Intent myIntent = new Intent(MainActivity.this, ProjectActivity.class);
                myIntent.putExtra("projectName", itemValue);

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
            everything.load();

            // Create & add the Project
            everything.addProject(new Project(name, description));

            // Save everything
            everything.save();

            refreshList();
        }
        else
        {
            Toast.makeText(this, R.string.toast_project_name_empty, Toast.LENGTH_SHORT).show();
        }
    }

    public void refreshList()
    {
        Toast.makeText(this, "List should be refreshed", Toast.LENGTH_SHORT).show();
        // projectsListAdapter.notifyDatasetChanged();
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
        if(id == R.id.action_add_task)
        {
            // ...Show up the dialog for adding a new project
            DialogFragment myDialogFragment = new ProjectDialogFragment();
            myDialogFragment.show(getFragmentManager(), "addProjectDialog");
        }
        return true;
    }
}