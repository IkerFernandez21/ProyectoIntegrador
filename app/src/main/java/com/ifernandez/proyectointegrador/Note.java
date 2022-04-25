package com.ifernandez.proyectointegrador;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Note implements Serializable {

    @Id
    private long id;
    private String tittle;
    private String description;
    private List<String> photos;



    public Note() {
        tittle = "";
        description = "";
        photos = new ArrayList<String>();
        //Photos are stored as an array of bytes as "BitMap" is not serializable
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }
}
