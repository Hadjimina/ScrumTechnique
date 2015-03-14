package com.example.philipp.scrum;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Task implements Serializable
{

    // Fields
    private String title;
    private String description;
    private boolean hasDescription;
    private GregorianCalendar date;
    private boolean hasDate;
    private String project;

    // String if categories are customisable, int if predefined
    private int category;

    // Getters & Setters
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle()
    {
        return this.title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDate(GregorianCalendar date) {
        this.date = date;
    }
    public boolean hasDate() {
        return hasDate;
    }
    public void setHasDate(boolean hasDate) {
        this.hasDate = hasDate;
    }
    public boolean hasDescription() {
        return hasDescription;
    }
    public void setHasDescription(boolean hasDescription) {
        this.hasDescription = hasDescription;
    }
    public void setCategory(int category) {
        this.category = category;
    }
    public int getCategory() {
        return this.category;
    }
    public String getProject() {
        return project;
    }
    public void setProject(String project) {
        this.project = project;
    }


    // Constructor (checks automatically if the entered values are empty)
    public Task(String newTitle, String newDescription, int newCategory, String newYear, String newMonth, String newDay)
    {
        this.title = newTitle;
        this.category = newCategory;

        if (newDescription.length() > 0)
        {
            this.description = newDescription;
            this.hasDescription = true;
        }
        else
        {
            this.hasDescription = false;
        }

        if(newYear.length() > 0 && newMonth.length() > 0 && newDay .length() > 0)
        {
            int yearInteger =  Integer.parseInt(newYear);
            int monthInteger = Integer.parseInt(newMonth);
            int dayInteger =   Integer.parseInt(newDay);

            this.date = new GregorianCalendar(yearInteger, monthInteger, dayInteger);
            this.hasDate = true;
        }
        else
        {
            this.hasDate = false;
        }
    }

    public String toString()
    {
        return "Title: "       + this.title
           + ", description: " + this.description
           + ", category: "    + this.category;
    }

}
