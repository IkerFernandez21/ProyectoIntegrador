package com.ifernandez.proyectointegrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class ActivityDay extends AppCompatActivity {

    TextView tvDay;
    TextView tvYear;
    RecyclerView rvDay;
    Date dayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        tvDay = findViewById(R.id.tv_day);
        tvYear = findViewById(R.id.tv_year);

        Bundle extras = getIntent().getExtras();
        dayDate = (Date) extras.get("dayDate");

        tvDay.setText(dayDate.getDay() + " " + dayDate.getDate());
        tvYear.setText(dayDate.getYear());

        setUpRecycler();

    }

    private void setUpRecycler(){

        Task task = new Task("Titulo", "Descripcion");
        ArrayList taskList = new ArrayList();
        taskList.add(task);

        //Recycler
        rvDay = findViewById(R.id.rv_day_tasks);
        LinearLayoutManager mLayout = new LinearLayoutManager(this);
        rvDay.setLayoutManager(mLayout);
        RecyclerView.Adapter adapter = new MyAdapter(this,taskList);
        rvDay.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvDay.getContext(),
                mLayout.getOrientation());
        rvDay.addItemDecoration(dividerItemDecoration);

    }
}