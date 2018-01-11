package com.example.administrator.learntask.activity;

import android.animation.ValueAnimator;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.webkit.CookieSyncManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.learntask.R;
import com.example.administrator.learntask.animator.AnimatorFactory;
import com.example.administrator.learntask.entity.DaoSession;
import com.example.administrator.learntask.entity.User;
import com.example.administrator.learntask.entity.UserDao;
import com.example.administrator.learntask.net.HttpConnection;
import com.example.administrator.learntask.net.RetrofitUsage;
import com.example.administrator.learntask.observer.MainObserver;
import com.example.administrator.learntask.subject.observer.StringObserver;
import com.example.administrator.learntask.subject.subject.StringSubject;
import com.example.administrator.learntask.view.CircleProgressView;
import com.example.administrator.learntask.view.CombineEditText;
import com.example.administrator.learntask.view.customeET;
import com.example.administrator.learntask.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ValueAnimator animator;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CookieSyncManager.createInstance(this);
        final TextView text = findViewById(R.id.main_text);
        CombineEditText combineEdit = findViewById(R.id.mConombineEidt);
        customeET customeET = findViewById(R.id.test001);
        final CircleProgressView circle = findViewById(R.id.cirlce_test);
        circle.setOnClickListener(this);

        //生命周期管理
        Lifecycle lifecycle = getLifecycle();
        lifecycle.addObserver(new MainObserver());
        //view model
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                text.setText(s);
            }
        };
        viewModel.getData().observe(this, observer);
        viewModel.getName().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                for (User user : users) {
                    Log.e("tag", "name=" + user.getName());
                }
            }
        });
        //被观察者：发布者
        final StringSubject subject = new StringSubject();
        //观察者：订阅者
        subject.attach(new StringObserver("lee"));
        subject.attach(new StringObserver("blue"));


        combineEdit.setOnTextChangeListener(new CombineEditText.OnTextChangeListener() {
            @Override
            public void onTextChanged(String text) {
                viewModel.getData().setValue(text);//postValue可用于从后台线程向主线程赋值
                subject.notify(text);
            }
        });
        combineEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new httpRequest().start();
                AnimatorFactory factory = new AnimatorFactory();
                animator = factory.CreateValueAnimator(circle, 10);

                viewModel.saveData(text.getText().toString(), 20);
                //  greendao 数据库操作
               /* DaoSession session = MyApplication.getInstance().getSession();
                UserDao dao = session.getUserDao();
                List<User> findUser = dao.queryBuilder().where(UserDao.Properties.Name.eq("Lee")).build().list();
                if (findUser != null) {
//                    dao.delete(findUser.get(0));  //删     数据库
                    Log.e("tag", "delete user");
                }
                */
            }
        });


        customeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitUsage.requestWebPage("");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cirlce_test:
                viewModel.queryData();
                break;
        }
    }

    private class httpRequest extends Thread {
        @Override
        public void run() {
            String url = "http://47.92.3.234:8090/rent-car-customer-interface/coveredCity/semiLogin/list.gson";
            Log.e("tag", "http do-->");
            Map<String, String> params = new HashMap<>();
            params.put("cityName", "兰州");
//            HttpConnection.doPost("http://wlei1818.iteye.com","GET", null);

            HttpConnection.getInstance().doPost(url, "POST", params);
//            HttpConnection.doGet("http://liuwangshu.cn/application/network/1-http.html");
            //          HttpConnection.getInstance().doGet("http://47.92.3.234:8090/rent-car-customer-interface/");
        }
    }

}
