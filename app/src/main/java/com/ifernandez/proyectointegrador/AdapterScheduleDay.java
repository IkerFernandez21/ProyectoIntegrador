package com.ifernandez.proyectointegrador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterScheduleDay extends RecyclerView.Adapter<AdapterScheduleDay.ViewHolder> {
    private List<ScheduleItem> mData;
    private LayoutInflater mInflater;
    private Context context;

    private int pos = 0;

    public int getPos() {
        return this.pos;
    }

    public void decrementarPos() {
        this.pos--;
    }

    ;

    AdapterScheduleDay(Context context, List<ScheduleItem> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleItem scheduleItem = mData.get(position);
        holder.text.setText(scheduleItem.getText());
        holder.time.setText(scheduleItem.getTime());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText time;
        EditText text;

        ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.schedule_time);
            text = itemView.findViewById(R.id.schedule_text);
        }
    }
}


