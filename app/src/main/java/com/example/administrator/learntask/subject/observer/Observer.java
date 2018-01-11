package com.example.administrator.learntask.subject.observer;

/**
 * Created by Administrator on 2018/1/11.
 */

public interface Observer<T> {
    /**
     * @param T 更新内容
     */
    void onUpdate(T t);
}
