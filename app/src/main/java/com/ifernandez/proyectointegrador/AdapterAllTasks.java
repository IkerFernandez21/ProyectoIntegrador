package com.ifernandez.proyectointegrador;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class AdapterAllTasks extends RecyclerView.Adapter<AdapterAllTasks.ViewHolder> {
    private List<Task> mData;
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

    AdapterAllTasks(Context context, List<Task> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_all_tasks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = mData.get(position);
        holder.title.setText(task.getTittle());
        holder.date.setText(task.getDate().toString());

        if (task.getDescription() != null && !task.getDescription().equals("")) {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(task.getDescription());
        }

        setTextColor(holder.title, task.getColor(), task);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView date;
        Context context;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.all_task_tittle_row);
            description = itemView.findViewById(R.id.all_task_description_row);
            date = itemView.findViewById(R.id.all_task_date);
            context = title.getContext();
        }
    }

    public void setTextColor(TextView textView, int color, Task task) {
        int padding = 1;

        SpannableString spannable = new SpannableString(textView.getText());


        if (color != 0) {
            spannable.setSpan(
                    new MyLineBackgroundSpan(color, padding),
                    0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(Color.WHITE),
                    0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        if (task.isCompleted()) {
            spannable.setSpan(
                    new StrikethroughSpan(),
                    0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(Color.GRAY),
                    0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        textView.setText(spannable);
    }
}


