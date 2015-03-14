package com.example.philipp.scrum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class has the purpose of unifying all the projects with their tasks into one single object.
 *
 * Usage:
 * - Make an Everything object and call Everything.load() to equip it with the saved information.
 * - add/remove projects
 * - call Everything.save() after each change or onStop() and onDestroy()
 * - Load it again the next time
 *
 * Created by balduin on 2015-03-11
 */

public class Everything implements Serializable
{
    // The only field is a List of all projects.
    List<Project> projectList = new ArrayList<Project>();

    // Simple methods
    public void addProject(Project projectToAdd)
    {
        projectList.add(projectToAdd);
    }

    public void removeProject(int index)
    {
        projectList.remove(index);
    }

    public Project getProject(int index)
    {
        return projectList.get(index);
    }

    public List<Project> getProjectList()
    {
        return this.projectList;
    }


    /**
     * This method saves an Everything object to internal storage. To use it, call Everything.save()
     */
    public void save()
    {
        try
        {
            // Make an ObjectOutputStream that writes objects to save.txt
            FileOutputStream fos = new FileOutputStream(new File("save.txt"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Write the object
            oos.writeObject(this);
            oos.flush();

            // Clean up
            oos.close();
            fos.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * This method loads a saved "Everything" object from memory. To load one, create one first and
     * then call createdEverything.load() to fill it with the stored projects.
     */
    public void load()
    {
        try
        {
            // Make an ObjectInputStream that reads objects from save.txt
            FileInputStream fis = new FileInputStream(new File("save.txt"));
            ObjectInputStream ois = new ObjectInputStream(fis);

            // We can cast the loaded object to an Everything because it already is one.
            // Then its projectList is saved into the Everything this method is called on.
            this.projectList = ((Everything) ois.readObject()).projectList;

            // Clean up
            ois.close();
            fis.close();
        }
        catch (Exception ex)
        {
            // Assuming that an exception means that there is no saved Everything yet, we just leave
            // it empty.
            ex.printStackTrace();
        }
    }
}