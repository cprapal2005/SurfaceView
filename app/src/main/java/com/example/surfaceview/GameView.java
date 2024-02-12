package com.example.surfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private Bitmap bmpBlood;
    private long lastClick = 0;
    private GameLoopThread gameLoopThread;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private List<TempSprite> temps = new ArrayList<TempSprite>();

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameLoopThread = new GameLoopThread(this, holder);
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
        bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
        createSprites();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        gameLoopThread.setRunning(false);

        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            } catch (InterruptedException e) { throw new RuntimeException(e); }
        }

    }

    @Override
    public void onDraw(Canvas canvas) {
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }
        for (TempSprite tempSprite : temps) {
            tempSprite.onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(System.currentTimeMillis() - lastClick > 500) lastClick = System.currentTimeMillis();

        synchronized (getHolder()) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    for (int i = sprites.size() - 1; i >= 0; i--) {
                        Sprite sprite = sprites.get(i);
                        if (sprite.isCollition(event.getX(), event.getY())){
                            sprites.remove(sprite);
                            temps.add(new TempSprite(temps, this, event.getX(), event.getY(), bmpBlood));
                        }
                    }
                    break;
            }

        }
        return true;

    }
    private void createSprites() {
        sprites.add(createSprite(R.drawable.bad1));
        sprites.add(createSprite(R.drawable.bad2));
        sprites.add(createSprite(R.drawable.bad3));
        sprites.add(createSprite(R.drawable.bad4));
        sprites.add(createSprite(R.drawable.bad5));
        sprites.add(createSprite(R.drawable.bad5));
    }

    private Sprite createSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this, bmp);
    }

}
