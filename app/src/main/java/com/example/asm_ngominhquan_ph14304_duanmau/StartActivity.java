package com.example.asm_ngominhquan_ph14304_duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class StartActivity extends AppCompatActivity {

    private TextView tvTitleStart;
    private LottieAnimationView ltMain;
    private ProgressBar progressBar;
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        tvTitleStart = (TextView) findViewById(R.id.tvTitleStart);
        ltMain = (LottieAnimationView) findViewById(R.id.ltMain);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        progressBar.setMax(100);
        progressBar.setSecondaryProgress(50);
        count = 20;
        progressBar.setProgress(count);
        CountDownTimer countDownTimer = new CountDownTimer(2000,500) {
            @Override
            public void onTick(long millisUntilFinished) {
                count+=35;
                progressBar.setProgress(count);
            }
            @Override
            public void onFinish() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(StartActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },1000);
            }
        }.start();
    }
}