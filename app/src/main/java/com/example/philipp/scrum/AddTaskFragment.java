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


public class AddTaskFragment extends DialogFragment
{
    private EditText projectNameEdittext;
    Context mContext;

    public AddTaskFragment() {
        mContext = getActivity();
    }

    public static AddTaskFragment newInstance() {
        AddTaskFragment f = new AddTaskFragment();
        return f;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Make a dialogBuilder and set the title
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage(R.string.add_task_message);

        // Get the dialog layout and apply it
        View dialogLayout = getActivity().getLayoutInflater().inflate(R.layout.addtaskfragment, null);
        dialogBuilder.setView(dialogLayout);

        // Automatically show up the keyboard
        InputMethodManager imm = (InputMethodManager)  getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        projectNameEdittext = (EditText) dialogLayout.findViewById(R.id.dialog_projectname_edittext);

        // Assign the buttons
        dialogBuilder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Send the entered text back to mainActivity which processes it further
                String projectName = projectNameEdittext.getText().toString().trim();
                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.createNewProject(projectName, "this is a test string");
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