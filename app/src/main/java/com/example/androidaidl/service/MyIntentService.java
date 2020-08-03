package com.example.androidaidl.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/8/3:14:05
 * @description
 */
public class MyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
