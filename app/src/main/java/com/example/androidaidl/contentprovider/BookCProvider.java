package com.example.androidaidl.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author:lgh on 2020-05-11 10:04
 * <p>
 * android 提供的MediaStore 就是文件类型的ContentProvider
 */
public class BookCProvider extends ContentProvider {

    private static final String TAG = "BookCProvider";

    public static final String AUTHORITY = "com.example.androidaidl.contentprovider";

    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private SQLiteDatabase mDatabase;

    static {
        URI_MATCHER.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        URI_MATCHER.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    @Override
    public boolean onCreate() {

        Log.e(TAG, "onCreate: " + Thread.currentThread().getName());

        DbOpenHelper mHelper = new DbOpenHelper(getContext());
        mDatabase = mHelper.getWritableDatabase();
        //初始化数据库，测试用，不建议在主线程做
        initDb();
        return true;
    }

    /**
     * 不应该做耗时操作
     */
    private void initDb() {
        mDatabase.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDatabase.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mDatabase.execSQL("insert into book values(3,'android');");
        mDatabase.execSQL("insert into book values(4,'ios');");
        mDatabase.execSQL("insert into book values(5,'html');");
        mDatabase.execSQL("insert into user values(1,'jack',1);");
        mDatabase.execSQL("insert into user values(2,'jasmine',0);");

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.e(TAG, "query: " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalStateException("Unsupportd Uri" + uri);
        }

        return mDatabase.query(tableName, projection, selection, selectionArgs, null, null
                , sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {//指定MIME类型
        Log.e(TAG, "getType: ");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.e(TAG, "insert: ");
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalStateException("Unsupportd Uri" + uri);
        }
        mDatabase.insert(tableName, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG, "delete: ");
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalStateException("Unsupportd Uri" + uri);
        }
        int count = mDatabase.delete(tableName, selection, selectionArgs);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG, "update: ");
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalStateException("Unsupportd Uri" + uri);
        }
        int update = mDatabase.update(tableName, values, selection, selectionArgs);
        if (update > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return update;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (URI_MATCHER.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }

    /**
     * 如果需要实现RPC（Remote Procedure call  远程程序调用：其他进程调用）
     * @param method
     * @param arg
     * @param extras
     * @return
     */

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        return super.call(method, arg, extras);
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String authority, @NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        return super.call(authority, method, arg, extras);
    }
}
