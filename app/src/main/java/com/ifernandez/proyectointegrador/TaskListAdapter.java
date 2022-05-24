package com.ifernandez.proyectointegrador;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    List<helper>  itemList1;
    private Context context;

    public TaskListAdapter(List<helper>   itemList, Context context) {

        this.itemList1=itemList;
        this.context=context;

    }

    @NonNull
    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowtasklist,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListAdapter.ViewHolder holder, final int position) {
            helper help = itemList1.get(position);




            holder.titulo.setText(help.getTitulo());
            holder.dia.setText(help.getDay());






    }

    @Override
    public int getItemCount() {
        return itemList1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dia;
        TextView titulo;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dia=itemView.findViewById(R.id.dia);
            titulo=itemView.findViewById(R.id.titulo);
            linearLayout=itemView.findViewById(R.id.layout_tasklist);

        }
    }
}
