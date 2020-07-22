package com.example.androidaidl.aidl.custombinder.server;

import android.os.IInterface;
import android.os.RemoteException;


import com.example.androidaidl.aidl.custombinder.CustomBook;

import java.util.List;

/**
 * author:lgh on 2019-11-19 14:56
 */
public interface BookManager extends IInterface {

    List<CustomBook> getBooks() throws RemoteException;

    void addBook(CustomBook book) throws RemoteException;

}
