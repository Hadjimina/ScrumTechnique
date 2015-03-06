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



public class ProjectDialogFragment extends DialogFragment
{
    private EditText projectNameEdittext;

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

        // Assign the buttons
        dialogBuilder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Send the entered text back to mainActivity which processes it further
                String projectName = projectNameEdittext.getText().toString().trim();
                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.createNewProject(projectName);
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

        return dialogBuilder.create();
    }

}