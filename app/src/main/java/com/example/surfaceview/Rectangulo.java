package com.example.surfaceview;

public class Rectangulo extends Figura {

    private int id, alto, ancho;

    public Rectangulo(int id, int alto, int ancho, float x, float y, boolean relleno) {
        super(x, y, relleno);
        this.id = id;
        this.alto = alto;
        this.ancho = ancho;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    @Override
    public boolean estaDentro(float x, float y) {
        if(x>=super.getX() && y>=super.getY() && x<=ancho+super.getX() && y<=alto+super.getY()) return true;
        else return false;
    }

    public boolean estaEnRadioCentral(float x, float y, float radio) {

        double distancia = Math.sqrt(Math.pow(x - super.getX(), 2) + Math.pow(y - super.getY(), 2));

        return distancia <= radio;
    }

}
