package com.bigx.app;

import android.app.Application;

import io.rong.imkit.RongIM;

/**
 * Created by zhaoshuai on 16/9/15.
 */
public class XTalkApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
    }
}
