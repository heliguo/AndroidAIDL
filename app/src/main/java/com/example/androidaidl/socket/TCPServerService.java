package com.example.androidaidl.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * author:lgh on 2020-05-11 14:31
 */
public class TCPServerService extends Service {

    private boolean mIsServiceDestroied = false;
    private String[] mDefinedMessages = new String[]{
            "你好啊",
            "你叫啥",
            "天气",
            "聊天机器",
            "笑话"
    };

    @Override
    public void onCreate() {
        new Thread(new TCPServer()).start();
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TCPServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                System.err.println("establish tcp server failed,port:8688");
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDestroied) {
                try {
                    final Socket client = serverSocket.accept();
                    System.out.println("accept");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        private void responseClient(Socket client) throws IOException {
            //接收客户端消息
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            //向客户端发送消息
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(client.getOutputStream())), true);
            out.println("欢迎来到聊天室");
            while (!mIsServiceDestroied) {
                String read = in.readLine();
                System.out.println("msg from client: " + read);
                if (read == null) {
                    break;
                }
                int i = new Random().nextInt(mDefinedMessages.length);
                String msg = mDefinedMessages[i];
                out.println(msg);
                System.out.println("send: " + msg);
            }
            System.out.println("client closed");
            out.close();
            in.close();
            client.close();
        }
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroied = true;
        super.onDestroy();
    }
}
