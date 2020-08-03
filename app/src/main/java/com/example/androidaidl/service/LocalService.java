package com.example.androidaidl.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.androidaidl.MainActivity;
import com.example.androidaidl.R;

/**
 * @author lgh on 2020/7/20:11:01
 * @description service 一般用法
 */
public class LocalService extends Service {

    private static final String TAG = "LocalService";

    /**
     * 该方法必须重写，一般使用返回null即可
     * 使用bindService()需返回Binder，如 AIDL
     *
     * @param intent intent
     * @return null
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: ");
        return new LocalBinder();
    }

    /**
     * 第一次启动时调用onCreate
     * 如未调用stopService
     * 后续调用startService不会调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 每次调用startService都会调用，在onCreate后
     *
     * @param intent  intent
     * @param flags   标志
     * @param startId startId
     * @return super
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("=====intent====="+intent.getStringExtra("key"));
        System.out.println("=====flags===="+flags);
        System.out.println("=====startId========"+startId);
        Log.e(TAG, "onStartCommand: "+ super.onStartCommand(intent, flags, startId));

        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 20, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getPackageName());
        builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("title")
                .setContentText("text")
                .setAutoCancel(true)//滑动取消
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pi)
                .setChannelId(getPackageName()) //必须添加（Android 8.0） 【唯一标识】
                .build();
        Notification notification = builder.build();
        startForeground(122,notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class LocalBinder extends Binder {

        public LocalService getService() {

            return LocalService.this;
        }


        public void start() {
            Log.e(TAG, "start: ");
        }

        public void stop() {
            Log.e(TAG, "stop: ");
        }

    }

    public void say() {
        Log.e(TAG, "say: ");
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e(TAG, "onTaskRemoved: " );
        super.onTaskRemoved(rootIntent);
    }
}
