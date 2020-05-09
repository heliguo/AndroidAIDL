package com.example.androidaidl.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidaidl.R;
import com.example.androidaidl.aidl.Book;
import com.example.androidaidl.aidl.IBookManager;
import com.example.androidaidl.service.BookManagerService;

import java.util.List;

/**
 * author:lgh on 2020-05-09 17:02
 */
public class BookManagerActivity extends AppCompatActivity {

    private static final String TAG = "BookManagerActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager iBookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> bookList = iBookManager.getBookList();
                for (int i = 0; i < bookList.size(); i++) {
                    Log.e(TAG, "onServiceConnected: " + bookList.get(i).getBookName());
                }
                iBookManager.addBook(new Book(2, "王五"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
