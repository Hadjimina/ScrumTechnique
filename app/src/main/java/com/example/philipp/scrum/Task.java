package com.example.philipp.scrum;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Task implements Serializable
{

    // Fields
    private String title;
    private String description;
    private GregorianCalendar date;
    private boolean hasDate;

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
        this.hasDate = true;
    }
    public boolean hasDate() {
        return hasDate;
    }
    public void setHasDate(Boolean hasDate) {
        this.hasDate = hasDate;
    }
    public void setCategory(int category) {
        this.category = category;
    }
    public int getCategory() {
        return this.category;
    }
    public GregorianCalendar getDate()
    {
        if(hasDate) {
            return this.date;
        } else {
            return null;
        }
    }

    /**
     * Constructs a Task from the given arguments
     */
    public Task(String title, String description, int category, String year, String month, String day)
    {
        this.title = title;
        this.description = description;
        this.category = category;

        if(year.length() > 0 && month.length() > 0 && day .length() > 0)
        {
            int yearInteger =  Integer.parseInt(year);
            int monthInteger = Integer.parseInt(month);
            int dayInteger =   Integer.parseInt(day);

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
