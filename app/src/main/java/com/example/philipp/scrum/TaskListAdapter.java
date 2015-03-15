package com.example.philipp.scrum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by balduin on 2015-03-15.
 */
public class TaskListAdapter extends ArrayAdapter<Task>
{
    List<Task> items;

    public TaskListAdapter(Context context, int textViewResourceID)
    {
        super(context, textViewResourceID);
    }

    public TaskListAdapter(Context context, int textViewResourceID, List<Task> items)
    {
        super(context, textViewResourceID, items);
        this.items = items;
    }

    public void setItems(List<Task> items)
    {
        this.items = items;
    }

    /**
     * Given an index which project to display, this method returns a view which contains a TextView
     * which in turn is filled with the name of the project. The returned view is used as a ListView
     * item in MainActivity.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if (v == null)
        {
            LayoutInflater inflater;
            inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        Task t = items.get(position);

        if(t != null)
        {
            TextView textView = (TextView) v.findViewById(android.R.id.text1);
            textView.setTextColor(0xFF000000); // That's black with alpha 255
            textView.setText(t.getTitle());
        }

        return v;
    }
}
