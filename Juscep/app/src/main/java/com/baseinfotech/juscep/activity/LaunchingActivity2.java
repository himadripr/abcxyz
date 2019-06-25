package com.baseinfotech.juscep.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.baseinfotech.juscep.R;
import com.baseinfotech.juscep.model.User;
import com.baseinfotech.juscep.model.UserType;
import com.baseinfotech.juscep.utility.ApplicationConstants;
import com.baseinfotech.juscep.utility.SharedPreferenceUtil;

public class LaunchingActivity2 extends AppCompatActivity {

    private static final long START_TIME = 2000;
    private static final long INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launching2);

        if (isAnyUserLoggedIn()){
            MyCountDownTimer myCountDownTimer = new MyCountDownTimer(START_TIME, INTERVAL);
            myCountDownTimer.start();

        } else {
            findViewById(R.id.vendor_button).setVisibility(View.VISIBLE);
            findViewById(R.id.customer_button).setVisibility(View.VISIBLE);
        }




    }

    private boolean isAnyUserLoggedIn(){
        User user = SharedPreferenceUtil.getLoggedInUSer(this);
        return user!=null;
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

    public void onVendorClick(View view) {
        ApplicationConstants.SELECTED_USER_TYPE_FOR_LOGIN_OR_REGISTRATION = UserType.VENDOR;
        startAskPermissionActivity();
    }

    public void onCustomerClick(View view) {
        ApplicationConstants.SELECTED_USER_TYPE_FOR_LOGIN_OR_REGISTRATION = UserType.CUSTOMER;
        startAskPermissionActivity();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }
}