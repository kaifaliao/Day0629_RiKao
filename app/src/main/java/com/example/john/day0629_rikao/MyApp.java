package com.example.john.day0629_rikao;

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
