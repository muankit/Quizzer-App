package com.developer.ankit.quizzer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import java.net.Inet4Address;

public class SplashScreenActivty extends AppCompatActivity {


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_activty);

        mAuth = FirebaseAuth.getInstance();

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);

                    Intent loginIntent = new Intent(SplashScreenActivty.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        myThread.start();
    }
}
