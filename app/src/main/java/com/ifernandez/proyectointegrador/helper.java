package com.ifernandez.proyectointegrador;

public class helper {
    private String day;
    private String titulo;

    public helper(String day,String titulo) {
        this.day = day;
        this.titulo = titulo;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
