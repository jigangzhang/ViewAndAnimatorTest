package com.example.administrator.learntask.subject.subject;

import com.example.administrator.learntask.subject.observer.Observer;

/**
 * Created by Administrator on 2018/1/11.
 */

public interface Subject<T> {
    /**
     * @param observer 增加的订阅者
     */
    void attach(Observer observer);

    /**
     * @param observer 删除的订阅者
     */
    void detach(Observer observer);

    /**
     * @param T 通知订阅者更新的消息内容
     */
    void notify(T t);
}
