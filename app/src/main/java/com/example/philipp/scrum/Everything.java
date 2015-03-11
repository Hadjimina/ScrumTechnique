package com.example.philipp.scrum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class has the purpose of unifying all the projects with their tasks into one single object,
 *
 * This object can be saved after each change and onStop() and onDestroy(). It can be loaded
 * onCreate() of each activity.
 *
 * Created by balduin on 11.03.15.
 */

public class Everything implements Serializable {

    // The only field is a List of all projects.
    List<Project> projectList = new ArrayList<Project>();

    public void addProject(Project projectToAdd)
    {
        projectList.add(projectToAdd);
    }

    public Project getProject(int index)
    {
        return projectList.get(index);
    }

    public void save()
    {
        //TODO all that stuff with output stream
    }

    public void load()
    {

    }
}
