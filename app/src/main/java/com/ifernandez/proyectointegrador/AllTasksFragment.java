package com.ifernandez.proyectointegrador;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AllTasksFragment extends Fragment {

    private RecyclerView rvAllTasks;
    private LinearLayoutManager mLayout;
    private Vault vault;
    private ArrayList<Day> daysList;
    private ArrayList<Task> taskList;

    public AllTasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_all_tasks, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpRecycler();
    }

    private void setUpRecycler() {

        vault = new Vault(getActivity().getFilesDir());
        daysList = vault.getDaysList();
        taskList = new ArrayList<>();

        for (Day day : daysList) {
            ArrayList<Task> dayTaskList = day.getTaskList();

            for (Task task: dayTaskList) {
                if (!task.isCompleted()) {
                    taskList.add(task);
                }
            }
        }

        rvAllTasks = getActivity().findViewById(R.id.rv_all_tasks);
        mLayout = new LinearLayoutManager(getActivity());
        rvAllTasks.setLayoutManager(mLayout);
        RecyclerView.Adapter adapter = new AdapterAllTasks(getActivity(), taskList);
        rvAllTasks.setAdapter(adapter);

    }
}