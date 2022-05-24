package com.ifernandez.proyectointegrador;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {

    private String tittle;
    private String description;
    private boolean completed;
    private LocalDate date;
    private int color;

    //Constructors
    public Task() {
        this.color = 0;
    }

    public Task(String tittle, String description) {
        this.tittle = tittle;
        this.description = description;
        this.completed = false;
        this.color = 0;
    }

    public Task(String tittle, String description, boolean completed) {
        this.tittle = tittle;
        this.description = description;
        this.completed = completed;
        this.color = 0;
    }

    //Setters and getters
    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Task{" +
                "tittle='" + tittle + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                '}';
    }
}
