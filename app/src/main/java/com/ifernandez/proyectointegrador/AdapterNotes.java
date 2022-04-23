package com.ifernandez.proyectointegrador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.ViewHolder> {
    private final ArrayList<Note> mData;
    private final LayoutInflater mInflater;
    private final Context context;
    private final NotesScreen activity;

    private int pos = 0;

    public AdapterNotes(Context context, ArrayList<Note> data, NotesScreen activity) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.activity = activity;
    }

    public int getPos() {
        return this.pos;
    }

    public void decrementarPos() {
        this.pos--;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = mData.get(position);
        holder.title.setText(note.getTittle());
        holder.description.setText(note.getDescription());

        //Button delete note
        holder.delRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(0, mData.size());
                decrementarPos();
                //TODO Implementar borrado de nota
            }
        });

        //Save the edit text content as it changes
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openNote(position);
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openNote(position);
            }
        });

        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openNote(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        Context context;
        ImageButton delRow;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_tittle_row);
            description = itemView.findViewById(R.id.note_description_row);
            delRow = itemView.findViewById(R.id.delRowButton);
            context = title.getContext();
        }
    }
}


