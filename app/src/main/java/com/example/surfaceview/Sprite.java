package com.example.surfaceview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Sprite {
    private static final int BMP_ROWS = 4, BMP_COLUMS= 3, MAX_SPEED = 10;
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame = 0, width, height, x, y, ySpeed, xSpeed;
    private int[] DIRECTION_TO_ANIMATION_MAP = {3,1,0,2};

    public Sprite(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMS;
        this.height = bmp.getHeight() / BMP_ROWS;

        Random rnd = new Random();
        x = rnd.nextInt(gameView.getWidth() - width);
        y = rnd.nextInt(gameView.getHeight() - height);
        xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
    }

    private void update() {
        if(x > gameView.getWidth() - width - xSpeed || x + xSpeed < 0) xSpeed = -xSpeed;
        x = x + xSpeed;
        if(y > gameView.getWidth() - height - ySpeed || y + ySpeed < 0) ySpeed = -ySpeed;
        y = y + ySpeed;
        currentFrame = ++currentFrame % BMP_COLUMS;
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) (Math.round(dirDouble) % BMP_ROWS);
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public boolean isCollition(float x2, float y2) {

        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;

    }


}
