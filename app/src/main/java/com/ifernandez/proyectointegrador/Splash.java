package com.ifernandez.proyectointegrador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = (ImageView) findViewById(R.id.iv1);

        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        logo.startAnimation(scale);

        openApp(true);
    }

    private void openApp(boolean LocationPermision){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent(Splash.this, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
            }
        },1100);
    }
    }
}