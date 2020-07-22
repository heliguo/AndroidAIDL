package com.example.androidaidl.aidlimpl;

import android.os.RemoteException;

import com.example.androidaidl.ICompute;

/**
 * author:lgh on 2020-05-11 16:56
 * 服务端
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
