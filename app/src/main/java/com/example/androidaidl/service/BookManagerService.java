package com.example.androidaidl.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.androidaidl.aidl.Book;
import com.example.androidaidl.aidl.IBookManager;
import com.example.androidaidl.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * author:lgh on 2020-05-09 16:48
 */
public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";


    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private RemoteCallbackList<IOnNewBookArrivedListener> mRemoteCallbackList = new RemoteCallbackList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(0, "张三"));
        mBookList.add(new Book(1, "李四"));
    }

    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

            //验证权限
            int check = checkCallingOrSelfPermission("com.example.androidaidl.permission.ACCESS_BOOK_SERVICE");
            if (check == PackageManager.PERMISSION_DENIED) {
                return false;
            }

            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length != 0) {
                packageName = packages[0];
            }
            if (packageName == null && !packageName.startsWith("com.example.androidaidl")) {
                return false;
            }

            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.e(TAG, "getBookList: " + mBookList.size());
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.e(TAG, "addBook: " + book.getBookName());
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mRemoteCallbackList.register(listener);//注册
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mRemoteCallbackList.unregister(listener);//解除注册
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //验证权限
        int check = checkCallingOrSelfPermission("com.example.androidaidl.permission.ACCESS_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return mBinder;
    }
}
