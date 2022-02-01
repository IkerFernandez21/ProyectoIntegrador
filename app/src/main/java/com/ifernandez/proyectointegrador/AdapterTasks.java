package com.ifernandez.proyectointegrador;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterTasks extends RecyclerView.Adapter<AdapterTasks.ViewHolder> {


    private List<Task> mData;
    private LayoutInflater mInflater;

    SparseBooleanArray checkBoxStateArray = new SparseBooleanArray();

    private int pos = 0;

    public int getPos() {
        return this.pos;
    }

    public void decrementarPos() {
        this.pos--;
    }

    ;

    AdapterTasks(Context context, List<Task> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override

    //crea los nuevos objetos
    //ViewHolder necesarios para los elementos de la colección. En nuestro
    //ejemplo, nos limitamos a inflar (construir) una vista a partir del layout
    //correspondiente a los elementos de la lista (row), y crear y devolver un nuevo view holder

    public AdapterTasks.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_tasks, parent, false);
        return new AdapterTasks.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTasks.ViewHolder holder, int position) {
        Task task = mData.get(position);
        holder.title.setText(task.getTittle());
        holder.description.setText(task.getDescription());

        if (task.isCompleted()){
            holder.checkBox.setChecked(true);
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.title.setTextColor(Color.GRAY);
            holder.description.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.description.setTextColor(Color.GRAY);
        }else{
            holder.checkBox.setChecked(false);
            holder.title.setPaintFlags(holder.title.getPaintFlags()& (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.title.setTextColor(Color.BLACK);
            holder.description.setPaintFlags(holder.title.getPaintFlags()& (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.title.setTextColor(Color.BLACK);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    task.setCompleted(true);
                    notifyItemChanged(position);

                }else{
                    task.setCompleted(false);
                    notifyItemChanged(position);
                }
            }
        });

        holder.title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //blank;
            }

            @Override
            public void afterTextChanged(Editable s) {
                mData.get(position).setTittle(holder.title.getText().toString());
            }
        });

        holder.description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //blank;
            }

            @Override
            public void afterTextChanged(Editable s) {
                mData.get(position).setDescription(holder.description.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        EditText title;
        EditText description;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_tittle_row);
            description = itemView.findViewById(R.id.task_description_row);
            checkBox = itemView.findViewById(R.id.checkboxRow);


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //getAdapter nos devuelve la posicion clickead

                    int position =getAdapterPosition();
                    if(!checkBoxStateArray.get(position,false)){
                        //checkbox checked

                        checkBoxStateArray.put(position,true);
                    } else {
                        //sin clickear
                        checkBox.setChecked(false);
                        checkBoxStateArray.put(position,false);

                    }

                }
            });
        }
    }
}

