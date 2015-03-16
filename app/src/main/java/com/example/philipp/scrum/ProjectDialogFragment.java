package com.example.philipp.scrum;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * This fragment displays the dialog for adding a new project. The corresponding layout file is in
 * /res/layout/fragment_project_dialog.xml. It is displayed when the user taps the "add" button in
 * the projects overview.
 *
 * The fragment displays and manages two Edittext fields, one for the name and one for the
 * description. It has a cancel and an add button. Both of them close the dialog, but the add button
 * additionally calls back to the main activity which then makes a new project based on the EditText
 * values.
 */

public class ProjectDialogFragment extends DialogFragment
{
    private EditText projectNameEdittext;
    private EditText projectDescEdittext;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Make a dialogBuilder and set the title
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage(R.string.project_dialog_message);

        // Get the dialog layout and apply it
        View dialogLayout = getActivity().getLayoutInflater().inflate(R.layout.fragment_project_dialog, null);
        dialogBuilder.setView(dialogLayout);

        // Automatically show up the keyboard
        final InputMethodManager imm = (InputMethodManager)  getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        projectNameEdittext = (EditText) dialogLayout.findViewById(R.id.dialog_projectname_edittext);
        projectDescEdittext = (EditText) dialogLayout.findViewById(R.id.dialog_projectdescription_edittext);

        // Assign the buttons
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //Send the entered text back to mainActivity which processes it further
                String projectName = projectNameEdittext.getText().toString().trim();
                String projectDesc = projectDescEdittext.getText().toString().trim();
                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.createNewProject(projectName, projectDesc);
                imm.hideSoftInputFromWindow(projectNameEdittext.getWindowToken(), 0);
            }
        });

        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //Dialog is closed automatically
            }
        });

        // Return the finished android.app.Dialog object
        return dialogBuilder.create();
    }

}