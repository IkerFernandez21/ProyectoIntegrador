package com.ifernandez.proyectointegrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class ActivityDay extends AppCompatActivity {

    TextView tvDay;
    TextView tvYear;
    RecyclerView rvDay;
    Date dayDate;
    ArrayList<Task> taskList;
    AdapterTasks adapter;
    Vault vault;
    ArrayList<Day> daysList;

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

        vault = new Vault(getFilesDir());
        daysList = vault.getDaysList();

        //Searchs and load the taskList for the correct day
        for(Day d : daysList){
            System.out.println(d.toString());
            if (d.getDate().getDate() == dayDate.getDate()
                && d.getDate().getMonth() == dayDate.getMonth()
                && d.getDate().getYear() == dayDate.getYear()) {
                taskList = d.getTaskList();
                break;
            }
        }

        //If there isnt any day saved, creates a new taskList
        if (taskList == null){
            taskList = new ArrayList<>();
        }

         //Load tasklist
        setUpRecycler();

    }

    @Override
    protected void onStop() {
        super.onStop();

        Day day = null;

        //Save changes on tasks
        for(Day d : daysList){
            if (d.getDate().compareTo(dayDate) == 0) {
                day = d;
                break;
            }
        }

        if (day != null){
            day.setTaskList(taskList);
        }else{
            day = new Day();
            day.setDate(dayDate);
            day.setTaskList(taskList);
            daysList.add(day);
        }

        System.out.println("Days on day saving: " + daysList);
        vault.setDaysList(daysList);
        vault.saveVaultToFile(getFilesDir());
    }


    private void setUpRecycler(){

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