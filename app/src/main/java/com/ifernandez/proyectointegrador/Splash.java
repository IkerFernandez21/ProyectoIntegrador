package com.ifernandez.proyectointegrador;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    ImageView logo;
    TextView tx ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = (ImageView) findViewById(R.id.imageView3);
        tx = findViewById(R.id.textView);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.logoanim);//logo animation
        logo.startAnimation(anim);
        Animation animtexto = AnimationUtils.loadAnimation(this, R.anim.logoanimtext);//text animation
        tx.startAnimation(animtexto);


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
        },3400);
    }
    }
