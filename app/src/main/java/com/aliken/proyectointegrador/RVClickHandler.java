package com.aliken.proyectointegrador;

import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RVClickHandler implements View.OnTouchListener {

    private RecyclerView mRecyclerView;
    private float mStartX;
    private float mStartY;

    public RVClickHandler(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean isConsumed = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mStartX = event.getX();
                mStartY = event.getY();
                break;
            }
            case MotionEvent.ACTION_UP: {
                float endX = event.getX();
                float endY = event.getY();
                if(detectClick(mStartX, mStartY, endX, endY)) {
                    //Ideally it would never be called when a child View is clicked.
                    //I am not so sure about this.
                    View itemView = mRecyclerView.findChildViewUnder(endX, endY);
                    if(itemView == null) {
                        //RecyclerView clicked
                        mRecyclerView.performClick();
                        isConsumed = true;
                    }
                }
                break;
            }
        }
        return isConsumed;
    }

    private static boolean detectClick(float startX, float startY, float endX, float endY) {
        return Math.abs(startX-endX) < 3.0 && Math.abs(startY-endY) < 3.0;
    }

}
