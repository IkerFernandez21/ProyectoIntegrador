package com.ifernandez.proyectointegrador;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Vault {

    private ArrayList<Day> daysList;

    public Vault(File path) {
        loadVaultFromFile(path);
    }

    public ArrayList<Day> getDaysList() {
        return daysList;
    }

    public void setDaysList(ArrayList<Day> daysList) {
        this.daysList = daysList;
    }

    public void loadVaultFromFile(File path){

        File fileName = new File(path, "/" + "vault.dat");

        // Deserialization
        try
        {
            // Reading the object from the file
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            ArrayList<Day> days = (ArrayList<Day>) in.readObject();

            in.close();
            file.close();

            this.daysList = days;

        }

        catch(IOException ex)
        {
            System.out.println("Vault not found");
            this.daysList = new ArrayList<Day>();
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }

    }

    public void saveVaultToFile(File path){

        File fileName = new File(path, "/" + "vault.dat");

        // Serialization
        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this.daysList);

            out.close();
            file.close();
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
            ex.printStackTrace();
        }
    }

}

