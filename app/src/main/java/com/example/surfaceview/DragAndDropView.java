package com.example.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class DragAndDropView extends SurfaceView implements SurfaceHolder.Callback {

    private ArrayList<Figura> figuras;
    private int figuraActiva, id=0;
    private float iniX=0, iniY=0;
    private boolean dentro = false;
    private Rectangulo rectangulo;
    private hiloPintar thread;

    public DragAndDropView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setBackgroundColor(Color.WHITE);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        figuras = new ArrayList<Figura>();
        figuras.add(new Circulo(this.id++, 200, 200, 100));
        this.rectangulo = new Rectangulo(this.id++, 200, 500, 200, 200);
        figuras.add(this.rectangulo);
        thread = new hiloPintar(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {}
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        setBackgroundColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        for (int i = 0; i < figuras.size(); i++) {
            if(figuras.get(i) instanceof Rectangulo) {
                paint.setColor(Color.RED);
                canvas.drawRect(figuras.get(i).getX(), figuras.get(i).getY(), (figuras.get(i).getX()+ ((Rectangulo) figuras.get(i)).getAncho()), (figuras.get(i).getY()+ ((Rectangulo) figuras.get(i)).getAlto()), paint);
            }
            if(figuras.get(i) instanceof Circulo) {
                paint.setColor(Color.BLUE);
                canvas.drawCircle(figuras.get(i).getX(), figuras.get(i).getY(), ((Circulo) figuras.get(i)).getRadio(), paint);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < figuras.size(); i++) {
                    if(figuras.get(i).estaDentro(event.getX(), event.getY())) {
                        this.dentro = true;
                        iniX = event.getX() - figuras.get(i).getX();
                        iniY = event.getY() - figuras.get(i).getY();
                        figuraActiva = i;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < figuras.size(); i++) {
                    if(this.dentro) {
                        figuras.get(figuraActiva).setX((event.getX() - iniX));
                        figuras.get(figuraActiva).setY((event.getY() - iniY));
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                this.dentro=false;
                break;
        }
        return true;

    }

}
