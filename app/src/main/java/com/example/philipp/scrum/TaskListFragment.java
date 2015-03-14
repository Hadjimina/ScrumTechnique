package com.example.philipp.scrum;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class TaskListFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inform the fragment about current Project and category
        Project currentProject = ((ProjectActivity) getActivity()).getProject();
        int category = (int) getArguments().get("category");

        /**
         * --- Adapter Creation ---
         *
         * We could at this point also create a custom adapter that returns ListView items based on
         * the given Task object, but it's easier to make an ArrayList<String> amd copy the names
         * of the tasks into it. Then we can use a standard ArrayAdapter<String>.
         */
        ArrayList<String> titleList;
        {
            // This method gives us the Task list for a given project and a given category.
            List<Task> categoryTaskList = currentProject.getCategoryTaskList(category);

            // Create an ArrayList that will hold all the titles
            titleList = new ArrayList<String>();

            // Fill it with the titles
            for(int i = 0; i < categoryTaskList.size(); i++)
            {
                Task currentTask = categoryTaskList.get(i);
                String taskTitle = currentTask.getTitle();
                titleList.add(taskTitle);
            }
        }


        // Make an Array Adapter that uses titleList to fill the listView
        Context context = getActivity().getApplicationContext();
        ArrayAdapter<String> taskListAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, titleList);

        // Get the layout, get the tasksListView from it and apply the adapter created above
        View layout = inflater.inflate(R.layout.fragment_tasklist, null);
        ListView tasksListView = (ListView) layout.findViewById(R.id.task_list_view);
        tasksListView.setAdapter(taskListAdapter);

        return layout;
    }
}
