package com.aliken.proyectointegrador;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.LineBackgroundSpan;

public class MyLineBackgroundSpan
        implements LineBackgroundSpan {

    private int backgroundColor = 0;
    private int padding = 0;

    public MyLineBackgroundSpan(int backgroundColor, int padding) {
        this.backgroundColor = backgroundColor;
        this.padding = padding;
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        final int textWidth = Math.round(p.measureText(text, start, end));
        final int paintColor = p.getColor();
        final Paint.FontMetrics fm = p.getFontMetrics();

        // using fontMetrics is to free to lineSpacingExtra or includeFontPadding of TextView attributes.

        RectF rect = new RectF(left - padding,
                baseline + fm.ascent - padding,
                left + textWidth + padding,
                baseline + fm.descent + padding);

        p.setColor(backgroundColor);
        c.drawRect(rect, p);

        p.setColor(paintColor);
    }
}