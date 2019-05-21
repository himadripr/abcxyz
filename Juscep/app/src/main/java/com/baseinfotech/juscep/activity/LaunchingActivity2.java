package com.baseinfotech.juscep.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.baseinfotech.juscep.R;

public class LaunchingActivity2 extends AppCompatActivity {

    private static final long START_TIME = 5000;
    private static final long INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launching2);
        MyCountDownTimer myCountDownTimer = new MyCountDownTimer(START_TIME, INTERVAL);
        myCountDownTimer.start();

    }

    public void startAskPermissionActivity(){
        startActivity(new Intent(this, AskPermissionActivity.class));
        finish();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //for getting full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private class MyCountDownTimer extends CountDownTimer {

        MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            startAskPermissionActivity();
        }
    }
}