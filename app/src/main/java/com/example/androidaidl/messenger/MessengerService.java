package com.example.androidaidl.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author:lgh on 2020-05-09 15:36
 */
public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    private static class MessengerHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {

            if (msg.what == 0) {
                Log.e(TAG, "handleMessage: " + msg.getData().getString("msg"));
                Messenger replyTo = msg.replyTo;//获取客户端的Messenger
                Message obtain = Message.obtain(null, 1);
                Bundle bundle = new Bundle();
                bundle.putString("reply", "message is received");
                obtain.setData(bundle);
                try {
                    replyTo.send(obtain);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            } else {
                super.handleMessage(msg);
            }

        }

    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
