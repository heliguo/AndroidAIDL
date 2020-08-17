package com.example.androidaidl.broadcast;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidaidl.databinding.ActivityBroadcastBinding;

public class BroadcastActivity extends AppCompatActivity {

    ActivityBroadcastBinding mBinding;
    CounterService counterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBroadcastBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Intent intent = new Intent(this, CounterService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        click();
    }

    private void click() {
        mBinding.startCounter.setOnClickListener(v -> {
            if (counterService != null) {
                counterService.startCounter(1);
            }
        });

        mBinding.stopCounter.setOnClickListener(v -> {
            if (counterService != null) {
                counterService.stopCounter();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter counterActionFilter =
                new IntentFilter(CounterService.BROADCAST_COUNTER_ACTION);
        registerReceiver(counterActionReceiver, counterActionFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(counterActionReceiver);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            counterService = ((CounterService.CounterBinder) service).getCounterService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            counterService = null;
        }
    };

    BroadcastReceiver counterActionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int value = intent.getIntExtra(CounterService.COUNTER_VALUE, 0);
            mBinding.counterValue.setText(String.valueOf(value));
        }
    };
}