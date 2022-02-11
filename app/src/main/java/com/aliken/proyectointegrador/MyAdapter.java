package com.aliken.proyectointegrador;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ifernandez.proyectointegrador.R;

import java.util.Date;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Task> mData;
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

    MyAdapter(Context context, List<Task> data, Date date) {
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
        Task task = mData.get(position);
        holder.title.setText(task.getTittle());
        if (task.getColor()!=0){
            setTextColor(holder.title,task.getColor());
        }

        if (task.isCompleted()){
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.title.setTextColor(Color.GRAY);
        }else{
            holder.title.setPaintFlags(holder.title.getPaintFlags()& (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.title.setTextColor(Color.BLACK);
        }

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
        TextView title;
        Context context;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTareaNombre);
            context = title.getContext();
        }
    }

    public void setTextColor(TextView textView, int color){
        int padding = 1;

        SpannableString spannable = new SpannableString(textView.getText());
        spannable.setSpan(
                new MyLineBackgroundSpan(color, padding),
                0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannable);
    }
}


