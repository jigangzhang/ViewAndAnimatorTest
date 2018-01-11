package com.example.administrator.learntask.subject.observer;

import android.util.Log;

/**
 * Created by Administrator on 2018/1/11.
 */

public class StringObserver implements Observer<String> {

    private String name;

    public StringObserver(String name) {
        this.name = name;
    }

    @Override
    public void onUpdate(String s) {
        Log.e("tag", "observer--" + name + "--" + s);
    }
}
