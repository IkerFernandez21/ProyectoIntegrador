package com.ifernandez.proyectointegrador;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> mData;
    private LayoutInflater mInflater;
    private Date date;
    private Context context;

    private int pos = 0;

    public int getPos() {
        return this.pos;
    }

    public void decrementarPos() {
        this.pos--;
    }

    ;

    MyAdapter(Context context, List<String> data, Date date) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.date = date;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.myTextView.setText(animal);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    MainActivity ma = (MainActivity) holder.context;

                    switch (date.getDay()){
                        case 0:
                            ma.openDayActivity(6);
                            break;
                        case 1:
                            ma.openDayActivity(0);
                            break;
                        case 2:
                            ma.openDayActivity(1);
                            break;
                        case 3:
                            ma.openDayActivity(2);
                            break;
                        case 4:
                            ma.openDayActivity(3);
                            break;
                        case 5:
                            ma.openDayActivity(4);
                            break;
                        case 6:
                            ma.openDayActivity(5);
                            break;
                    }
                }

            }
        );
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        Context context;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvTareaNombre);
            context = myTextView.getContext();
        }
    }
}


