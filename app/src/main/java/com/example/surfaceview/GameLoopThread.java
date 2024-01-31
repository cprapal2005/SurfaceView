package com.example.surfaceview;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoopThread extends Thread{
    static final long FPS = 10;
    private GameView view;
    private boolean running;
    private SurfaceHolder holder;

    public GameLoopThread(GameView view, SurfaceHolder holder) {
        this.view = view;
        this.holder = holder;
        this.running = false;
    }

    @Override
    public void run() {
        long ticksFPS = 1000 / FPS;
        long startTime = 0;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.postInvalidate();
                }
            } finally {
                if(c != null) view.getHolder().unlockCanvasAndPost(c);
            }
        }
        sleepTime = ticksFPS-(System.currentTimeMillis() - startTime);
        try {
            if(sleepTime > 0) this.sleep(sleepTime);
            else this.sleep(10);
        } catch (InterruptedException e) {throw new RuntimeException();}

    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
