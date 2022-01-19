package com.ifernandez.proyectointegrador;

public class Task {

    private String tittle;
    private String description;
    private boolean completed;

    //Constructors
    public Task() {
    }

    public String getTittle() {
        return tittle;
    }

    //Setters and getters
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

    @Override
    public String toString() {
        return "Task{" +
                "tittle='" + tittle + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                '}';
    }
}
