package com.example.philipp.scrum;

import android.content.Context;

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
    // Without this, an exception is thrown that the expected and found Everything objects have
    // different serialVersionUIDs.
    static final long serialVersionUID = 3629739416051034729L;

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

    public int getNumberOfProjects()
    {
      return  projectList.size();
    }
    public List<Project> getProjectList()
    {
        return this.projectList;
    }

    //TODO Add clear function

    /**
     * This method saves an Everything object to internal storage. To use it, call Everything.save()
     * It needs a context passed so that it can get the internal storage files directory.
     */
    public void save(Context context)
    {
        try
        {
            // Make an ObjectOutputStream that writes objects to scrumSave.txt
            File savefile = new File(context.getFilesDir().getPath() + R.string.filename);
            FileOutputStream fos = new FileOutputStream(savefile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Write the object
            oos.writeObject(this);

            // Clean up
            oos.flush();
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
     *
     * It needs a context passed so that it can get the internal storage files directory.
     */
    public void load(Context context)
    {
        try
        {
            /**
             * Even if you save a file to /files/scrumSave.ser it produces a file called /files/
             * files2131492892. Using this horrible hack, we find the correct file: We get the
             * files directory as a path string (which has no forward slash at the end) and append
             * the magic number. This gives us the path to the file.
             */
            String pathString = context.getFilesDir().getPath() + "2131492892";
            File savefile = new File(pathString);

            // Make an ObjectInputStream that reads objects from save.txt
            FileInputStream fis = new FileInputStream(savefile);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Cast the loaded object because it always is an Everything
            Everything loadedEverything = null;
            loadedEverything = (Everything) ois.readObject();

            // Transfer the heart of the object, the project list
            this.projectList = loadedEverything.getProjectList();

            // Clean up
            ois.close();
        }
        catch (Exception ex)
        {
            // Assuming that an exception means that there is no saved Everything yet, we just leave
            // it empty.
            ex.printStackTrace();
        }
    }
}