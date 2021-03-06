package com.ifernandez.proyectointegrador;

import android.app.Activity;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import io.grpc.Context;

public class Vault implements Parcelable {

    private ArrayList<Day> daysList;

    public Vault(File path) {
        loadVaultFromFile(path);
    }

    protected Vault(Parcel in) {
        this.daysList = in.readArrayList(null);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(daysList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Vault> CREATOR = new Creator<Vault>() {
        @Override
        public Vault createFromParcel(Parcel in) {
            return new Vault(in);
        }

        @Override
        public Vault[] newArray(int size) {
            return new Vault[size];
        }
    };

    public ArrayList<Day> getDaysList() {
        return daysList;
    }

    public void setDaysList(ArrayList<Day> daysList) {
        this.daysList = daysList;
    }

    public void loadVaultFromFile(File path) {

        File fileName = new File(path, "/" + "vault.dat");

        // Deserialization
        try {
            // Reading the object from the file
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            ArrayList<Day> days = (ArrayList<Day>) in.readObject();

            in.close();
            file.close();

            this.daysList = days;

        } catch (IOException ex) {
            System.out.println("Vault not found");
            this.daysList = new ArrayList<Day>();
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }

    }

    public void saveVaultToFile(File path) {

        File fileName = new File(path, "/" + "vault.dat");

        // Serialization
        try {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this.daysList);

            out.close();
            file.close();
        } catch (IOException ex) {
            System.out.println("IOException is caught");
            ex.printStackTrace();
        }
    }

    public void saveVaultToCloud(File path, Activity activity) {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(activity);

        if (acct != null) {
            try {
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();

                File fileName = new File(path, "/" + "vault.dat");

                InputStream stream = new FileInputStream(fileName);

                // Create a reference to "mountains.jpg"
                String destiny = "vaults/vault" + acct.getId() + ".dat";
                StorageReference mountainsRef = FirebaseStorage.getInstance().getReference().child(destiny);


                UploadTask uploadTask = mountainsRef.putStream(stream);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        System.out.println("Save to cloud fail");
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        System.out.println("save to cloud success");
                    }
                });

            } catch (Exception e) {
                System.out.println("Fail to save vault to cloud");
            }
        }
    }

}

