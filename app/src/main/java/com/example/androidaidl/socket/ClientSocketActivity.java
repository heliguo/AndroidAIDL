package com.example.androidaidl.socket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidaidl.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

/**
 * author:lgh on 2020-05-11 14:57
 */
public class ClientSocketActivity extends AppCompatActivity {

    private Socket mClientSocket;
    private PrintWriter mPrintWriter;
    private EditText mEditText;
    private TextView mTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);
        mEditText = findViewById(R.id.et_msg);
        mTextView = findViewById(R.id.tv_msg);
        findViewById(R.id.sent_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMess();
            }
        });
        Intent server = new Intent(this, TCPServerService.class);
        startService(server);
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectServer();//不能在主线程
            }
        }).start();

    }

    private void connectServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())
                ), true);
                mHandler.sendEmptyMessage(0);//connected
                System.out.println("connected server success");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                System.out.println("connected server failed");
                e.printStackTrace();
            }
        }
        try {
            //接收服务端数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!ClientSocketActivity.this.isFinishing()) {
                String readLine = reader.readLine();
                System.out.println("receive" + readLine);
                if (readLine != null) {
                    @SuppressLint("SimpleDateFormat")
                    String time = new SimpleDateFormat("(HH:ss:mm)").format(System.currentTimeMillis());
                    String showMsg = "server" + time + ":" + readLine + "\n";
                    mHandler.obtainMessage(0, showMsg).sendToTarget();
                }
            }
            System.out.println("quit...");
            mPrintWriter.close();
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {
                mTextView.setText(mTextView.getText().toString() + msg.obj);
            }
            if (msg.what == 1) {
                //断开链接操作
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @SuppressLint("SetTextI18n")
    public void sendMess() {
        String msg = mEditText.getText().toString();
        if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
            mPrintWriter.println(msg);
            mEditText.setText("");
            @SuppressLint("SimpleDateFormat")
            String time = new SimpleDateFormat("(HH:ss:mm)").format(System.currentTimeMillis());
            String showMsg = "self" + time + ":" + msg + "\n";
            mTextView.setText(mTextView.getText() + showMsg);
        }
    }
}
