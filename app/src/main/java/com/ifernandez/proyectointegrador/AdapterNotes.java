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
import java.util.List;

import io.objectbox.Box;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.ViewHolder> {
    private final List<Note> mData;
    private final LayoutInflater mInflater;
    private final Context context;
    private final NotesScreen activity;
    private Box<Note> noteBox;

    private int pos = 0;

    public AdapterNotes(Context context, List<Note> data, NotesScreen activity) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.activity = activity;
        this.noteBox = ObjectBox.get().boxFor(Note.class);
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
                long noteId = mData.get(position).getId();
                mData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(0, mData.size());
                decrementarPos();
                noteBox.remove(noteId);
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


