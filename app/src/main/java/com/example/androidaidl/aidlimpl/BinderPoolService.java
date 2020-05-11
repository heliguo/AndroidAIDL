package com.example.androidaidl.aidlimpl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * author:lgh on 2020-05-11 17:03
 *
 */
public class BinderPoolService extends Service {


    private Binder mBinder = new BinderPool.BinderPoolImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
