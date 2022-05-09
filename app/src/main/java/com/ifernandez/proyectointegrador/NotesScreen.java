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
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

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

        getSupportActionBar().setTitle("");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_notes_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btn_fingerprint) {
            SharedPreferences preferences = getSharedPreferences("MisPrefrencias", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();

            if (preferences.getBoolean("fingerprint", false)) {
                editor.putBoolean("fingerprint", false);
                editor.commit();
                Toast.makeText(this, "Huella digital desactivada", Toast.LENGTH_SHORT).show();
            }else {
                editor.putBoolean("fingerprint", true);
                editor.commit();
                Toast.makeText(this, "Huella digital activada", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
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
            case "Mostaza":setTheme(R.style.theme_ProyectoIntegradorMustard);break;
            case "Verde":setTheme(R.style.theme_ProyectoIntegrador);break;
            case "Azul":setTheme(R.style.theme_ProyectoIntegradorTeal);break;
            case "Azul y naranja":setTheme(R.style.theme_ProyectoIntegradorOrangeBlue);break;
            case "Rosa":setTheme(R.style.theme_ProyectoIntegradorPink);break;
            case "Gris":setTheme(R.style.theme_ProyectoIntegradorGrey);break;

        }
    }

}