package com.example.surfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

public class DragAndDropView extends SurfaceView implements SurfaceHolder.Callback {

    private ArrayList<Figura> figuras;
    private ArrayList<Integer> colores;
    private int figuraActiva, id=0, contFiguras = 2, numeroFiguras = 14, contPuntos = 0;
    private float iniX=0, iniY=0;
    private boolean dentro = false;
    private hiloPintar thread;
    private Button botonReset, botonAnadir;
    private TextView puntos, countdownTextView;
    private RelativeLayout layout;
    private Canvas canvas;
    private int secondsLeft = 10;
    private Handler handler;

    public DragAndDropView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setBackgroundColor(Color.WHITE);
        colores = new ArrayList<Integer>();
        colores.add(Color.RED);colores.add(Color.RED);
        colores.add(Color.BLUE);colores.add(Color.BLUE);
        colores.add(Color.BLACK);colores.add(Color.BLACK);
        colores.add(Color.YELLOW);colores.add(Color.YELLOW);
        colores.add(Color.MAGENTA);colores.add(Color.MAGENTA);
        colores.add(Color.GREEN);colores.add(Color.GREEN);

        countdownTextView = new TextView(context);
        countdownTextView.setText("60");
        RelativeLayout.LayoutParams countdownTextViewParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        countdownTextViewParams.addRule(RelativeLayout.ALIGN_START);
        countdownTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        puntos = new TextView(context);
        puntos.setText("Puntos: " + contPuntos + " / " + numeroFiguras/2);
        RelativeLayout.LayoutParams puntosParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        puntosParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        puntosParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        botonReset = new Button(context);
        botonReset.setText("Reset");
        botonReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(secondsLeft==0) {
                    crearFiguras();
                    contPuntos = 0;
                    puntos.setText("Puntos: " + contPuntos + " / " + numeroFiguras/2);
                    secondsLeft = 10;
                    thread = new hiloPintar(getHolder(), DragAndDropView.this);
                    thread.setRunning(true);
                    thread.start();
                    handler = new Handler();
                    startCountdownThread();
                }

            }
        });
        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        botonAnadir = new Button(context);
        botonAnadir.setText("Añadir");
        botonAnadir.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contFiguras+4<=numeroFiguras) contFiguras+=2;
            }
        });
        RelativeLayout.LayoutParams buttonParamsAnadir = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        buttonParamsAnadir.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout = new RelativeLayout(context);
        layout.addView(this);
        layout.addView(countdownTextView, countdownTextViewParams);
        layout.addView(puntos, puntosParams);
        layout.addView(botonAnadir, buttonParamsAnadir);
        layout.addView(botonReset, buttonParams);

    }

    public RelativeLayout getLayout() {
        return layout;
    }

    private void crearFiguras() {
        contFiguras=2;
        figuras = new ArrayList<Figura>();
        int minX = 0;
        int maxX = layout.getWidth()-200;
        int minY = 0;
        int maxY = layout.getHeight()-200;
        for (int i = 0; i < numeroFiguras; i++) {
            Random random = new Random();
            int randomX = random.nextInt(maxX - minX + 1) + minX;
            int randomY = random.nextInt(maxY - minY + 1) + minY;
            if(i%2==0) {
                figuras.add(new Circulo(this.id++, (float) randomX, (float) randomY, 100, false));
                 randomX = random.nextInt(maxX - minX + 1) + minX;
                 randomY = random.nextInt(maxY - minY + 1) + minY;
                figuras.add(new Circulo(this.id++, (float) randomX, (float) randomY, 100, true));
            }
            else {
                figuras.add(new Rectangulo(this.id++, 200, 500, (float) randomX, (float) randomY, false));
                 randomX = random.nextInt(maxX - minX + 1) + minX;
                 randomY = random.nextInt(maxY - minY + 1) + minY;
                figuras.add(new Rectangulo(this.id++, 200, 500, (float) randomX, (float) randomY, true));
            }


        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        //Imagen Fondo
        //Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
        //Bitmap img = bmp.createScaledBitmap(bmp, getWidth()*0.2, getHeight()*0.2, true);
        //canvas.drawBitmap(img, iniX, iniY, null);

        //Musica
        //setVolumeControlStream();

        thread = new hiloPintar(getHolder(), this);
        thread.setRunning(true);
        thread.start();
        handler = new Handler();

        // Inicia el hilo de cuenta regresiva
        startCountdownThread();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        crearFiguras();
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

    private void dibujarFiguras(Paint paint) {

        for (int i = 0; i < contFiguras; i++) {
            if(figuras.get(i).isRelleno()) paint.setStyle(Paint.Style.FILL);
            else paint.setStyle(Paint.Style.STROKE);
            if(figuras.get(i) instanceof Rectangulo) {
                paint.setColor(colores.get(i));
                canvas.drawRect(figuras.get(i).getX(), figuras.get(i).getY(), (figuras.get(i).getX()+ ((Rectangulo) figuras.get(i)).getAncho()), (figuras.get(i).getY()+ ((Rectangulo) figuras.get(i)).getAlto()), paint);
            }
            if(figuras.get(i) instanceof Circulo) {
                paint.setColor(colores.get(i));
                canvas.drawCircle(figuras.get(i).getX(), figuras.get(i).getY(), ((Circulo) figuras.get(i)).getRadio(), paint);
            }
        }

    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        setBackgroundColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        this.canvas = canvas;
        dibujarFiguras(paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < figuras.size(); i++) {
                    if(figuras.get(i).estaDentro(event.getX(), event.getY()) && figuras.get(i).isRelleno()) {
                        this.dentro = true;
                        iniX = event.getX() - figuras.get(i).getX();
                        iniY = event.getY() - figuras.get(i).getY();
                        figuraActiva = i;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < figuras.size(); i++) {
                    if(this.dentro && !figuras.get(figuraActiva-1).estaEnRadioCentral(figuras.get(figuraActiva).getX(), figuras.get(figuraActiva).getY(), 40)) {
                        figuras.get(figuraActiva).setX((event.getX() - iniX));
                        figuras.get(figuraActiva).setY((event.getY() - iniY));
                        if (figuras.get(figuraActiva-1).estaEnRadioCentral(figuras.get(figuraActiva).getX(), figuras.get(figuraActiva).getY(), 40)) {
                            figuras.get(figuraActiva).setX(figuras.get(figuraActiva-1).getX());
                            figuras.get(figuraActiva).setY(figuras.get(figuraActiva-1).getY());
                            contPuntos++;
                            puntos.setText("Puntos: " + contPuntos + " / " + numeroFiguras/2);
                            break;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                this.dentro=false;
                break;
        }
        return true;

    }

    private void startCountdownThread() {
        Thread countdownThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (secondsLeft > 0) {
                    try {
                        // Pausa el hilo durante un segundo
                        Thread.sleep(1000);

                        // Actualiza la interfaz de usuario en el hilo principal
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                updateUI();
                            }
                        });

                        // Decrementa el contador
                        secondsLeft--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                boolean retry = true;
                thread.setRunning(false);
                while (retry) {
                    try {
                        thread.join();
                        retry = false;
                    } catch (InterruptedException e) {}
                }
            }
        });

        // Inicia el hilo
        countdownThread.start();
    }

    private void updateUI() {
        countdownTextView.setText(String.valueOf(secondsLeft));
    }

}
