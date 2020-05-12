package com.example.androidaidl.aidlimpl;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidaidl.ICompute;
import com.example.androidaidl.ISecurityCenter;
import com.example.androidaidl.R;

/**
 * author:lgh on 2020-05-12 08:30
 */
public class BinderPoolActivity extends AppCompatActivity {

    private static final String TAG = "BinderPoolActivity";

    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: " );
                doWork();
            }
        }).start();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
        Log.e(TAG, "visit security center ");
        String msg = "hello android";
        try {
            String password = mSecurityCenter.encrypt(msg);
            Log.e(TAG, "encrypt: " + password);
            Log.e(TAG, "decrypt: " + mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "visit compute ");
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);
        try {
            Log.e(TAG, "compute: 3+5=" +mCompute.add(3,5) );
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
