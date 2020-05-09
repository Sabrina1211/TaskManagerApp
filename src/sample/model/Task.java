package sample.model;

import java.sql.Timestamp;

//Declaring Variables

public class Task {

    private int userId;
    private int taskId;
    private Timestamp datecreated;
    private String description;
    private String task;


    //Empty Constructor
    public Task() {
    }

    public Task(Timestamp datecreated, String description, String task, int userId) {
        this.datecreated = datecreated;
        this.description = description;
        this.task = task;
        this.userId = userId;
    }
     //The User class a used getter and setter method for each instance
// for example description, task, detecreate etc


//The getter method returns the value of the attribute while the setter method
// takes a parameter and assigns it to the attribute.


    public Timestamp getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Timestamp datecreated) {
        this.datecreated = datecreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
       this.taskId = taskId;
    }


}
