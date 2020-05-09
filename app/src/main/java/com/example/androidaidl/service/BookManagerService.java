package com.example.androidaidl.service;

import android.app.Service;
import android.content.Intent;
import android.net.nsd.NsdManager;
import android.os.Binder;
import android.os.IBinder;
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

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(0, "张三"));
        mBookList.add(new Book(1, "李四"));
    }

    private Binder mBinder = new IBookManager.Stub() {
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

        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
