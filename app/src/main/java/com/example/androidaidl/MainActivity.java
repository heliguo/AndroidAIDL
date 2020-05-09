package com.example.androidaidl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidaidl.client.BookManagerActivity;
import com.example.androidaidl.client.MessengerActivity;

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
}
