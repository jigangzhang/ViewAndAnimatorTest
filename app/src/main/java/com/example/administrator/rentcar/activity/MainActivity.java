package com.example.administrator.rentcar.activity;

import android.animation.ValueAnimator;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

import com.example.administrator.rentcar.R;
import com.example.administrator.rentcar.animator.AnimatorFactory;
import com.example.administrator.rentcar.net.HttpConnection;
import com.example.administrator.rentcar.view.CircleProgressView;
import com.example.administrator.rentcar.view.CombineEditText;
import com.example.administrator.rentcar.view.customeET;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CookieSyncManager.createInstance(this);
        CombineEditText combineEdit = (CombineEditText) findViewById(R.id.mConombineEidt);
        final customeET customeET = (customeET) findViewById(R.id.test001);
        final CircleProgressView circle = (CircleProgressView) findViewById(R.id.cirlce_test);
        combineEdit.setEditText("18883286003");

        combineEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "customeET==onclick", Toast.LENGTH_SHORT).show();
                Log.e("tag", "onclick");
                new httpRequest().start();
                AnimatorFactory factory = new AnimatorFactory();
                animator = factory.CreateValueAnimator(circle, 10);
            }
        });


        customeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag", "customeET==onclick");
                if (animator == null) {
                    return;
                }
                if (animator.isPaused()) {
                    animator.resume();
                } else if (animator.isRunning()) {
                    animator.pause();
                }
            }
        });

        //裁剪圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            customeET.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    view.setClipToOutline(true);
                    outline.setRoundRect(20, 0, view.getWidth(), view.getHeight(), 30);
                }
            });
        }



    /*    TestEditText editText = (TestEditText) findViewById(R.id.test);
        editText.setLeftDrawableRedId(R.mipmap.ic_launcher);
        editText.setRightDrawableResId(R.mipmap.ic_launcher_round);
        editText.setAddTextLeft(true);  //不起作用
        editText.setLeftText("password");*/
    }

    private class httpRequest extends Thread {
        @Override
        public void run() {
            String url = "http://47.92.3.234:8090/rent-car-customer-interface/carTypeRecommend/semiLogin/carTypeRecommendList.gson";
            Log.e("tag", "http do-->");
            Map<String, String> params = new HashMap<>();
            params.put("customerId", "null");
            params.put("cityName", "兰州");
//            HttpConnection.doPost("http://wlei1818.iteye.com","GET", null);

            HttpConnection.getInstance().doPost(url, "POST", params);
//            HttpConnection.doGet("http://liuwangshu.cn/application/network/1-http.html");
            HttpConnection.getInstance().doGet("http://47.92.3.234:8090/rent-car-customer-interface/");
        }
    }

}