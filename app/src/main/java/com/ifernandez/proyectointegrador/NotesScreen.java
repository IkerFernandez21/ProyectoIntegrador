package com.ifernandez.proyectointegrador;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class NotesScreen extends AppCompatActivity {

    private View view;
    private ArrayList<Note> notesList;
    private FloatingActionButton fab;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_screen);

        setActivityResultLauncher();
    }

    @Override
    protected void onStart() {
        super.onStart();
        view = findViewById(R.id.notesScreen);
        setUpRecycler();
        configureButton();
    }

    private void setActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //TODO PERSISTENCE
                        /*
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            vault.loadVaultFromFile(main.getFilesDir());
                            notesList = vault.getNotesList();
                            setUpRecycler();
                        }

                         */
                    }
                }
        );


    }

    private void setUpRecycler() {

        RecyclerView recycler;
        notesList = new ArrayList<Note>();

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
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("newNote", false);
        intent.putExtra("notePos",notePos);
        activityResultLauncher.launch(intent);
    }
}