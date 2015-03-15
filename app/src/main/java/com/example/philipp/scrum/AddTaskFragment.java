package com.example.philipp.scrum;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * This class has the purpose of displaying a dialog for adding a Task. The corresponding layout is
 * in /app/res/layout/addtaskfragment.xml. It has two text fields for title and description, a
 * spinner which lets you select the categories and three number fields for year, day and month.
 * When the positive button ("Add") is pressed, all these values are sent back to ProjectActivity by
 * calling its createTask() method. This further processes the data and eventually adds a Task.
 */

public class AddTaskFragment extends DialogFragment
{
    private EditText taskNameEdittext;
    private EditText taskDescEdittext;
    private Spinner taskCategorySpinner;
    private EditText yearEditText;
    private EditText monthEditText;
    private EditText dayEditText;
    private Context mContext;

    public AddTaskFragment() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Make a dialogBuilder and set the title
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        if(getArguments().getBoolean("editTask")) {
            dialogBuilder.setMessage("Edit task");
        } else {
            dialogBuilder.setMessage(R.string.add_task_message);
        }

        // Get the dialog layout and apply it
        View dialogLayout = getActivity().getLayoutInflater().inflate(R.layout.addtaskfragment, null);
        dialogBuilder.setView(dialogLayout);

        // Automatically show up the keyboard
        InputMethodManager imm = (InputMethodManager)  getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        // Initialise all the views in the dialog
        taskNameEdittext =    (EditText) dialogLayout.findViewById(R.id.task_title_edittext);
        taskDescEdittext =    (EditText) dialogLayout.findViewById(R.id.task_desc_edittext);
        taskCategorySpinner = (Spinner)  dialogLayout.findViewById(R.id.spinner1);
        yearEditText =        (EditText) dialogLayout.findViewById(R.id.date_year);
        monthEditText =       (EditText) dialogLayout.findViewById(R.id.date_month);
        dayEditText =         (EditText) dialogLayout.findViewById(R.id.date_day);

        // If the user is editing a task, fill the Views with the attributes of the task to edit
        Bundle arguments = getArguments();
        taskNameEdittext.setText(arguments.getCharSequence("title"));
        taskDescEdittext.setText(arguments.getCharSequence("description"));
        taskCategorySpinner.setSelection(arguments.getInt("category"));
        if(arguments.getBoolean("hasDate"))
        {
            yearEditText.setText(String.valueOf(arguments.getInt("year")));
            monthEditText.setText(String.valueOf(arguments.getInt("month")));
            dayEditText.setText(String.valueOf(arguments.getInt("day")));
        }

        // Set the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.category_array, R.layout.spinner_item);
        taskCategorySpinner.setAdapter(adapter);
        taskCategorySpinner.setSelection((int) getArguments().get("category"));

        // Assign the buttons
        dialogBuilder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve all the entered values
                String taskName = taskNameEdittext.getText().toString().trim();
                String taskDesc = taskDescEdittext.getText().toString().trim();
                int category = taskCategorySpinner.getSelectedItemPosition();
                String year = yearEditText.getText().toString();
                String month = monthEditText.getText().toString();
                String day = dayEditText.getText().toString();

                // Only proceed if the name is not empty
                if(!taskName.equals(""))
                {
                    ProjectActivity callingActivity = (ProjectActivity) getActivity();

                    // If the user is editing a task, call editTask in ProjectActivity and pass it
                    // all the values, and additionally the position of the clicked task within its
                    // category.
                    if(getArguments().getBoolean("editTask"))
                    {
                        Bundle taskInfo = new Bundle();

                        taskInfo.putString("taskName", taskName);
                        taskInfo.putString("taskDesc", taskDesc);
                        taskInfo.putInt("category", category);
                        if(!year.equals("") && !month.equals("") && !day.equals("")) {
                            taskInfo.putInt("year", Integer.parseInt(year));
                            taskInfo.putInt("month", Integer.parseInt(month));
                            taskInfo.putInt("day", Integer.parseInt(day));
                        }

                        taskInfo.putInt("taskPosition", getArguments().getInt("taskPosition"));

                        callingActivity.editTask(taskInfo);
                    }
                    // If the user wants to create an entire new Task, use the createTask() method.
                    else
                    {
                        callingActivity.createTask(taskName, taskDesc, category, year, day, month);
                    }
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Name must not be empty", Toast.LENGTH_SHORT).show();
                }
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
