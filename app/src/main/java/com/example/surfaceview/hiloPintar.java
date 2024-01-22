package com.example.surfaceview;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class hiloPintar extends Thread {

    private SurfaceHolder sh;
    private DragAndDropView view;
    private boolean run;

    public hiloPintar(SurfaceHolder sh, DragAndDropView view) {
        this.sh = sh;
        this.view = view;
        run = false;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (run) {
            canvas = null;
            try {
                canvas = sh.lockCanvas(null);
                if(canvas!=null) {
                    synchronized (sh){view.postInvalidate();}
                }
            }
            finally {
                if(canvas!=null) sh.unlockCanvasAndPost(canvas);
            }
        }

    }

}
