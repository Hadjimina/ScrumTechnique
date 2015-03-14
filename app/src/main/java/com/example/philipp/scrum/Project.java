package com.example.philipp.scrum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by balduin on 2015-03-11 19:01
 *
 * This class can save a Project. Its only fields are the name and the description (Strings) and a
 * 2D List of all the tasks
 */

public class Project
{
    /**
     * Looks a bit complicated at first glance. It's basically a 2D Array, a "List of Lists". The
     * "outermost" index is the category, of which there will always be 5. So if you want to add
     * something to category 2 (To do), you may do so using listOfTaskLists.get(2).add(Task);
     */
    List<List<Task>> listOfTaskLists = new ArrayList<>();

    // Simple string fields
    String name;
    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
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
    }

    public Task getTask(int category, int position)
    {
        return listOfTaskLists.get(category).get(position);
    }

    /**
     * Saves a given task to a project by simply calling Project.addTask(Task). Automatically uses
     * the right category.
     *
     * @param taskToAdd
     * The task object that should be added to the "database"
     */
    public void addTask(Task taskToAdd)
    {
        int category = taskToAdd.getCategory();

        /**
         * -1 because category 0 is the overview. So the first category (To do) has the value 1, but
         * is saved in the 0th index of the list.
         *
         * Order doesn't matter (yet), because there won't be many tasks in a specific category.
         * If this changes later on, we could do something like Project.sort() that sorts the tasks
         * of each category by date.
         */

        listOfTaskLists.get(category - 1).add(taskToAdd);

        // TODO save and load
        // Ideally, we would have something like IOHelper.save() or IOHelper.load() that saves and
        // loads everything in a simple manner. This would be a good moment to call it.
    }

    public void removeTask(int category, int task)
    {
        listOfTaskLists.get(category).remove(task);

        // TODO save
    }

}
