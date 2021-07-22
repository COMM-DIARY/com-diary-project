package com.example.cd;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        Thread thread = new Thread(() -> {
            try {
                sleep(5000);
                Intent in = new Intent(SplashScreen.this,Hp.class);
                startActivity(in);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}