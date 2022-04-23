package com.ifernandez.proyectointegrador;


import java.io.Serializable;
import java.util.ArrayList;

public class Note implements Serializable {

    private String tittle;
    private String description;
    private ArrayList<byte[]> photos;

    public Note() {
        tittle = "";
        description = "";
        photos = new ArrayList<byte[]>();
        //Photos are stored as an array of bytes as "BitMap" is not serializable
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

    public ArrayList<byte[]> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<byte[]> photos) {
        this.photos = photos;
    }
}
