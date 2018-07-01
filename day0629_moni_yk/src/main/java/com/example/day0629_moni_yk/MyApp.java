package com.example.day0629_moni_yk;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by John on 2018/6/29 0029.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
