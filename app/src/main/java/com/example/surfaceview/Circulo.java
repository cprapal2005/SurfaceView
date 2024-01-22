package com.example.surfaceview;

public class Circulo extends Figura {

    private int id, radio;
    private float x,y;
    public Circulo(int id, float x, float y, int radio) {
        super(x, y);
        this.id = id;
        this.radio = radio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }

    @Override
    public boolean estaDentro(float x, float y) {
       if(Math.pow(this.radio, 2) > (Math.pow(x-this.x, 2) + Math.pow(y-this.y, 2))) {

           return true;

       }

       else return false;
    }
}
