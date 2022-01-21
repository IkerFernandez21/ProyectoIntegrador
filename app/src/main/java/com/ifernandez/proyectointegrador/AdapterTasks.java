package com.ifernandez.proyectointegrador;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterTasks extends RecyclerView.Adapter<AdapterTasks.ViewHolder> {

    private List<Task> mData;
    private LayoutInflater mInflater;

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
    public AdapterTasks.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_tasks, parent, false);
        return new AdapterTasks.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTasks.ViewHolder holder, int position) {
        Task task = mData.get(position);
        holder.title.setText(task.getTittle());
        holder.description.setText(task.getDescription());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText title;
        EditText description;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_tittle_row);
            description = itemView.findViewById(R.id.task_description_row);
        }
    }
}
