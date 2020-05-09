// IBookManager.aidl
package com.example.androidaidl.aidl;

// Declare any non-default types here with import statements
import com.example.androidaidl.aidl.Book;
import com.example.androidaidl.aidl.IOnNewBookArrivedListener;

interface IBookManager {

  List<Book> getBookList();

  void addBook(in Book book);

  void registerListener(IOnNewBookArrivedListener listener);

  void unregisterListener(IOnNewBookArrivedListener listener);

}
