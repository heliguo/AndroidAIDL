package com.example.androidaidl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidaidl.aidlimpl.BinderPoolActivity;
import com.example.androidaidl.aidlimpl.BookManagerActivity;
import com.example.androidaidl.contentprovider.ProviderActivity;
import com.example.androidaidl.messenger.MessengerActivity;
import com.example.androidaidl.service.ServiceActivity;
import com.example.androidaidl.socket.ClientSocketActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
