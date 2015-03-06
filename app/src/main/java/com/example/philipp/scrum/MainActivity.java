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

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    ArrayList<String> projectsArray = new ArrayList<String>();
    ArrayAdapter<String> projectsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().setTitle(R.string.main_activity_title);

        final ListView projectsListView = (ListView) findViewById(R.id.projectsListView);
        projectsListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, projectsArray);
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

    public void createNewProject(String name)
    {
        // Check if name is empty (name has been trimmed before)
        if (name.length() > 0)
        {
            // Add it to the list of projects. TODO: make it an actual object
            projectsArray.add(name);
            projectsListAdapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(this, R.string.toast_project_name_empty, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

            DialogFragment myDialogFragment = new ProjectDialogFragment();

            myDialogFragment.show(getFragmentManager(), "addProjectDialog");
            return true;

    }
}