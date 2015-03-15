package com.example.philipp.scrum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProjectOverviewFragment extends Fragment {

    View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // Inflate the layout
        layout = inflater.inflate(R.layout.fragment_project_overview, null);

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

        return layout;
    }

}
