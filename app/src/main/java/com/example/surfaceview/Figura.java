package com.example.surfaceview;

public abstract class Figura {

    private float x;
    private float y;

    private boolean relleno;

    public Figura(float x, float y, boolean relleno) {
        this.x = x;
        this.y = y;
        this.relleno = relleno;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isRelleno() {
        return relleno;
    }

    public void setRelleno(boolean relleno) {
        this.relleno = relleno;
    }

    public abstract boolean estaDentro(float x, float y);

    public abstract boolean estaEnRadioCentral(float x, float y, float radio);

}
