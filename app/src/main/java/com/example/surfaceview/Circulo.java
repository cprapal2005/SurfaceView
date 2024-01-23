package com.example.surfaceview;

public class Circulo extends Figura {

    private int id, radio;
    public Circulo(int id, float x, float y, int radio, boolean relleno) {
        super(x, y, relleno);
        this.id = id;
        this.radio = radio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }

    @Override
    public boolean estaDentro(float x, float y) {
       if(Math.pow(this.radio, 2) > (Math.pow(x-super.getX(), 2) + Math.pow(y-super.getY(), 2))) {

           return true;

       }

       else return false;
    }

    public boolean estaEnRadioCentral(float x, float y, float radioAproximado) {

        double distancia = Math.sqrt(Math.pow(x - super.getX(), 2) + Math.pow(y - super.getY(), 2));

        return distancia <= radioAproximado;
    }

}
