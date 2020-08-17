package com.example.androidaidl.broadcast;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @author lgh on 2020/8/11:16:59
 * @description
 */
public class CounterService extends Service implements ICounterService {


    private final static String LOG_TAG = "com.example.androidaidl.broadcast.CounterService";

    public final static String BROADCAST_COUNTER_ACTION = "com.example.androidaidl.broadcast.COUNTER_ACTION";

    public final static String COUNTER_VALUE = "com.example.androidaidl.broadcast.counter.value";

    private boolean stop = false;

    private final IBinder binder = new CounterBinder();


    public class CounterBinder extends Binder {
        public CounterService getCounterService() {
            return CounterService.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void startCounter(int initVal) {

        @SuppressLint("StaticFieldLeak") AsyncTask<Integer, Integer, Integer> task = new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... integers) {
                Integer initCounter = initVal;
                stop = false;
                while (!stop) {
                    publishProgress(initCounter);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    initCounter++;

                }
                return initCounter;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                int counter = values[0];
                Intent intent = new Intent(BROADCAST_COUNTER_ACTION);
                intent.putExtra(COUNTER_VALUE, counter);
                sendBroadcast(intent);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                int value = integer;
                Intent intent = new Intent(BROADCAST_COUNTER_ACTION);
                intent.putExtra(COUNTER_VALUE, value);
                sendBroadcast(intent);
            }
        };

        task.execute();

    }

    @Override
    public void stopCounter() {
        stop = true;
    }
}
