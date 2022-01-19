package com.ifernandez.proyectointegrador;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> mData;
    private LayoutInflater mInflater;

    private int pos = 0;
    public int getPos(){return this.pos;}
    public void decrementarPos(){this.pos --;};

    MyAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
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

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                TextView myTV = view.findViewById(R.id.tvTareaNombre);
                ColorDrawable viewColor = (ColorDrawable) myTV.getBackground();

                if (viewColor == null) myTV.setBackgroundColor(Color.RED);
                else{
                    int colorId = viewColor.getColor();
                    if (colorId == Color.WHITE) myTV.setBackgroundColor(Color.RED);
                    else myTV.setBackgroundColor(Color.WHITE);
                }

                pos = position;
            }
        }
        );

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){
                TextView myTV = view.findViewById(R.id.tvTareaNombre);
                myTV.setBackgroundColor(Color.BLUE);
                return false;
            }
        });

    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView myTextView;

        ViewHolder(View itemView){
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvTareaNombre);
        }
    }
}
