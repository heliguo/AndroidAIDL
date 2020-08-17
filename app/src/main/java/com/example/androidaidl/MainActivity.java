package com.example.androidaidl;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidaidl.aidl.custombinder.client.ClientActivity;
import com.example.androidaidl.aidlimpl.BinderPoolActivity;
import com.example.androidaidl.aidlimpl.BookManagerActivity;
import com.example.androidaidl.broadcast.BroadcastActivity;
import com.example.androidaidl.contentprovider.ProviderActivity;
import com.example.androidaidl.messenger.MessengerActivity;
import com.example.androidaidl.service.ServiceActivity;
import com.example.androidaidl.socket.ClientSocketActivity;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.e(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    public void startMes(View view) {
        startActivity(new Intent(this, MessengerActivity.class));
    }

    public void startAIDL(View view) {
        startActivity(new Intent(this, BookManagerActivity.class));
    }

    public void startProvider(View view) {
        startActivity(new Intent(this, ProviderActivity.class));
    }

    public void useSocket(View view) {
        startActivity(new Intent(this, ClientSocketActivity.class));
    }

    public void binderPool(View view) {

        startActivity(new Intent(this, BinderPoolActivity.class));

    }

    public void binderLocalService(View view) {
        startActivity(new Intent(this, ServiceActivity.class));
    }

    public void customBinder(View view) {
        startActivity(new Intent(this, ClientActivity.class));
    }

    public void broadcast(View view) {
        startActivity(new Intent(this, BroadcastActivity.class));
    }
}
