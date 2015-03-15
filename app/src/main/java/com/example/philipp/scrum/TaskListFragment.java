package com.example.philipp.scrum;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;


public class TaskListFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inform the fragment about current Project and category
        Project currentProject = ((ProjectActivity) getActivity()).getProject();
        int category = (int) getArguments().get("category");
        List<Task> taskList = currentProject.getCategoryTaskList(category);

        // Make a new TaskListAdapter using the taskList we extracted from the Project above
        Context context = getActivity().getApplicationContext();
        TaskListAdapter taskListAdapter = new TaskListAdapter(context, android.R.layout.simple_list_item_1, taskList);

        // Get the layout, get the tasksListView from it and apply the adapter created above
        View layout = inflater.inflate(R.layout.fragment_tasklist, null);
        ListView tasksListView = (ListView) layout.findViewById(R.id.task_list_view);
        tasksListView.setAdapter(taskListAdapter);

        return layout;
    }
}
