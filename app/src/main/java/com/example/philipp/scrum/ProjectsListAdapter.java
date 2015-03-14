package com.example.philipp.scrum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * An object of this class can return a ListView item in the form of an android.view.View that
 * represents a Project
 *
 * Created by balduin on 2015-03-12.
 */
public class ProjectsListAdapter extends ArrayAdapter<Project>
{

    public ProjectsListAdapter(Context context, int textViewResourceId)
    {
        super(context, textViewResourceId);
    }

    public ProjectsListAdapter(Context context, int resource, List<Project> items)
    {
        super(context, resource, items);
    }

    /**
     * Given an index which project to display, this method returns a view which contains a TextView
     * which in turn is filled with the name of the project. The returned view is used as a ListView
     * item in MainActivity.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (convertView == null)
        {
            LayoutInflater inflater;
            inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        Everything everything = new Everything();
        everything.load(getContext());
        Project p = everything.getProject(position);

        if(p != null)
        {
            TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
            textView.setText(p.getName());
        }

        return convertView;
    }
}
