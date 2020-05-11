// IOnNewBookArrivedListener.aidl
package com.example.androidaidl.aidl;

import com.example.androidaidl.aidl.Book;

interface IOnNewBookArrivedListener {

   void onNewBookArrived(in Book book);

}
