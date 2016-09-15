package com.bigx.config;

import android.os.Environment;

/**
 * Created by zhaoshuai on 16/9/15.
 */
public class BigxConstant {

    public static final String CACHE_BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath().concat("/bigx");
    public static final String CACHE_PICTURE_PATH = CACHE_BASE_PATH.concat("/picture");
    
}
