package com.ifernandez.proyectointegrador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int showingWeek;
    private ArrayList<Day> vault;
    private ArrayList<Date> week;
    private RecyclerView rvMonday;
    private RecyclerView rvTuesday;
    private RecyclerView rvWednesday;
    private RecyclerView rvThursday;
    private RecyclerView rvFriday;
    private RecyclerView rvSaturday;
    private RecyclerView rvSunday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showingWeek = 0;
        week = getWeekDateList();
        setDaysOfWeekUI();
        setRecyclersUp();
    }

    /**
     * Set ups RecyclerViews on init
     */
    private void setRecyclersUp(){

        ArrayList<String> list = new ArrayList();
        list.add("ejemplo 1 muy largo super largo hiper mega super larguisimo de la muerte");
        list.add("ejemplo 2");
        list.add("ejemplo 3");
        list.add("ejemplo 4");
        list.add("ejemplo 5");
        list.add("ejemplo 6");
        list.add("ejemplo 7");
        list.add("ejemplo 8");
        list.add("ejemplo 9");


        //Recycler Monday
        rvMonday = findViewById(R.id.rv_monday);
        LinearLayoutManager mLayout = new LinearLayoutManager(this);
        rvMonday.setLayoutManager(mLayout);
        RecyclerView.Adapter adapter = new MyAdapter(this,list);
        rvMonday.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvMonday.getContext(),
                mLayout.getOrientation());
        rvMonday.addItemDecoration(dividerItemDecoration);

        //Recycler Tuesday
        rvTuesday = findViewById(R.id.rv_tueday);
        mLayout = new LinearLayoutManager(this);
        rvTuesday.setLayoutManager(mLayout);
        adapter = new MyAdapter(this,list);
        rvTuesday.setAdapter(adapter);

        dividerItemDecoration = new DividerItemDecoration(rvTuesday.getContext(),
                mLayout.getOrientation());
        rvTuesday.addItemDecoration(dividerItemDecoration);

        //Recycler Wednesday
        rvWednesday = findViewById(R.id.rv_wednesday);
        mLayout = new LinearLayoutManager(this);
        rvWednesday.setLayoutManager(mLayout);
        adapter = new MyAdapter(this,list);
        rvWednesday.setAdapter(adapter);

        dividerItemDecoration = new DividerItemDecoration(rvWednesday.getContext(),
                mLayout.getOrientation());
        rvWednesday.addItemDecoration(dividerItemDecoration);

        //Recycler Thursday
        rvThursday = findViewById(R.id.rv_thursday);
        mLayout = new LinearLayoutManager(this);
        rvThursday.setLayoutManager(mLayout);
        adapter = new MyAdapter(this,list);
        rvThursday.setAdapter(adapter);

        dividerItemDecoration = new DividerItemDecoration(rvThursday.getContext(),
                mLayout.getOrientation());
        rvThursday.addItemDecoration(dividerItemDecoration);

        //Recycler Friday
        rvFriday = findViewById(R.id.rv_friday);
        mLayout = new LinearLayoutManager(this);
        rvFriday.setLayoutManager(mLayout);
        adapter = new MyAdapter(this,list);
        rvFriday.setAdapter(adapter);

        dividerItemDecoration = new DividerItemDecoration(rvFriday.getContext(),
                mLayout.getOrientation());
        rvFriday.addItemDecoration(dividerItemDecoration);

        //Recycler Saturday
        rvSaturday = findViewById(R.id.rv_saturday);
        mLayout = new LinearLayoutManager(this);
        rvSaturday.setLayoutManager(mLayout);
        adapter = new MyAdapter(this,list);
        rvSaturday.setAdapter(adapter);

        dividerItemDecoration = new DividerItemDecoration(rvSaturday.getContext(),
                mLayout.getOrientation());
        rvSaturday.addItemDecoration(dividerItemDecoration);

        //Recycler Sunday
        rvSunday = findViewById(R.id.rv_sunday);
        mLayout = new LinearLayoutManager(this);
        rvSunday.setLayoutManager(mLayout);
        adapter = new MyAdapter(this,list);
        rvSunday.setAdapter(adapter);

        dividerItemDecoration = new DividerItemDecoration(rvSunday.getContext(),
                mLayout.getOrientation());
        rvSunday.addItemDecoration(dividerItemDecoration);
    }


    /**
     * This method set the days of the week in the UI
     */
    private void setDaysOfWeekUI(){

        TextView monday = findViewById(R.id.tv_monday);
        TextView tuesday = findViewById(R.id.tv_tuesday);
        TextView wednesday = findViewById(R.id.tv_wednesday);
        TextView thursday = findViewById(R.id.tv_thursday);
        TextView friday = findViewById(R.id.tv_friday);
        TextView weekend = findViewById(R.id.tv_weekend);

        ArrayList<Date> showingWeekList = week;

        monday.setText(getString(R.string.monday) + " " + showingWeekList.get(0).getDate());
        tuesday.setText(getString(R.string.tuesday) + " " + showingWeekList.get(1).getDate());
        wednesday.setText(getString(R.string.wednesday) + " " + showingWeekList.get(2).getDate());
        thursday.setText(getString(R.string.thursday) + " " + showingWeekList.get(3).getDate());
        friday.setText(getString(R.string.friday) + " " + showingWeekList.get(4).getDate());
        weekend.setText(getString(R.string.weekend) + " " + showingWeekList.get(5).getDate() + "/" + showingWeekList.get(6).getDate());

    }

    /**
     * This method retrieves all the days of the current week plus the specified number of weeks
     * @param plusWeek Number of weeks plus or minus the current, you want to retrieve the days for
     * @return a List of "Date" with all the days of the specified week
     */
    @NonNull
    public static ArrayList<Date> getWeekDateList(int plusWeek) {
        Calendar cal = Calendar.getInstance();
        if (plusWeek != 0){
            cal.add(Calendar.DAY_OF_MONTH,7*plusWeek);
        }
        //  Set the first day of the week. According to Spanish custom, the first day of the week is Monday
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        //  Get the current date is the day of the week
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if(dayWeek==1){
            dayWeek = 8;
        }

        //  According to the rules of the calendar, subtract the difference between the day of the week and the first day of the week from the current date
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);
        Date mondayDate = cal.getTime();

        cal.add(Calendar.DATE, 4 +cal.getFirstDayOfWeek());
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
     * @return a List of "Date" with all the days of the current week
     */
    @NonNull
    public static ArrayList<Date> getWeekDateList() {
        return getWeekDateList(0);
    }

    private void openDayActivity(int day){
        Intent i = new Intent(this, ActivityDay.class);
        i.putExtra("dayDate", week.get(day));
        startActivity(i);
    }

    public void mondayClick(View view) {
        openDayActivity(0);
    }

    public void tuesdayClick(View view) {
        openDayActivity(1);
    }

    public void wednesdayClick(View view) {
        openDayActivity(2);
    }

    public void ThursdayClick(View view) {
        openDayActivity(3);
    }

    public void fridayClick(View view) {
        openDayActivity(4);
    }

    public void saturdayClick(View view) {
        openDayActivity(5);
    }

    public void sundayClick(View view) {
        openDayActivity(6);
    }
}