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
        final ListView projectsListView = (ListView) findViewById(R.id.projectsListView);

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

                // ListView Clicked item value
                String projectName = projectList.get(itemPosition).getName();
                String projectDesc = projectList.get(itemPosition).getDescription();

                // Start ProjectActivity and pass it the selected Project
                Intent myIntent = new Intent(MainActivity.this, ProjectActivity.class);
                myIntent.putExtra("projectName", projectName);
                myIntent.putExtra("projectDesc", projectDesc);

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

            // Save everything
            everything.save(getApplicationContext());

            //Update projectList
            projectList.clear();
            projectList = everything.getProjectList();

          //  projectsListAdapter.clear();
            projectsListAdapter.addAll(projectList);
            projectsListAdapter.notifyDataSetChanged();
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
        if(id == R.id.action_add_task)
        {
            // ...Show up the dialog for adding a new project
            DialogFragment myDialogFragment = new ProjectDialogFragment();
            myDialogFragment.show(getFragmentManager(), "addProjectDialog");
        }
        return true;
    }
}