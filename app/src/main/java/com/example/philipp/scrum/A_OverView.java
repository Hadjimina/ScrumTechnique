package com.example.philipp.scrum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class A_OverView extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View ret =  inflater.inflate(R.layout.a_overview, null);
        EditText title = (EditText) ret.findViewById(R.id.editText);
        title.setText("Google is your friend.", TextView.BufferType.EDITABLE);
        Button addtask = (Button) ret.findViewById(R.id.addtask);

        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddTaskFragment dialog = AddTaskFragment.newInstance();
                dialog.show(getActivity().getFragmentManager(), "MyDialogFragment");
            }
        });

        return ret;
    }
}