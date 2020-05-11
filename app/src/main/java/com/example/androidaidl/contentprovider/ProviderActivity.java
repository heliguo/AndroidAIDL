package com.example.androidaidl.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidaidl.R;
import com.example.androidaidl.aidl.Book;
import com.example.androidaidl.aidl.User;

/**
 * author:lgh on 2020-05-11 10:19
 */
public class ProviderActivity extends AppCompatActivity {

    private static final String TAG = "ProviderActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        //声明权限
//        Uri uri = Uri.parse("content://com.example.androidaidl.contentprovider");
//        getContentResolver().query(uri, null, null, null, null);
//        getContentResolver().query(uri, null, null, null, null);
//        getContentResolver().query(uri, null, null, null, null);
//        getContentResolver().query(uri, null, null, null, null);

        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "ProviderActivity");
        getContentResolver().insert(BookCProvider.BOOK_CONTENT_URI, values);
        Cursor bookCursor = getContentResolver().query(BookCProvider.BOOK_CONTENT_URI, new String[]{"_id", "name"},
                null, null, null);

        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            Log.e(TAG, "bookCursor: " + book.toString());
        }

        bookCursor.close();

        Cursor userCursor = getContentResolver().query(BookCProvider.USER_CONTENT_URI, new String[]{"_id", "name", "sex"}, null,
                null, null);

        while (userCursor.moveToNext()) {
            User user = new User();
            user.setName(userCursor.getString(1));
            user.setSex(userCursor.getInt(2));
            Log.e(TAG, "userCursor: " + user.toString());
        }
        userCursor.close();

    }
}
