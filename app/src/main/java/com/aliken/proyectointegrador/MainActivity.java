package com.aliken.proyectointegrador;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.ifernandez.proyectointegrador.R;

import org.joda.time.DateTime;
import org.joda.time.Weeks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button bthome, btCalendar;
    private int showingWeek;
    private Vault vault;
    private ArrayList<Day> daysList;
    private ArrayList<Date> week;
    private RecyclerView rvMonday;
    private RecyclerView rvTuesday;
    private RecyclerView rvWednesday;
    private RecyclerView rvThursday;
    private RecyclerView rvFriday;
    private RecyclerView rvSaturday;
    private RecyclerView rvSunday;
    private OnSwipeTouchListener onSwipeTouchListener;
    private ConstraintLayout cl;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private LinearLayoutManager mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showingWeek = 0;
        week = getWeekDateList();
        setDaysOfWeekUI();
        setRecyclersUp();
        setRecyclersDecoration();
        CambioSemana();
        eventoHome();
        eventoCalendario();
        setActivityResultLauncher();
        setBtCalendar();
    }

    private void setBtCalendar(){
        btCalendar = findViewById(R.id.buttonCalendar);

        btCalendar.setOnClickListener(this::showDatePicker);
    }

    public void showDatePicker(View view) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {


                Date dateNow = week.get(0);

                Date datePicked = new Date();
                datePicked.setDate(day);
                datePicked.setMonth(month);
                datePicked.setYear(year-1900);

                DateTime dateTime1 = new DateTime(dateNow);
                DateTime dateTime2 = new DateTime(datePicked);

                int weeks = 0;

                if (dateTime1.isBefore(dateTime2)){
                    weeks = Weeks.weeksBetween(dateTime1, dateTime2).getWeeks();
                }else{
                    weeks = Weeks.weeksBetween(dateTime2,dateTime1).getWeeks();
                    weeks = - weeks -1;
                }

                showingWeek = showingWeek + weeks;
                week = getWeekDateList(showingWeek);
                setDaysOfWeekUI();
                setRecyclersUp();

            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void eventoCalendario() {
        btCalendar = findViewById(R.id.buttonCalendar);
        btCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void eventoHome() {
        bthome = findViewById(R.id.buttonHome);
        bthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showingWeek = 0;
                week = getWeekDateList(showingWeek);
                setDaysOfWeekUI();
                setRecyclersUp();
            }
        });
    }


    /**
     * This method allow to return info from activitu day
     */
    private void setActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent resultIntent = result.getData();
                            vault = (Vault) resultIntent.getParcelableExtra("vault");
                            vault.saveVaultToFile(getFilesDir());
                            setRecyclersUp();
                        }

                    }
                }
        );


    }

    /**
     * creation of the event to scroll laterally
     */
    private void CambioSemana() {

        cl = findViewById(R.id.ConstraintLayout);
        cl.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {

            public void onSwipeRight() {
                showingWeek -= 1;
                week = getWeekDateList(showingWeek);
                setDaysOfWeekUI();
                setRecyclersUp();
            }

            public void onSwipeLeft() {
                showingWeek += 1;
                week = getWeekDateList(showingWeek);
                setDaysOfWeekUI();
                setRecyclersUp();
            }


        });
    }


    /**
     * Set ups RecyclerViews on init
     */
    public void setRecyclersUp() {

        ArrayList<String> list = new ArrayList<String>();
        ArrayList<Task> taskList;

        vault = new Vault(getFilesDir());
        daysList = vault.getDaysList();

        //Recycler Monday
        taskList = getTaskListFromDay(week.get(0));
        rvMonday = findViewById(R.id.rv_monday);
        mLayout = new LinearLayoutManager(this);
        rvMonday.setLayoutManager(mLayout);
        RecyclerView.Adapter adapter = new MyAdapter(this, taskList, week.get(0));
        rvMonday.setAdapter(adapter);

        //Recycler Tuesday
        taskList = getTaskListFromDay(week.get(1));
        rvTuesday = findViewById(R.id.rv_tueday);
        mLayout = new LinearLayoutManager(this);
        rvTuesday.setLayoutManager(mLayout);
        adapter = new MyAdapter(this, taskList, week.get(1));
        rvTuesday.setAdapter(adapter);

        //Recycler Wednesday
        taskList = getTaskListFromDay(week.get(2));
        rvWednesday = findViewById(R.id.rv_wednesday);
        mLayout = new LinearLayoutManager(this);
        rvWednesday.setLayoutManager(mLayout);
        adapter = new MyAdapter(this, taskList, week.get(2));
        rvWednesday.setAdapter(adapter);

        //Recycler Thursday
        taskList = getTaskListFromDay(week.get(3));
        rvThursday = findViewById(R.id.rv_thursday);
        mLayout = new LinearLayoutManager(this);
        rvThursday.setLayoutManager(mLayout);
        adapter = new MyAdapter(this, taskList, week.get(3));
        rvThursday.setAdapter(adapter);

        //Recycler Friday
        taskList = getTaskListFromDay(week.get(4));
        rvFriday = findViewById(R.id.rv_friday);
        mLayout = new LinearLayoutManager(this);
        rvFriday.setLayoutManager(mLayout);
        adapter = new MyAdapter(this, taskList, week.get(4));
        rvFriday.setAdapter(adapter);

        //Recycler Saturday
        taskList = getTaskListFromDay(week.get(5));
        rvSaturday = findViewById(R.id.rv_saturday);
        mLayout = new LinearLayoutManager(this);
        rvSaturday.setLayoutManager(mLayout);
        adapter = new MyAdapter(this, taskList, week.get(5));
        rvSaturday.setAdapter(adapter);

        //Recycler Sunday
        list = new ArrayList<String>();
        taskList = getTaskListFromDay(week.get(6));
        rvSunday = findViewById(R.id.rv_sunday);
        mLayout = new LinearLayoutManager(this);
        rvSunday.setLayoutManager(mLayout);
        adapter = new MyAdapter(this, taskList, week.get(6));
        rvSunday.setAdapter(adapter);

        setRecyclerClickEvent();
    }

    private void setRecyclersDecoration() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvMonday.getContext(),
                mLayout.getOrientation());
        rvMonday.addItemDecoration(dividerItemDecoration);

        dividerItemDecoration = new DividerItemDecoration(rvTuesday.getContext(),
                mLayout.getOrientation());
        rvTuesday.addItemDecoration(dividerItemDecoration);

        dividerItemDecoration = new DividerItemDecoration(rvWednesday.getContext(),
                mLayout.getOrientation());
        rvWednesday.addItemDecoration(dividerItemDecoration);

        dividerItemDecoration = new DividerItemDecoration(rvThursday.getContext(),
                mLayout.getOrientation());
        rvThursday.addItemDecoration(dividerItemDecoration);

        dividerItemDecoration = new DividerItemDecoration(rvFriday.getContext(),
                mLayout.getOrientation());
        rvFriday.addItemDecoration(dividerItemDecoration);

        dividerItemDecoration = new DividerItemDecoration(rvSaturday.getContext(),
                mLayout.getOrientation());
        rvSaturday.addItemDecoration(dividerItemDecoration);

        dividerItemDecoration = new DividerItemDecoration(rvSunday.getContext(),
                mLayout.getOrientation());
        rvSunday.addItemDecoration(dividerItemDecoration);

    }

    private ArrayList<Task> getTaskListFromDay(Date day) {

        ArrayList<Task> taskList = null;
        //Searchs and load the taskList for the correct day
        for (Day d : daysList) {
            if (d.getDate().getDate() == day.getDate()
                    && d.getDate().getMonth() == day.getMonth()
                    && d.getDate().getYear() == day.getYear()) {
                taskList = d.getTaskList();
                break;
            }
        }
        //If there isnt any day saved, creates a new taskList
        if (taskList == null) {
            taskList = new ArrayList<>();
        }

        return taskList;
    }

    /**
     * This method allows the recyclerviews to open day activity by selecting an empty space
     */
    private void setRecyclerClickEvent() {
        rvMonday.setOnTouchListener(new RVClickHandler(rvMonday));
        rvMonday.setOnClickListener((v) -> {
            openDayActivity(0);
        });

        rvTuesday.setOnTouchListener(new RVClickHandler(rvTuesday));
        rvTuesday.setOnClickListener((v) -> {
            openDayActivity(1);
        });

        rvWednesday.setOnTouchListener(new RVClickHandler(rvWednesday));
        rvWednesday.setOnClickListener((v) -> {
            openDayActivity(2);
        });

        rvThursday.setOnTouchListener(new RVClickHandler(rvThursday));
        rvThursday.setOnClickListener((v) -> {
            openDayActivity(3);
        });

        rvFriday.setOnTouchListener(new RVClickHandler(rvFriday));
        rvFriday.setOnClickListener((v) -> {
            openDayActivity(4);
        });

        rvSaturday.setOnTouchListener(new RVClickHandler(rvSaturday));
        rvSaturday.setOnClickListener((v) -> {
            openDayActivity(5);
        });

        rvSunday.setOnTouchListener(new RVClickHandler(rvSunday));
        rvSunday.setOnClickListener((v) -> {
            openDayActivity(6);
        });


    }


    /**
     * This method set the days of the week in the UI
     */
    private void setDaysOfWeekUI() {

        TextView monday = findViewById(R.id.tv_monday);
        TextView tuesday = findViewById(R.id.tv_tuesday);
        TextView wednesday = findViewById(R.id.tv_wednesday);
        TextView thursday = findViewById(R.id.tv_thursday);
        TextView friday = findViewById(R.id.tv_friday);
        TextView weekend = findViewById(R.id.tv_weekend);
        TextView month = findViewById(R.id.tv_month);

        ArrayList<Date> showingWeekList = week;

        monday.setText(getString(R.string.monday) + " " + showingWeekList.get(0).getDate());
        tuesday.setText(getString(R.string.tuesday) + " " + showingWeekList.get(1).getDate());
        wednesday.setText(getString(R.string.wednesday) + " " + showingWeekList.get(2).getDate());
        thursday.setText(getString(R.string.thursday) + " " + showingWeekList.get(3).getDate());
        friday.setText(getString(R.string.friday) + " " + showingWeekList.get(4).getDate());
        weekend.setText(getString(R.string.weekend) + " " + showingWeekList.get(5).getDate() + "/" + showingWeekList.get(6).getDate());

        int firstMonth = -1, secondMonth = -1, fmCount = 0, smCount = 0, monthVar = -1;

        firstMonth = showingWeekList.get(0).getMonth();
        for(Date d : showingWeekList){

            monthVar = d.getMonth();
            if (monthVar == firstMonth){
                fmCount++;
            }else if (secondMonth == -1){
                secondMonth = monthVar;
                smCount++;
            }else{
                smCount++;
            }
        }

        if (fmCount > smCount){
            monthVar = firstMonth;
        }else{
            monthVar = secondMonth;
        }


        String monthString = "";

        switch(monthVar){
            case 0:
                monthString = getString(R.string.januray);
                break;
            case 1:
                monthString = getString(R.string.february);
                break;
            case 2:
                monthString = getString(R.string.march);
                break;
            case 3:
                monthString = getString(R.string.april);
                break;
            case 4:
                monthString = getString(R.string.may);
                break;
            case 5:
                monthString = getString(R.string.june);
                break;
            case 6:
                monthString = getString(R.string.july);
                break;
            case 7:
                monthString = getString(R.string.august);
                break;
            case 8:
                monthString = getString(R.string.september);
                break;
            case 9:
                monthString = getString(R.string.october);
                break;
            case 10:
                monthString = getString(R.string.november);
                break;
            case 11:
                monthString = getString(R.string.december);
                break;
        }

        month.setText(monthString);

    }

    /**
     * This method retrieves all the days of the current week plus the specified number of weeks
     *
     * @param plusWeek Number of weeks plus or minus the current, you want to retrieve the days for
     * @return a List of "Date" with all the days of the specified week
     */
    @NonNull
    public static ArrayList<Date> getWeekDateList(int plusWeek) {
        Calendar cal = Calendar.getInstance();
        if (plusWeek != 0) {
            cal.add(Calendar.DAY_OF_MONTH, 7 * plusWeek);
        }
        //  Set the first day of the week. According to Spanish custom, the first day of the week is Monday
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        //  Get the current date is the day of the week
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayWeek == 1) {
            dayWeek = 8;
        }

        //  According to the rules of the calendar, subtract the difference between the day of the week and the first day of the week from the current date
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);
        Date mondayDate = cal.getTime();

        cal.add(Calendar.DATE, 4 + cal.getFirstDayOfWeek());
        Date sundayDate = cal.getTime();

        ArrayList lDate = new ArrayList();
        lDate.add(mondayDate);
        Calendar calBegin = Calendar.getInstance();
        //  Set the time of this Calendar with the given Date
        calBegin.setTime(mondayDate);
        Calendar calEnd = Calendar.getInstance();
        //  Set the time of this Calendar with the given Date
        calEnd.setTime(sundayDate);
        //Test whether this date is after the specified date
        while (sundayDate.after(calBegin.getTime())) {
            //  According to the rules of the calendar, add or subtract a specified amount of time for a given calendar field
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * This method retrieves all the days of the current week in date format
     *
     * @return a List of "Date" with all the days of the current week
     */
    @NonNull
    public static ArrayList<Date> getWeekDateList() {
        return getWeekDateList(0);
    }

    public void openDayActivity(int day) {
        Intent i = new Intent(this, ActivityDay.class);
        i.putExtra("dayDate", week.get(day));
        activityResultLauncher.launch(i);
    }

}