package com.ifernandez.proyectointegrador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RESULT_LOAD_IMAGE = 2;
    private EditText tittle;
    private EditText description;
    private ArrayList<Note> noteList;
    private Note note;
    private LinearLayout linearNote;
    private ImageView imageSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        getSupportActionBar().setTitle("");

        tittle = findViewById(R.id.etNoteTittle);
        description = findViewById(R.id.etNoteDescription);

        initNote();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // you can set menu header with title icon etc
        menu.setHeaderTitle("Are you sure?");
        // add menu items
        menu.add(0, v.getId(), 0, "Delete");
        imageSelected = (ImageView) v;
    }

    // menu item select listener
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle() == "Delete") {

            for (int i = 2; i < linearNote.getChildCount(); i++) {
                if (linearNote.getChildAt(i).equals(imageSelected)){
                    note.getPhotos().remove(i-2); //TODO persistence
                    linearNote.removeView(imageSelected);
                }
            }

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_camera) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }else if (id == R.id.add_gallery) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //Set image on view
            ImageView iv = new ImageView(this);
            linearNote = findViewById(R.id.linear_notes);
            linearNote.addView(iv);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1000);
            lp.setMargins(10,10,10,10);
            iv.setLayoutParams(lp);
            iv.setImageBitmap(imageBitmap);

            //Add to Persistence
            //TODO Persistence
            /*
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageByte = baos.toByteArray();

            note.getPhotos().add(imageByte);

             */

            //Add click listener
            registerForContextMenu(iv);
        }else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);

                //Set image on view
                ImageView iv = new ImageView(this);
                linearNote = findViewById(R.id.linear_notes);
                linearNote.addView(iv);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1000);
                lp.setMargins(10,10,10,10);
                iv.setLayoutParams(lp);
                iv.setImageBitmap(imageBitmap);

                //Add to Persistence
                //TODO persistence
                /*
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageByte = baos.toByteArray();

                note.getPhotos().add(imageByte);

                 */

                //Add click listener
                registerForContextMenu(iv);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onBackPressed() {

        //TODO PERSISTENCE
        /*
        vault.setNotesList(noteList);
        vault.saveVaultToFile(getFilesDir());
        vault.saveVaultToCloud(getFilesDir(),this);
        Intent result = new Intent();
        setResult(RESULT_OK, result);
        */
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //TODO PERSISTENCE
        /*
        vault.setNotesList(noteList);
        vault.saveVaultToFile(getFilesDir());
        vault.saveVaultToCloud(getFilesDir(),this);

         */
    }

    private void initNote() {
        //TODO PERSISTENCE
        //vault = new PersistenceVault(getFilesDir());
        noteList = new ArrayList<Note>();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Boolean newNote = extras.getBoolean("newNote");

        //Retrieve the opened note or creates a new one if new note was selected
        if (newNote) {
            Note newNoteObject = new Note();
            noteList.add(newNoteObject);
            note = noteList.get(noteList.size() - 1);
        } else {
            int notePos = extras.getInt("notePos");
            note = noteList.get(notePos);
        }

        tittle.setText(note.getTittle());
        description.setText(note.getDescription());

        //Store the edit text values as its change
        tittle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                note.setTittle(tittle.getText().toString());
            }
        });

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                note.setDescription(description.getText().toString());
            }
        });

        //Deploy saved photos
        //TODO Persistence
        /*
        ArrayList<byte[]> photos = note.getPhotos();

        for(byte[] photo : photos){
            setPhotoInView(photo);
        }

         */
    }

    private void setPhotoInView(byte[] photo) {

        Bitmap imageBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);

        ImageView iv = new ImageView(this);
        linearNote = findViewById(R.id.linear_notes);
        linearNote.addView(iv);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1000);
        lp.setMargins(10,10,10,10);
        iv.setLayoutParams(lp);
        iv.setImageBitmap(imageBitmap);
        registerForContextMenu(iv);
    }
}