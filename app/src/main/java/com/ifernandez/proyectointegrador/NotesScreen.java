package com.ifernandez.proyectointegrador;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class NotesScreen extends AppCompatActivity {

    private View view;
    private List<Note> notesList;
    private FloatingActionButton fab;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Box<Note> noteBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ponerTema();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_screen);

        setActivityResultLauncher();
    }

    @Override
    protected void onStart() {
        super.onStart();
        view = findViewById(R.id.notesScreen);
        noteBox = ObjectBox.get().boxFor(Note.class);
        setUpRecycler();
        configureButton();
    }

    private void setActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            notesList = noteBox.getAll();
                            setUpRecycler();
                        }

                    }
                }
        );


    }

    private void setUpRecycler() {

        RecyclerView recycler;
        notesList = noteBox.getAll();

        recycler = view.findViewById(R.id.recycler_notes);
        StaggeredGridLayoutManager mLayout = new StaggeredGridLayoutManager(2,1);
        recycler.setLayoutManager(mLayout);
        RecyclerView.Adapter adapter = new AdapterNotes(this, notesList, this);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new LandingAnimator(new OvershootInterpolator(2.0f)));
        recycler.getItemAnimator().setRemoveDuration(220);
    }

    private void configureButton() {
        fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesScreen.this, NoteActivity.class);
                intent.putExtra("newNote", true);
                activityResultLauncher.launch(intent);
            }
        });
    }

    public void openNote(int notePos){

        long noteId = notesList.get(notePos).getId();
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("newNote", false);
        intent.putExtra("noteId",noteId);
        activityResultLauncher.launch(intent);
    }

    private void ponerTema() {

        SharedPreferences prefrencias = getSharedPreferences("MisPrefrencias", Context.MODE_PRIVATE);


        String temas = prefrencias.getString("tema","Verde");
        switch (temas){
            case "Mostaza":setTheme(R.style.theme_Mustardsinactionbar_);break;
            case "Verde":setTheme(R.style.theme_sinactionbar);break;
            case "Azul":setTheme(R.style.theme_Tealsinactionbar_);break;
            case "Azul y naranja":setTheme(R.style.theme_OrangeBluesinactionbar_);break;
            case "Rosa":setTheme(R.style.theme_Pinksinactionbar_);break;
            case "Gris":setTheme(R.style.theme_Greysinactionbar_);break;

        }
    }

}