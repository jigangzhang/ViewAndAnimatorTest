package com.example.administrator.learntask.subject.subject;

import com.example.administrator.learntask.subject.observer.Observer;
import com.example.administrator.learntask.subject.observer.StringObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/11.
 * <p>
 * 观察者模式：android中BaseAdapter、OnClickListener 等等，都有使用。
 * 具体可用接口或抽象类+泛型的灵活使用实现
 */

public class StringSubject implements Subject<String> {

    private List<Observer> observers = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notify(String s) {
        for (Observer observer : observers) {
            observer.onUpdate(s);
        }
    }
}
