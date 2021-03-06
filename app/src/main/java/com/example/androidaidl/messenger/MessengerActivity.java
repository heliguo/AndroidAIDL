package com.example.androidaidl.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidaidl.R;

/**
 * author:lgh on 2020-05-09 15:43
 */
public class MessengerActivity extends AppCompatActivity {

    private static final String TAG = "MessengerActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
//        Debug.waitForDebugger();
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected: " + name.toString());
            //串行处理
            Messenger messenger = new Messenger(service);
            Message msg = Message.obtain(null, 0);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "hello,this is client");
            msg.setData(bundle);
            msg.replyTo = mGetMessenger;
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private static class ClientHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Log.e(TAG, "handleMessage: " + msg.getData().getString("reply"));
            } else
                super.handleMessage(msg);
        }
    }

    private Messenger mGetMessenger = new Messenger(new ClientHandler());

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
