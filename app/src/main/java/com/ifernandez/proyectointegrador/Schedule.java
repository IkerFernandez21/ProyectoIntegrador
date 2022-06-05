package com.ifernandez.proyectointegrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class Schedule extends AppCompatActivity {

    private String[] weekDays = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo"};
    private Spinner spinner;
    private Box<ScheduleItem> box;
    private List<ScheduleItem> itemsList;
    private FloatingActionButton fab;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        spinner = (Spinner) findViewById(R.id.schedule_spinner);
        spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, weekDays));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        box = ObjectBox.get().boxFor(ScheduleItem.class);

        setUpRecycler();
        configureButton();
    }

    private void setUpRecycler() {

        itemsList = box.getAll();

        recycler = findViewById(R.id.recycler_schedule);
        LinearLayoutManager mLayout = new LinearLayoutManager(this);
        recycler.setLayoutManager(mLayout);
        RecyclerView.Adapter adapter = new AdapterScheduleDay(this, itemsList);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new LandingAnimator(new OvershootInterpolator(2.0f)));
        recycler.getItemAnimator().setRemoveDuration(220);
    }

    private void configureButton() {
        fab = findViewById(R.id.fab_schedule);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsList.add(new ScheduleItem());
                recycler.getAdapter().notifyItemInserted(itemsList.size());
            }
        });
    }
}