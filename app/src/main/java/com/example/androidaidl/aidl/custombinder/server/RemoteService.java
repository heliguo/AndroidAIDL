package com.example.androidaidl.aidl.custombinder.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;


import com.example.androidaidl.aidl.custombinder.CustomBook;

import java.util.ArrayList;
import java.util.List;

/**
 * author:lgh on 2019-11-19 14:54
 */
public class RemoteService extends Service {

    private List<CustomBook> books = new ArrayList<>();

    public RemoteService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CustomBook book = new CustomBook();
        book.setName("三体");
        book.setPrice(88);
        books.add(book);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return bookManager;
    }

    private final Stub bookManager = new Stub() {
        @Override
        public List<CustomBook> getBooks() throws RemoteException {
            synchronized (this) {
                if (books != null) {
                    return books;
                }
                return new ArrayList<>();
            }
        }

        @Override
        public void addBook(CustomBook book) throws RemoteException {
            synchronized (this) {
                if (books == null) {
                    books = new ArrayList<>();
                }

                if (book == null)
                    return;

                book.setPrice(book.getPrice() * 2);
                books.add(book);

                Log.e("Server", "books: " + book.toString());
            }
        }
    };
}
