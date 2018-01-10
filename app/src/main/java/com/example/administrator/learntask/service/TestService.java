package com.example.administrator.learntask.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

public class TestService extends Service {

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 0:
                    Toast.makeText(getApplicationContext(), "remote process toast", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    Messenger mMessenger = new Messenger(mHandler);

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("tag", "onBind...");
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("tag", "onUnbind...");
        return false; //返回 true 或 false 的区别
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("tag", "service create");//多进程的 pid 不同， uid 相同
        //使用stopService可结束远程进程，调用onDestroy
        //远程进程存活时，再开启当前service时，不调用onCreate(),只触发onStartCommand
        //ActivityManager.killBackgroundProcesses() 可杀掉远程进程，但不调用onDestroy，若需要退出当前应用，需与其他方法一起使用：Process.killProcess(Process.myPid())，System.exit(0);
        //当前service stop后，若有线程未结束，则线程继续工作，直至线程结束
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("tag", "onStartCommand...");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("tag", "service destroy");
    }
}
