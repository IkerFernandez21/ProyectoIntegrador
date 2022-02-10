package com.aliken.proyectointegrador;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ifernandez.proyectointegrador.R;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.List;

public class AdapterTasks extends RecyclerView.Adapter<AdapterTasks.ViewHolder> {


    private List<Task> mData;
    private LayoutInflater mInflater;
    private RecyclerView mRecycler;
    private ActionMode aMode;

    SparseBooleanArray checkBoxStateArray = new SparseBooleanArray();

    private int pos = 0;

    public int getPos() {
        return this.pos;
    }

    public void decrementarPos() {
        this.pos--;
    };


    AdapterTasks(Context context, List<Task> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.mRecycler = recyclerView;
    }

    @Override

    //crea los nuevos objetos
    //ViewHolder necesarios para los elementos de la colección. En nuestro
    //ejemplo, nos limitamos a inflar (construir) una vista a partir del layout
    //correspondiente a los elementos de la lista (row), y crear y devolver un nuevo view holder

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_tasks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTasks.ViewHolder holder, int position) {
        Task task = mData.get(position);
        holder.title.setText(task.getTittle());
        if (task.getColor()!=0){
            setTextColor(holder.title,task.getColor());
        }
        holder.description.setText(task.getDescription());
        ActivityDay ad = (ActivityDay) holder.context;
        ActionMode[] actionMode = new ActionMode[1];

        ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

            // Called when the action mode is created; startActionMode() was called
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate a menu resource providing context menu items
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.keyboard_menu, menu);
                return true;
            }

            // Called each time the action mode is shown. Always called after onCreateActionMode, but
            // may be called multiple times if the mode is invalidated.
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false; // Return false if nothing is done
            }

            // Called when the user selects a contextual menu item
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.reset:

                        setTextColor(holder.title,Color.TRANSPARENT);
                        task.setColor(Color.TRANSPARENT);
                        String place = holder.title.getText().toString();
                        holder.title.setText(null);
                        holder.title.setText(place);
                        return true;
                    case R.id.redCricle:

                        setTextColor(holder.title,Color.RED);
                        task.setColor(Color.RED);
                        return true;
                    default:
                        return false;
                }
            }

            // Called when the user exits the action mode
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                aMode = null;
            }
        };

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
                    if (!mRecycler.isComputingLayout() && mRecycler.getScrollState() == SCROLL_STATE_IDLE) {
                        notifyItemChanged(position);
                    }

                }else{
                    task.setCompleted(false);
                    if (!mRecycler.isComputingLayout() && mRecycler.getScrollState() == SCROLL_STATE_IDLE) {
                        notifyItemChanged(position);
                    }
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
                try {
                    mData.get(position).setTittle(holder.title.getText().toString());
                }catch (IndexOutOfBoundsException e){

                }
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
                try {
                    mData.get(position).setDescription(holder.description.getText().toString());
                }catch (IndexOutOfBoundsException e){

            }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(0,mData.size());
                decrementarPos();
            }
        });

        KeyboardVisibilityEvent.setEventListener(
                ad,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen) {
                           aMode = ad.startActionMode(actionModeCallback);
                        }else{
                            if(aMode!=null){aMode.finish();}
                        }
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
        ImageButton delete;
        Context context;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_tittle_row);
            description = itemView.findViewById(R.id.task_description_row);
            checkBox = itemView.findViewById(R.id.checkboxRow);
            delete = itemView.findViewById(R.id.deleteRowButton);
            context = title.getContext();
        }
    }

    public void setTextColor(EditText editText, int color){
        int padding = 10;

        SpannableString spannable = new SpannableString(editText.getText());
        spannable.setSpan(
                new MyLineBackgroundSpan(color, padding),
                0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        editText.setText(spannable);
    }
}

