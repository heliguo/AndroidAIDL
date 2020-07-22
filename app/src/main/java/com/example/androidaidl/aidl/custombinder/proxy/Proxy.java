package com.example.androidaidl.aidl.custombinder.proxy;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;


import com.example.androidaidl.aidl.custombinder.CustomBook;
import com.example.androidaidl.aidl.custombinder.server.BookManager;
import com.example.androidaidl.aidl.custombinder.server.Stub;

import java.util.List;

/**
 * author:lgh on 2019-11-19 14:55
 */
public class Proxy implements BookManager {

    private static final String DESCRIPTOR = "com.baronzhang.ipc.server.BookManager";

    private IBinder remote;

    public Proxy(IBinder remote) {

        this.remote = remote;
    }

    public String getInterfaceDescriptor() {
        return DESCRIPTOR;
    }

    @Override
    public List<CustomBook> getBooks() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();
        List<CustomBook> result;

        try {
            data.writeInterfaceToken(DESCRIPTOR);
            remote.transact(Stub.TRANSAVTION_getBooks, data, replay, 0);
            replay.readException();
            result = replay.createTypedArrayList(CustomBook.CREATOR);
        } finally {
            replay.recycle();
            data.recycle();
        }
        return result;
    }

    @Override
    public void addBook(CustomBook book) throws RemoteException {

        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();

        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (book != null) {
                data.writeInt(1);
                book.writeToParcel(data, 0);
            } else {
                data.writeInt(0);
            }
            remote.transact(Stub.TRANSAVTION_addBook, data, replay, 0);
            replay.readException();
        } finally {
            replay.recycle();
            data.recycle();
        }
    }

    @Override
    public IBinder asBinder() {
        return remote;
    }
}
