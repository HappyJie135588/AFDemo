package com.example.huangjie.afdemo.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


/**
 * Created by gongli on 2017/7/17 17:39
 * email: lc824767150@163.com
 */
public class App extends MultiDexApplication {
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static App getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(final Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static Context getContext() {
        return sInstance.getApplicationContext();
    }
}
