package com.bigx.app.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.bigx.app.LoginActivity;
import com.bigx.app.MainActivity;
import com.bigx.app.R;
import com.bigx.utils.SharedUtil;

public class SplashActivity extends Activity {

    private static final long SPLASH_DELAY_MILLS = 1000l;
    private static final int GO_HOME= 1000;
    private static final int GO_LOGIN= 1001;
    private boolean isLogin = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_LOGIN:
                    goLogin();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        isLogin = SharedUtil.isLogin(this);
        //int go = isLogin ? GO_HOME : GO_LOGIN;
        int go = GO_LOGIN;
        mHandler.sendEmptyMessageDelayed(go, SPLASH_DELAY_MILLS);
    }

    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
