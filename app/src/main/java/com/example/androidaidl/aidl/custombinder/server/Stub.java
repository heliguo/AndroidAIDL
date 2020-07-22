package com.example.androidaidl.aidl.custombinder.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;


import com.example.androidaidl.aidl.Book;
import com.example.androidaidl.aidl.custombinder.CustomBook;
import com.example.androidaidl.aidl.custombinder.proxy.Proxy;

import java.util.List;


/**
 * author:lgh on 2019-11-19 14:58
 */
public abstract class Stub extends Binder implements BookManager  {

    private static final String DESCRIPTOR = "com.baronzhang.ipc.server.BookManager";

    public Stub() {
        this.attachInterface(this, DESCRIPTOR);
    }

    public static BookManager asInterface(IBinder binder) {
        if (binder == null)
            return null;
        IInterface iin = binder.queryLocalInterface(DESCRIPTOR);
        if (iin != null && iin instanceof BookManager)
            return (BookManager) iin;
        return new Proxy(binder);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {

            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;

            case TRANSAVTION_getBooks:
                data.enforceInterface(DESCRIPTOR);
                List<CustomBook> result = this.getBooks();
                reply.writeNoException();
                reply.writeTypedList(result);
                return true;

            case TRANSAVTION_addBook:
                data.enforceInterface(DESCRIPTOR);
                CustomBook arg0 = null;
                if (data.readInt() != 0) {
                    arg0 = CustomBook.CREATOR.createFromParcel(data);
                }
                this.addBook(arg0);
                reply.writeNoException();
                return true;

        }
        return super.onTransact(code, data, reply, flags);
    }

    public static final int TRANSAVTION_getBooks = IBinder.FIRST_CALL_TRANSACTION;
    public static final int TRANSAVTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;
}
