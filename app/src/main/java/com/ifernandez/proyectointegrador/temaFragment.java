package com.ifernandez.proyectointegrador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import yuku.ambilwarna.AmbilWarnaDialog;


public class temaFragment extends Fragment {
    private Button mSetColorButton;
    private SharedPreferences prefrencias;
    private Spinner sp;
    private String seleccion;
    private View view,viewMain;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewMain = inflater.inflate(R.layout.content_main, container, false);
        view = inflater.inflate(R.layout.fragment_tema, container, false);

        String[] temas = {getString(R.string.green),getString(R.string.mustard),getString(R.string.blue),getString(R.string.bluenorange),getString(R.string.pink),getString(R.string.gray)};
        mSetColorButton = view.findViewById(R.id.bt2);
        sp = view.findViewById(R.id.spinner);
        prefrencias = getActivity().getSharedPreferences("MisPrefrencias", Context.MODE_PRIVATE);
        sp.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,temas));
        sp.setSelection(0);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (sp.getSelectedItemPosition()){
                    case 0: seleccion = "Verde"; break;
                    case 1: seleccion = "Mostaza"; break;
                    case 2: seleccion = "Azul"; break;
                    case 3: seleccion = "Azul y naranja"; break;
                    case 4: seleccion = "Rosa"; break;
                    case 5: seleccion = "Gris"; break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                seleccion = "Verde";
            }
        });

        mSetColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = prefrencias.edit();
                editor.putString("tema",seleccion);
                editor.commit();

                Intent intent1 = new Intent(getContext(), MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);


            }
        });



        return view;

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}