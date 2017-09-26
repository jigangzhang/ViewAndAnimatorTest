package com.example.administrator.rentcar.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

public class TestService extends Service {
    public TestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("tag", "service create");//多进程的 pid 不同， uid 相同
        Log.d("tag", "service process info : pid-" + Process.myPid() + " uid-" + Process.myUid() + " tid-" + Process.myTid());
        //使用stopService可结束远程进程，调用onDestroy
        //远程进程存活时，再开启当前service时，不调用onCreate(),只触发onStartCommand
        //ActivityManager.killBackgroundProcesses() 可杀掉远程进程，但不调用onDestroy，若需要退出当前应用，需与其他方法一起使用：Process.killProcess(Process.myPid())，System.exit(0);
        //当前service stop后，若有线程未结束，则线程继续工作，直至线程结束
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    for (int i = 0; i < 10; ++i) {
                        Log.d("tag", "wake-->" + i);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
//                    stopSelf();
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("tag", "service destroy");
        sendBroadcast(new Intent("com.example.service.test"));
    }
}
