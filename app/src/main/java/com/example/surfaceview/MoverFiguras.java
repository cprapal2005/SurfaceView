package com.example.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class MoverFiguras extends SurfaceView {

    public MoverFiguras(Context context) {
        super(context);
        this.setBackgroundColor(Color.RED);
    }

    @Override
    public void onDraw(Canvas canvas) {

        Paint p = new Paint();
        p.setAntiAlias(true);

        canvas.drawColor(Color.WHITE);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {



        return true;

    }

}
