package com.ifernandez.proyectointegrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;
import jp.wasabeef.recyclerview.animators.FlipInLeftYAnimator;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class ActivityDay extends AppCompatActivity {
    private SharedPreferences prefrencias;
    private ImageButton deleteButton;
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
        ponerTema();
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

    private void ponerTema() {
        deleteButton = this.findViewById(R.id.deleteRowButton);


        prefrencias = getSharedPreferences("MisPrefrencias", Context.MODE_PRIVATE);
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

        String monthString = "";
        switch(dayDate.getMonth()){
            case 0:
                monthString = getString(R.string.jan);
                break;
            case 1:
                monthString = getString(R.string.feb);
                break;
            case 2:
                monthString = getString(R.string.mar);
                break;
            case 3:
                monthString = getString(R.string.apr);
                break;
            case 4:
                monthString = getString(R.string.mays);
                break;
            case 5:
                monthString = getString(R.string.jun);
                break;
            case 6:
                monthString = getString(R.string.jul);
                break;
            case 7:
                monthString = getString(R.string.aug);
                break;
            case 8:
                monthString = getString(R.string.sep);
                break;
            case 9:
                monthString = getString(R.string.oct);
                break;
            case 10:
                monthString = getString(R.string.nov);
                break;
            case 11:
                monthString = getString(R.string.dec);
                break;
        }
        tvDay.setText(day + " " + dayDate.getDate() + " " + monthString);
        dayDate.getMonth();
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

        rvDay.setHasFixedSize(true);

        rvDay.setItemAnimator(new LandingAnimator(new OvershootInterpolator(2.0f)));
        rvDay.getItemAnimator().setChangeDuration(0);
        rvDay.getItemAnimator().setMoveDuration(0);
        rvDay.getItemAnimator().setRemoveDuration(220);
        rvDay.getItemAnimator().setAddDuration(300);
    }


    /**
     * Button to add a new task
     * @param view
     */
    public void addTaskButton(View view) {
        Task newTask = new Task();

        taskList.add(newTask);
        rvDay.getAdapter().notifyItemInserted(rvDay.getAdapter().getItemCount()+1);
        //setUpRecycler();
    }
}