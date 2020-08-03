package com.example.androidaidl.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidaidl.databinding.ActivityServiceBinding;

public class ServiceActivity extends AppCompatActivity {

    ActivityServiceBinding mServiceBinding;
    private boolean bindService = false;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceBinding = ActivityServiceBinding.inflate(getLayoutInflater());
        setContentView(mServiceBinding.getRoot());
        mServiceBinding.bindService.setOnClickListener(v -> {
            Log.e("TAG", "onCreate: ");
            Intent intent = new Intent(this, LocalService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            bindService = true;
        });
        mServiceBinding.unbindService.setOnClickListener(v -> {
            if (!bindService) {
                Toast.makeText(this, "you should bind service firstly", Toast.LENGTH_SHORT).show();
                return;
            }
            unbindService(mConnection);
            bindService = false;
        });

        mServiceBinding.startService.setOnClickListener(v -> {
            mIntent = new Intent(this,LocalService.class);
            mIntent.putExtra("key","12345");
            startService(mIntent);

        });

        mServiceBinding.stopService.setOnClickListener(v -> {
            stopService(mIntent);
        });


    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            binder.start();
            binder.stop();
            binder.getService().say();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        if (bindService) {
            unbindService(mConnection);
        }
        super.onDestroy();
    }
}