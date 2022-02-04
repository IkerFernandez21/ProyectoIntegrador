package com.aliken.proyectointegrador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Day implements Serializable {

    private Date date;
    private ArrayList<Task> taskList;

    //Constructors
    public Day() {
    }

    public Day(Date date, ArrayList<Task> taskList) {
        this.date = date;
        this.taskList = taskList;
    }

    //Setters and getters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "Day{" +
                "date=" + date +
                ", taskList=" + taskList +
                '}';
    }
}
