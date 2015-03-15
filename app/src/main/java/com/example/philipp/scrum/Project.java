package com.example.philipp.scrum;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by balduin on 2015-03-11
 *
 * This class can save a Project. Its only fields are the name and the description (Strings) and a
 * 2D List of all the tasks
 */

public class Project implements Serializable
{

    /**
     * Looks a bit complicated at first glance. It's basically a 2D Array, a "List of Lists". The
     * "outermost" index is the category, of which there will always be 5. So if you want to add
     * something to category 2 (To do), you may do so using listOfTaskLists.get(2).add(Task);
     */
    List<List<Task>> listOfTaskLists = new ArrayList<>();

    // Simple string fields
    private String name;
    private String description;

    /**
     * Getter and setter method
     */
    public String getDescription() {
        return description;
    }

    // Probably not needed
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getCategoryTaskList(int category)
    {
        return listOfTaskLists.get(category);
    }

    public void setCategoryTaskList(int category, List<Task> taskList){
        listOfTaskLists.set(category, taskList);
    }

    public String getName() {
        return name;
    }

    public int getNumberOfTasksinCategory(int category){
        return listOfTaskLists.get(category).size();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project(String name, String description)
    {
        // It is already ensured within MainActivity.createNewProject(name, description) that the
        // name is longer than 0. Also the two strings have already been trimmed.
        this.name = name;
        this.description = description;

        // Fill the list of Task lists with empty categories so that no invalid position is called
        // by the task list fragments.
        for(int i = 0; i<5; i++)
        {
            listOfTaskLists.add(new ArrayList<Task>());
        }
    }

    /**
     * Probably not needed
     *
     * @param category - The category of the wanted task
     * @param position - The position of the wanted task within its category
     * @return - The task located at the specified category and position
     */
    public Task getTask(int category, int position)
    {
        return this.listOfTaskLists.get(category).get(position);
    }

    /**
     * Saves a given task to a project by simply calling Project.addTask(Task). Automatically uses
     * the right category.
     *
     * @param taskToAdd
     * The task object that should be added to the "database"
     *
     * @param projectPosition
     * This parameter could also be found out by the function itself by searching for the current
     * project in the project List from Everything, however it is easier to just provide it to it
     * since every activity/fragment that can add a task already has this information.
     *
     * @param context
     * It needs a context to call the load() and save() functions (go to Everything.java to see why
     * they need a context). Just use getApplicationContext() or getActivity().getAppContext().
     */
    public void addTask(Task taskToAdd, int projectPosition, Context context)
    {
        int category = taskToAdd.getCategory();

        listOfTaskLists.get(category).add(taskToAdd);

        // Now that the project (this) is equipped with the new task, we may summon Everything and
        // replace the project with the new one
        Everything everything = new Everything();
        everything.load(context);
        everything.setProject(projectPosition, this);
        everything.save(context);
    }

    public void setTask(int category, int taskPosition, Task task)
    {
        List<Task> categoryList = this.getCategoryTaskList(category);
        categoryList.set(taskPosition, task);
        this.setCategoryTaskList(category, categoryList);
    }

    /**
     * @param category The category of the task to be removed
     * @param task The location of the task to be removed within its category
     */
    public void removeTask(int category, int task)
    {
        listOfTaskLists.get(category).remove(task);

        // TODO save
    }

}
