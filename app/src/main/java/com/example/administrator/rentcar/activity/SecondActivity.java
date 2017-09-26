package com.example.administrator.rentcar.activity;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.rentcar.R;
import com.example.administrator.rentcar.databinding.ActivitySecondBinding;
import com.example.administrator.rentcar.service.TestService;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySecondBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_second);
        binding.setBtnName("click");
        binding.setPresenter(this);
        startService(new Intent(this, TestService.class));
        Log.d("tag", "MainThread process info : pid-" + Process.myPid() + " uid-" + Process.myUid() + " tid-" + Process.myTid());
    }

    public void onClick() {
        stopService(new Intent(this, TestService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("tag", "activity destroy");
    }

    @Override
    public void onBackPressed() {
        Log.d("tag", "system exit");
//        onDestroy();
        ActivityManager manager = (ActivityManager) getSystemService(Service.ACTIVITY_SERVICE);
//        manager.restartPackage(getPackageName());//需要其他方式配合使用？测试，不能结束进程，该方法已失效
//        manager.killBackgroundProcesses(getPackageName());//需要其他方式配合，不然会重新启动进程？？使用此方法可杀掉多进程
//        Process.killProcess(Process.myPid());//经测试，本地service不再存活，不触发service onDestroy.kill uid 不退出当前应用，且远程service继续工作，
        // 应使用pid，且与killBackgroundProcesses配合使用杀掉远程进程
        System.exit(0); //经测试，本地service不再存活，不触发service onDestroy
//        finish();//看Service是否在后台活动，经测试，service继续工作

       /* try {
            Method method = Class.forName("ActivityManager").getMethod("forceStopPackage", String.class);
            method.invoke(manager, getPackageName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }*/
    }
}
