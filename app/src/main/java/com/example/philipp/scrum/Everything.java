package com.example.philipp.scrum;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    // Simple methods for interaction
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

    /**
     * This is not really necessary; each time the number of projects is needed, the projectList is
     * already in RAM. Then we can just use projectList.size() from there.
     */
    public int getNumberOfProjects()
    {
      return  projectList.size();
    }

    public List<Project> getProjectList()
    {
        return this.projectList;
    }

    /**
     * This method simply creates a new, empty Everything object and writes it to the save file,
     * effectively deleting all the contents of the app.
     *
     * @param context - This method calls save() which needs a context
     */
    public void clear(Context context)
    {
        Everything everything = new Everything();
        everything.save(context);
    }

    /**
     * This method saves an Everything object to internal storage. To use it, call Everything.save()
     * It needs a context passed to get the internal storage files directory.
     */
    public void save(Context context)
    {
        try
        {
            // Make an ObjectOutputStream that writes objects to scrumSave.txt
            File savefile = new File(context.getFilesDir().getPath() + "/" + "scrumSave.txt");
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
            // Get the file that presumably has the stored information
            String pathString = context.getFilesDir().getPath() + "/scrumSave.txt";
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
            if (ex instanceof FileNotFoundException)
            {
                Toast.makeText(context, "Save file does not exist yet", Toast.LENGTH_SHORT).show();
            }

            ex.printStackTrace();
        }
    }

    /**
     * This method allows you to comfortably replace a project with a new version.
     * IMPORTANT: Don't forget to load and save the current Everything when using this method.
     * Example is in Project.java, in addTask(). It will probably stay the only place that needs
     * the setProject() method.
     *
     * @param projectPosition
     * The position that should be overwritten
     *
     * @param project
     * The project that should be saved in the given position
     */
    public void setProject(int projectPosition, Project project)
    {
        this.projectList.set(projectPosition, project);
    }
}