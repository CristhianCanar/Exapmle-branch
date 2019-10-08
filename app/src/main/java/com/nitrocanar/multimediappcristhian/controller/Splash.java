package com.nitrocanar.multimediappcristhian.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.nitrocanar.multimediappcristhian.R;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
/*        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this,MenuPricipal.class));
            }
        };

        Timer timer = new Timer();

        timer.schedule(timerTask,3000);
    }
}
