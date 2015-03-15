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

        /*
        // Button to change description and its onClickListener
        Button changeDescButton = (Button) layout.findViewById(R.id.update_desc_button);

        changeDescButton.setOnClickListener(new View.OnClickListener() {

            /**
             * This method handles clicks on the "update description" button. On a click, it pulls
             * the text entered in R.id.project_description and sets it as the new description.
             * @param v - the View that has been clicked. In our case this is always the same Button.
             *./

            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.update_desc_button)
                {
                    // Get the EditText from the layout and the project index from the arguments
                    EditText descEditText = (EditText) layout.findViewById(R.id.project_description);
                    int projectPosition = getArguments().getInt("project");

                    // Load the current Everything object
                    Everything everything = new Everything();
                    everything.load(getActivity().getApplicationContext());

                    // Get the current project, set its description to the entered text and save it
                    Project currentProject = everything.getProject(projectPosition);
                    currentProject.setDescription(descEditText.getText().toString());
                    everything.setProject(projectPosition, currentProject);
                }

            }
        });
        */

        return layout;
    }

}
