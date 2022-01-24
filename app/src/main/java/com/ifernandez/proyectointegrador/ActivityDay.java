package com.ifernandez.proyectointegrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class ActivityDay extends AppCompatActivity {

    TextView tvDay;
    TextView tvYear;
    RecyclerView rvDay;
    Date dayDate;
    ArrayList<Task> taskList;
    AdapterTasks adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        tvDay = findViewById(R.id.tv_day);
        tvYear = findViewById(R.id.tv_year);

        Bundle extras = getIntent().getExtras();
        dayDate = (Date) extras.get("dayDate");

        tvDay.setText(dayDate.getDay() + " " + dayDate.getDate());
        //tvYear.setText(dayDate.getYear());

        taskList = new ArrayList<>(); //Load tasklist
        setUpRecycler();

    }

    private void setUpRecycler(){

        Task task = new Task("Titulo", "Descripcion");
        taskList.add(task);

        //Recycler
        rvDay = findViewById(R.id.rv_day_tasks);
        LinearLayoutManager mLayout = new LinearLayoutManager(this);
        rvDay.setLayoutManager(mLayout);
        adapter = new AdapterTasks(this,taskList);
        rvDay.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvDay.getContext(),
                mLayout.getOrientation());
        rvDay.addItemDecoration(dividerItemDecoration);

    }

    public void addTaskButton(View view) {
        Task newTask = new Task();

        int posicionInsertion = (adapter.getPos()>=0)? adapter.getPos()+1:0;
        taskList.add(newTask);
        adapter.notifyItemInserted(taskList.size());
        adapter.notifyItemRangeChanged(0,taskList.size());
        rvDay.scheduleLayoutAnimation();
    }
}