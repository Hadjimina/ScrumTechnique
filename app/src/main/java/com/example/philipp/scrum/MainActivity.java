package com.example.philipp.scrum;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
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
    public static Activity activity;
    Context context;

    /**
     * Is called when the Activity is first instantiated
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Create & apply the corresponding layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;


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

        projectsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int projectPosition = position;

                AlertDialog.Builder build = new AlertDialog.Builder(activity);
                build.setMessage("Do you want to delete this project?");
                build.setCancelable(true);

                /**
                 * Set the positive button. On its click, the current Everything is loaded, the
                 * project that has been long-pressed is removed from the projectsList and the
                 * Everything object is saved again. Then the ProjectsListAdapter is set again to
                 * refresh the contents of the list.
                 */

                build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Load the current Everything object
                        Everything everything = new Everything();
                        everything.load(getApplicationContext());

                        // Delete the project that has been long-pressed
                        everything.removeProject(projectPosition);

                        // Save again
                        everything.save(getApplicationContext());

                        //Refresh the List by making a new ProjectsListAdapter...
                        projectsListAdapter = new ProjectsListAdapter(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                everything.getProjectList()
                        );

                        // ...assigning it to the List view...
                        projectsListView.setAdapter(projectsListAdapter);

                        // ...and refreshing it.
                        projectsListAdapter.notifyDataSetChanged();

                    }
                });

                // Set negative button to a simple "No" cancelling the dialog without further action
                build.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                build.show();

                return true;
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