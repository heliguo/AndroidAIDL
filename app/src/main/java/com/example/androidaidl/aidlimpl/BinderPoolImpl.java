package com.example.androidaidl.aidlimpl;

import android.os.IBinder;
import android.os.RemoteException;

import com.example.androidaidl.IBinderPool;

import static com.example.androidaidl.aidlimpl.BinderPool.BINDER_COMPUTE;
import static com.example.androidaidl.aidlimpl.BinderPool.BINDER_SECURITY_CENTER;

/**
 * author:lgh on 2020-05-11 16:59
 */
public class BinderPoolImpl extends  IBinderPool.Stub{

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_SECURITY_CENTER:
                    binder = new SecurityCenterImpl();
                    break;

                case BINDER_COMPUTE:
                    binder = new ComputeImpl();
                    break;

            }

            return binder;
        }
}
