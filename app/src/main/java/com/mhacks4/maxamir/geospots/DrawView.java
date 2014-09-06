package com.mhacks4.maxamir.geospots;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by MaxSD_000 on 9/6/2014.
 */
public class DrawView extends View {
    private Paint paint;

    public DrawView(Context context){
        super(context);
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas){
        float x_m = (float) (canvas.getWidth()/2.0);
        float y_m = (float) (canvas.getHeight()/2.0);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(x_m, y_m, x_m+80, y_m+80, paint);
    }

}
