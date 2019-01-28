package com.bignerdranch.android.justspin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CustomProgressBar extends View {
    // % value of the progressbar.
    int progressBarValue = 0;

    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressBarValue = attrs.getAttributeIntValue(null, "progressBarValue", 0);
    }

    public void setValue(int value) {
        progressBarValue = value;
        invalidate();
    }
    public int getValue(){
        return progressBarValue;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float cornerRadius = 30.0f;

        // Draw the background of the bar.
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.CYAN);
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setAntiAlias(true);

        RectF backgroundRect = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawRoundRect(backgroundRect, cornerRadius, cornerRadius, backgroundPaint);

        // Draw the progress bar.
        Paint barPaint = new Paint();
        barPaint.setColor(Color.RED);
        barPaint.setStyle(Paint.Style.FILL);
        barPaint.setAntiAlias(true);

        float progress = (backgroundRect.width() / 100) * progressBarValue;
        RectF barRect = new RectF(0, 0, progress, canvas.getClipBounds().bottom);

        canvas.drawRoundRect(barRect, cornerRadius, cornerRadius, barPaint);

    }
}
