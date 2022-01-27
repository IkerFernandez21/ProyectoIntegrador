package com.ifernandez.proyectointegrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

    /**
     * This method saves all the changes to a file when back button pressed
     */
    @Override
    public void onBackPressed() {
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

        vault.setDaysList(daysList);

        Intent result = new Intent();
        result.putExtra("vault", vault);
        setResult(RESULT_OK, result);
        finish();
    }

    /**
     * Set ups the recyclerview with the correct values
     */
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

    /**
     * Button to add a new task
     * @param view
     */
    public void addTaskButton(View view) {
        Task newTask = new Task();

        int posicionInsertion = (adapter.getPos()>=0)? adapter.getPos()+1:0;
        taskList.add(newTask);
        adapter.notifyItemInserted(taskList.size());
        adapter.notifyItemRangeChanged(0,taskList.size());
        rvDay.scheduleLayoutAnimation();
    }
}