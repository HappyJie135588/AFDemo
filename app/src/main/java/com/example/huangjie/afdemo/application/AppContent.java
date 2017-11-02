package com.example.huangjie.afdemo.application;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gongli on 2017/7/17 17:39
 * email: lc824767150@163.com
 */

public class AppContent {
    private static AppContent instance;

    //线程池
    private ExecutorService threadPool;

    private Gson gson;


    //APP启动时调用
    public void init(Context context) {
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }
        return gson;
    }

    public ExecutorService getThreadPool() {
        if (threadPool == null) {
            threadPool = Executors.newFixedThreadPool(4);
        }
        return threadPool;
    }

    private AppContent() {
    }

    public static AppContent getInstance() {
        if (instance == null) {
            synchronized (AppContent.class) {
                if (instance == null) {
                    instance = new AppContent();
                }
            }
        }
        return instance;
    }
}
