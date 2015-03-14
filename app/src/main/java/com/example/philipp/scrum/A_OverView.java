package com.example.philipp.scrum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class A_OverView extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // Inflate the layout
        View layout = inflater.inflate(R.layout.a_overview, null);

        // Get the currently opened project. Cast to ProjectActivity since not every Activity has
        // the getProject() method.
        Project currentProject = ((ProjectActivity) getActivity()).getProject();

        // Set the project title
        TextView titleText = (TextView) layout.findViewById(R.id.project_title);
        String title = currentProject.getName();
        titleText.setText(title);


        // Set the project description
        TextView descriptionText = (TextView) layout.findViewById(R.id.project_description);
        String description = currentProject.getDescription();
        descriptionText.setText(description);

        // Assign the button to add a new task to an object and set an onClickListener
        Button addTask = (Button) layout.findViewById(R.id.add_task);
        addTask.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                // Show up a dialog to add a new Task when the user clicks the button
                AddTaskFragment dialog = new AddTaskFragment();
                dialog.show(getActivity().getFragmentManager(), "MyDialogFragment");
            }
        });

        return layout;
    }

    //TODO show actionbar

}