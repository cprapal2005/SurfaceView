package com.example.surfaceview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class TestActivity extends Activity {

    private View miVista;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        DragAndDropView miVista = new DragAndDropView(this);
        setContentView(miVista.getLayout());
    }
}
