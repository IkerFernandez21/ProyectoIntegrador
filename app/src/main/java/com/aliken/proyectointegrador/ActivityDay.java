package com.aliken.proyectointegrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ifernandez.proyectointegrador.R;

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
    LinearLayoutManager mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        tvDay = findViewById(R.id.tv_day);
        tvYear = findViewById(R.id.tv_year);

        Bundle extras = getIntent().getExtras();
        dayDate = (Date) extras.get("dayDate");

        setTextviewsUI();

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

    private void setTextviewsUI(){

        String day = "";

        switch(dayDate.getDay()){
            case 0:
                day = getString(R.string.sunday);
                break;
            case 1:
                day = getString(R.string.monday);
                break;
            case 2:
                day = getString(R.string.tuesday);
                break;
            case 3:
                day = getString(R.string.wednesday);
                break;
            case 4:
                day = getString(R.string.thursday);
                break;
            case 5:
                day = getString(R.string.friday);
                break;
            case 6:
                day = getString(R.string.saturday);
                break;
        }

        tvDay.setText(day + " " + dayDate.getDate());
        int year = dayDate.getYear()+1900;
        tvYear.setText(""+year);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        vault.saveVaultToFile(getFilesDir());
    }

    /**
     * Set ups the recyclerview with the correct values
     */
    private void setUpRecycler(){

        //Recycler
        rvDay = findViewById(R.id.rv_day_tasks);
        mLayout = new LinearLayoutManager(this);
        rvDay.setLayoutManager(mLayout);
        adapter = new AdapterTasks(this,taskList);
        rvDay.setAdapter(adapter);
    }


    /**
     * Button to add a new task
     * @param view
     */
    public void addTaskButton(View view) {
        Task newTask = new Task();

        taskList.add(newTask);
        setUpRecycler();
        //rvDay.scrollToPosition(adapter.getItemCount());
    }
}