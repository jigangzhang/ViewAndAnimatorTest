package com.example.administrator.learntask.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.learntask.R;
import com.example.administrator.learntask.databinding.ActivitySecondBinding;
import com.example.administrator.learntask.service.TestService;

import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private Messenger mMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySecondBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_second);
        binding.setBtnName("click");
        binding.setPresenter(this);
        SharedPreferences sp = getSharedPreferences("demo", Context.MODE_PRIVATE);
        String key = sp.getString("key", null);
        Log.e("tag", "key=" + key);
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("tag", "onServiceConnected...");
            mMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("tag", "onServiceDisconnected...");
            SharedPreferences sp = getSharedPreferences("demo", Context.MODE_PRIVATE);
            sp.edit().putString("key", "onServiceDisconnected").apply();
        }
    };

    /**
     * 绑定
     */
    public void onClick() {
        Log.d("tag", "onClick...");
        bindService(new Intent(this, TestService.class), mConnection, BIND_AUTO_CREATE);
    }

    public void click() {
        Log.d("tag", "click...");
        unbindService(mConnection);
        ActivityManager info = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
                info.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
            if (runningAppProcess.processName.equals("com.example.administrator.learntask:test")) {
                Process.killProcess(runningAppProcess.pid);
            }

        }
//        startService(new Intent(this, TestService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        Log.d("tag", "activity destroy");
    }
}
