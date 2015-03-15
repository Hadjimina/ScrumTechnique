package com.example.philipp.scrum;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


public class TaskListFragment extends Fragment
{
    TaskListAdapter taskListAdapter;
    ListView tasksListView;
    LayoutInflater inflater;
    ProjectActivity parentActivity;
    Context context;

    public static TaskListFragment newInstance(Context context)
    {
        TaskListFragment tlf = new TaskListFragment();
        tlf.context = context;
        return tlf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final List<Task> taskList = getRefreshedTasklist(getActivity().getApplicationContext());

        // Make a new TaskListAdapter using the taskList we extracted from the Project above
        final Context context = getActivity().getApplicationContext();
        taskListAdapter = new TaskListAdapter(context, android.R.layout.simple_list_item_1, taskList);

        // Get the layout, get the tasksListView from it and apply the adapter created above
        this.inflater = inflater;
        View layout = inflater.inflate(R.layout.fragment_tasklist, null);
        tasksListView = (ListView) layout.findViewById(R.id.task_list_view);
        tasksListView.setAdapter(taskListAdapter);

        //LongClickListener will remove the selected task
        tasksListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {

                // Load everything
                Everything everything = new Everything();
                everything.load(context);

                // Get the project position from the argument and use it to get the whole project object
                int projectPosition = (int) getArguments().get("project");
                Project currentProject = everything.getProject(projectPosition);

                //Get category
                int category = (int) getArguments().get("category");

                //remove task
                currentProject.removeTask(category,pos);

                //Update projectList (before saving so UI comes first)

                taskListAdapter = new TaskListAdapter(context, android.R.layout.simple_list_item_1, currentProject.getCategoryTaskList(category));
                tasksListView.setAdapter(taskListAdapter);
                taskListAdapter.notifyDataSetChanged();

                // Save everything
                everything.save(context);



                return true;
            }
        });

        return layout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = (ProjectActivity) activity;
    }

    /**
     * This method makes a new taskListAdapter using the most up-to-date task list, and then sets it
     * as the listView's new adapter.
     *
     * @param context - because it calls getRefreshedTaskList() which needs a context for load()
     */
    public void refresh(Context context)
    {
        super.onResume();

        List<Task> taskList = getRefreshedTasklist(context);

        // taskListAdapter becomes a new adapter
        taskListAdapter = new TaskListAdapter(context, android.R.id.text1, taskList);
        // Give it the new task list
        // taskListAdapter.setItems(taskList);

        // Inflate the task list layout and get the List view out of it
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View layout = layoutInflater.inflate(R.layout.fragment_tasklist, null);
        tasksListView = (ListView) layout.findViewById(R.id.task_list_view);

        // Assign the adapter to the list view
        tasksListView.setAdapter(taskListAdapter);

    }

    /**
     * Simple method for getting the fresh task List. It is used in onCreateView and onResume.
     *
     * @param context - because Everything.load() is called and it needs a context.
     * @return the List<Task> containing the tasks from the current project and the right category.
     */
    public List<Task> getRefreshedTasklist(Context context)
    {
        // Load everything
        Everything everything = new Everything();
        everything.load(context);

        // Get the project position from the argument and use it to get the whole project object
        int projectPosition = (int) getArguments().get("project");
        Project currentProject = everything.getProject(projectPosition);

        int category = (int) getArguments().get("category");
        return currentProject.getCategoryTaskList(category);
    }
}
