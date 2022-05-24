package com.ifernandez.proyectointegrador;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListaTasks extends Fragment {

    RecyclerView recyclerView;

    List<helper> listaDiaTitulo = new ArrayList<helper>();
    private Vault vault;
    private ArrayList<Day> daysList;
    private ArrayList<Task> taskList;

    private String dia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_lista_tasks, container, false);

        vault = new Vault(getContext().getFilesDir());
        daysList = vault.getDaysList();
        recyclerView=view.findViewById(R.id.recyclerTaskList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initData();

        recyclerView.setAdapter(new TaskListAdapter(initData(),getContext()));



        return view;
    }

    private List<helper> initData() {



        for (Day d:daysList) {
            dia = d.getDate().toString();
            taskList = d.getTaskList();
            if(taskList.size()!=0){
                for (int i = 0; i < taskList.size(); i++) {

                    listaDiaTitulo.add(new helper(dia,taskList.get(i).getTittle()));
                }
            }

        }






        return listaDiaTitulo;
    }
}