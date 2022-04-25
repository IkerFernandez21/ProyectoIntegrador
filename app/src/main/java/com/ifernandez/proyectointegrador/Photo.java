package com.ifernandez.proyectointegrador;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Photo {

    @Id
    private long id;
    private byte[] data;

    public Photo() {
    }

    public Photo(int id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
